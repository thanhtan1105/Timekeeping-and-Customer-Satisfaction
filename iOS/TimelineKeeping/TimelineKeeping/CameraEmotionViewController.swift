//
//  CameraEmotionViewController.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import AVFoundation

class CameraEmotionViewController: UIViewController {

  @IBOutlet weak var cameraStill: UIImageView!
  @IBOutlet weak var cameraPreview: UIView!  
  @IBOutlet weak var cameraCapture: UIButton!
  
  var preview: AVCaptureVideoPreviewLayer?
  
  var isRunning = false
  var camera: Camera?
  var status: Status = .Preview
  var faceView: UIView?
  var isCameraTaken = false

  
  override func viewDidLoad() {
    super.viewDidLoad()
    cameraCapture.layer.cornerRadius = cameraCapture.frame.size.width / 2
    
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
  }


  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
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

  
  override func viewWillTransitionToSize(size: CGSize,
                                         withTransitionCoordinator coordinator: UIViewControllerTransitionCoordinator) {
    
    coordinator.animateAlongsideTransition({ (UIViewControllerTransitionCoordinatorContext) -> Void in
      
      let orient = UIApplication.sharedApplication().statusBarOrientation
      
      switch orient {
      case .Portrait:
        print("Portrait")
      // Do something
      default:
        print("Anything But Portrait")
        // Do something else
      }
      
      }, completion: { (UIViewControllerTransitionCoordinatorContext) -> Void in
        print("rotation completed")
    })
    
    super.viewWillTransitionToSize(size, withTransitionCoordinator: coordinator)
		}
  

  @IBAction func onCaptureTapped(sender: UIButton) {
    if self.status == .Preview {
      UIView.animateWithDuration(0.05, animations: { () -> Void in
        self.cameraPreview.alpha = 0.0
        self.cameraStill.alpha = 1.0
        
      })
      
      self.camera?.captureStillImage({ (image) -> Void in
        if image != nil {
          self.cameraStill.image = image
          self.status = .Preview
          self.callApiGetEmotion(self.cameraStill.image!, completion: { (emotion, error) in
            if let emotion = emotion {
              self.showInfoScren(emotion)
            } else {
              // fail
              self.cameraPreview.alpha = 1.0
              self.cameraStill.image = nil
            }
          })
          
        } else {
          self.status = .Error
        }
      })
    }
  }

}

extension CameraEmotionViewController: CameraDelegate {
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
          self.onCaptureTapped(UIButton())
        }
      }
      
    }
  }
}

extension CameraEmotionViewController {
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
  
  private func showInfoScren(emotion: [Emotion]) {
    let showInforVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("EmotionSuggestionViewController") as! EmotionSuggestionViewController
    showInforVC.emotion = emotion
    self.presentViewController(showInforVC, animated: true, completion: {
      self.camera?.stopCamera()
    })
  }
  
  private func callApiGetEmotion(faceImage: UIImage, completion onCompletionHandler: ((emotion: [Emotion]?, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.getEmotionSugesstion(self.cameraStill.image!, employeeId: 3, isFirstTime: true) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        onCompletionHandler!(emotion: nil, error: nil)
        return
      }
      print(response?.response)
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        print("Call api success")
        let content = dict["data"] as! [[String : AnyObject]]
        let emotion = Emotion.emotions(content)
        onCompletionHandler!(emotion: emotion, error: nil)
      } else {
        print("Fail")
        let errorCode = dict["errorCode"] as? String
        let message = dict["message"] as? String
        onCompletionHandler!(emotion: nil, error: NSError(domain: "", code: 0, userInfo: ["info" : message!]))
      }
    }
  }
  
}

