//
//  XMCCameraViewController.swift
//  dojo-custom-camera
//
//  Created by David McGraw on 11/13/14.
//  Copyright (c) 2014 David McGraw. All rights reserved.
//

import UIKit
import AVFoundation

enum Status: Int {
    case Preview, Still, Error
}

class CameraViewController: UIViewController {

  @IBOutlet weak var cameraStill: UIImageView!
  @IBOutlet weak var cameraPreview: UIView!
  @IBOutlet weak var cameraStatus: UILabel!
  @IBOutlet weak var cameraCapture: UIButton!
  
  var preview: AVCaptureVideoPreviewLayer?
  
  var isRunning = false
  var camera: Camera?
  var status: Status = .Preview
  var faceView: UIView?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.initializeCamera()
    cameraCapture.layer.cornerRadius = cameraCapture.frame.size.width / 2
    
    faceView = UIView()
    faceView?.layer.borderColor = UIColor.greenColor().CGColor
    faceView?.layer.borderWidth = 2
    view.addSubview(faceView!)
    view.bringSubviewToFront(faceView!)
  }
  
  override func viewDidAppear(animated: Bool) {
      super.viewDidAppear(animated)
      if !isRunning {
        self.establishVideoPreviewArea()
        isRunning = true
      }
  }
  
  func establishVideoPreviewArea() {
    self.preview = AVCaptureVideoPreviewLayer(session: self.camera?.session)
    self.preview?.videoGravity = AVLayerVideoGravityResizeAspectFill
    self.preview?.frame = self.cameraPreview.bounds
    self.preview?.cornerRadius = 0
    self.cameraPreview.layer.addSublayer(self.preview!)    

  }

  // MARK: Button Actions
  @IBAction func captureFrame(sender: AnyObject) {
    if self.status == .Preview {
        self.cameraStatus.text = "Capturing Photo"
        UIView.animateWithDuration(0.225, animations: { () -> Void in
          self.cameraPreview.alpha = 0.0
          self.cameraStatus.alpha = 1.0
        })
        
        self.camera?.captureStillImage({ (image) -> Void in
            if image != nil {
              self.cameraStill.image = image
              self.status = .Preview
            } else {
                self.cameraStatus.text = "Uh oh! Something went wrong. Try it again."
                self.status = .Error
            }
        })
      
    } else if self.status == .Still || self.status == .Error {
        UIView.animateWithDuration(0.225, animations: { () -> Void in
          self.cameraStill.alpha = 0.0
          self.cameraStatus.alpha = 0.0
          self.cameraPreview.alpha = 1.0
        }, completion: { (done) -> Void in
            self.cameraStill.image = nil;
            self.status = .Preview
        })
    }
  }
}

extension CameraViewController: CameraDelegate {
  // MARK: Camera Delegate
  func cameraSessionConfigurationDidComplete() {
    self.camera?.startCamera()
  }
  
  func cameraSessionDidBegin() {
    UIView.animateWithDuration(0.225, animations: { () -> Void in
      self.cameraPreview.alpha = 1.0
      self.cameraCapture.alpha = 1.0
    })
  }
  
  func cameraSessionDidStop() {
    UIView.animateWithDuration(0.225, animations: { () -> Void in
      self.cameraPreview.alpha = 0.0
      
    })
  }
  
  func camera(camera: Camera, didShowFaceDetect face: AVMetadataFaceObject) {
    let adjusted = self.preview?.transformedMetadataObjectForMetadataObject(face)
    dispatch_async(dispatch_get_main_queue()) { 
      self.faceView?.frame = (adjusted?.bounds)!
      
      // remove faceView
      let delayTime = dispatch_time(DISPATCH_TIME_NOW, Int64(1 * Double(NSEC_PER_SEC)))
      dispatch_after(delayTime, dispatch_get_main_queue()) {
        self.faceView?.frame = CGRect.zero
      }
    }
    
    
  }
  

}


// MARK: Private Method
extension CameraViewController {
  private func initializeCamera() {
    self.camera = Camera(sender: self)
  }
}

