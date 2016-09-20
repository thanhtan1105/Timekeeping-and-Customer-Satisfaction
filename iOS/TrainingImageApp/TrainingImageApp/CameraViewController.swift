//
//  CameraViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import AVFoundation

enum Status: Int {
  case Preview, Still, Error
}

protocol CameraViewControllerDelegate: class {
  func cameraViewController(cameraViewController: CameraViewController, didFinishUploadFace image: UIImage)
}

class CameraViewController: BaseViewController {

  @IBOutlet weak var cameraStill: UIImageView!
  @IBOutlet weak var cameraPreview: UIView!
  @IBOutlet weak var cameraCapture: UIButton!
  @IBOutlet weak var retakeButton: UIButton!
  @IBOutlet weak var captureButton: UIButton!
  @IBOutlet weak var usePhotoButton: UIButton!

  var isRunning = false
  var preview: AVCaptureVideoPreviewLayer?
  var camera: Camera?
  var status: Status = .Preview
  var faceView: UIView?
  var isCameraTaken = false
  weak var delegate: CameraViewControllerDelegate?
    
  override func viewDidLoad() {
    super.viewDidLoad()
    layoutBeforeCapture()
    
    faceView = UIView()
    faceView?.layer.borderColor = UIColor.greenColor().CGColor
    faceView?.layer.borderWidth = 2
    faceView?.tag = 280394
    view.addSubview(faceView!)
    view.bringSubviewToFront(faceView!)

  }
  
  override func viewWillAppear(animated: Bool) {
    super.viewWillAppear(animated)
    self.initializeCamera()
    
    self.cameraStill.image = nil
    self.cameraPreview.alpha = 1.0
    isRunning = false
    
    let delayTime = dispatch_time(DISPATCH_TIME_NOW, Int64(5 * Double(NSEC_PER_SEC)))
    dispatch_after(delayTime, dispatch_get_main_queue()) {
      self.isCameraTaken = false
      
    }
    
  }
  
  override func viewDidAppear(animated: Bool) {
    super.viewDidAppear(animated)
    if !isRunning {
      self.establishVideoPreviewArea()
      isRunning = true
    }
  }

  
  @IBAction func onRetakeTapped(sender: UIButton) {
    self.isCameraTaken = false
    UIView.animateWithDuration(0.05, animations: { () -> Void in
      self.cameraPreview.alpha = 1.0
      self.cameraStill.image = nil
      self.cameraStill.alpha = 0.0
      dispatch_async(dispatch_get_main_queue(), {
        self.layoutBeforeCapture()
      })
    })
  }
  
  @IBAction func onCancelTapped(sender: UIButton) {
    dismissViewControllerAnimated(true, completion: nil)
  }
  
  @IBAction func onCaptureTapped(sender: UIButton) {
    if self.status == .Preview {
      self.isCameraTaken = true
      UIView.animateWithDuration(0.05, animations: { () -> Void in
        self.cameraPreview.alpha = 0.0
        self.cameraStill.alpha = 1.0
        
        
        dispatch_async(dispatch_get_main_queue(), { 
          self.layoutAfterCapture()
        })
      })
      
      self.camera?.captureStillImage({ (image) -> Void in
        if image != nil {
          self.cameraStill.image = image
          self.status = .Preview
        } else {
          self.status = .Error
        }
      })
    }
  }
  
  @IBAction func onUsePhotoTapped(sender: UIButton) {
    callApiAddFaceToPerson { (isSuccess, error) in
      // error of network
      guard error == nil else {
        let alertVC = UIAlertController(title: "Please try again", message: "Cannot detect face", preferredStyle: .Alert)
        alertVC.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
          
        }))
        self.presentViewController(alertVC, animated: true, completion: nil)
        return
      }
      
      let alertVC = UIAlertController(title: "Success", message: "Face has added to person", preferredStyle: .Alert)
      alertVC.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
        self.delegate?.cameraViewController(self, didFinishUploadFace: self.cameraStill.image!)
        self.dismissViewControllerAnimated(true, completion: nil)
      }))
      self.presentViewController(alertVC, animated: true, completion: nil)
      
      
      
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
      if self.cameraStill.image == nil {
        // filter
        dispatch_async(dispatch_get_main_queue(), {
          if let adjusted = adjusted {
            self.faceView?.frame = adjusted.bounds
          }
          
        })
        
        let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(1 * Double(NSEC_PER_SEC)))
        dispatch_after(delayTime3, dispatch_get_main_queue()) {
          self.faceView?.frame = CGRect.zero
        }
      }
      
      if self.isCameraTaken == false {
        self.isCameraTaken = true
        let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(0.75 * Double(NSEC_PER_SEC)))
        dispatch_after(delayTime3, dispatch_get_main_queue()) {
//          self.captureFrame(UIButton())
        }
        
      }
      
    }
  }
}


// MARK: Private Method
extension CameraViewController {
  
  private func establishVideoPreviewArea() {
    self.preview = AVCaptureVideoPreviewLayer(session: self.camera?.session)
    self.preview?.videoGravity = AVLayerVideoGravityResizeAspectFill
    self.preview?.frame = self.cameraPreview.bounds
    self.preview?.cornerRadius = 0
    self.cameraPreview.layer.addSublayer(self.preview!)
  }
  
  private func initializeCamera() {
    self.camera = Camera(sender: self)
  }
  
  func videoOrientationFromCurrentDeviceOrientation() -> AVCaptureVideoOrientation {
    switch UIApplication.sharedApplication().statusBarOrientation {
    case .Portrait:
      return AVCaptureVideoOrientation.Portrait
    case .LandscapeLeft:
      return AVCaptureVideoOrientation.LandscapeLeft
    case .LandscapeRight:
      return AVCaptureVideoOrientation.LandscapeRight
    case .PortraitUpsideDown:
      return AVCaptureVideoOrientation.PortraitUpsideDown
    default:
      // Can it happens?
      return AVCaptureVideoOrientation.Portrait
    }
  }
  
  private func layoutBeforeCapture() {
    retakeButton.hidden = true
    usePhotoButton.hidden = true
    captureButton.hidden = false
  }
  
  private func layoutAfterCapture() {
    retakeButton.hidden = false
    usePhotoButton.hidden = false
    captureButton.hidden = true
  }
  
  private func callApiAddFaceToPerson(completion onCompletionHandler: ((isSuccess: Bool, error: NSError?) -> Void)?) {
    // get data
    let personGroupId = String(Department.getDepartmentFromUserDefault().code!)
    let personId = Employee.getEmployeeFromUserDefault().id!
    
    APIRequest.shareInstance.addFaceToPerson(personGroupId, personId: personId, imageFace: cameraStill.image!) { (response: ResponsePackage?, error: ErrorWebservice?) in
      // error of network
      guard error == nil else {
        print("Fail")
        return
      }
//      ["data": {
//        persistedFaceId = "1062b2b5-7120-4091-9c2f-550de17cdabe";
//        }, "message": <null>, "errorCode": <null>, "success": 1]
// 
      let data = response?.response as! [String : AnyObject]
      let success = data["success"] as! Bool
      if success == true {
        onCompletionHandler!(isSuccess: true, error: nil)
      } else {
        let failMessage = data["message"] as! String
        onCompletionHandler!(isSuccess: false, error: NSError(domain: "com.trainingImage", code: 100, userInfo: ["info" : failMessage]))
      }
      
      print(response?.response)
    }
  }
}

