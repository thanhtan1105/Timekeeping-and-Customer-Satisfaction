//
//  CameraEmotionViewController.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import AVFoundation

enum Status: Int {
  case Preview, Still, Error
}

class CameraEmotionViewController: UIViewController {

  @IBOutlet weak var cameraStill: UIImageView!
  @IBOutlet weak var cameraPreview: UIView!
  @IBOutlet weak var beginTransactionButton: UIButton!
  
  
  var preview: AVCaptureVideoPreviewLayer?
  var isRunning = false
  var camera: Camera?
  var status: Status = .Preview
  var faceView: UIView?
  var isCameraTaken = false
  var isBeginTransaction = false
  var isEndTransaction = true
  var customerCode = ""
  
  
  override func viewDidLoad() {
    super.viewDidLoad()
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
    LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
    isCameraTaken = false
    isBeginTransaction = false
    isEndTransaction = true
    status = .Preview
  }
  
  override func viewDidAppear(animated: Bool) {
    super.viewDidAppear(animated)
    if !isRunning {
      self.establishVideoPreviewArea()
      isRunning = true
    }
  }
  
  override func viewDidDisappear(animated: Bool) {
    super.viewDidDisappear(animated)
    if self.tabBarController?.selectedIndex == 1 {
      self.camera?.stopCamera()
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
    isBeginTransaction = true
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
//      self.cameraCapture.alpha = 1.0
    })
  }
  
  func cameraSessionDidStop() {
    UIView.animateWithDuration(0.225, animations: { () -> Void in
      self.cameraPreview.alpha = 0.0
      
    })
  }
  
  // show face detection
  func camera(camera: Camera, didShowFaceDetect face: AVMetadataFaceObject) {
    let adjusted = self.preview?.transformedMetadataObjectForMetadataObject(face)
    dispatch_async(dispatch_get_main_queue()) {
      if self.isBeginTransaction {
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
          
          let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(1 * Double(NSEC_PER_SEC)))
          dispatch_after(delayTime3, dispatch_get_main_queue()) {
            self.capture()
          }
        }
        
      }
      
    }
  }
}

extension CameraEmotionViewController: EmotionSuggestionDelegate {
  func emotionSuggestionController(didEndTransaction emotionSuggestionController: EmotionSuggestionViewController) {
    endTransaction()
  }
}

// MARK: - Private method
extension CameraEmotionViewController {
  
  private func capture() {
    if isBeginTransaction {
      if self.status == .Preview {
        if self.customerCode == "" {
          UIView.animateWithDuration(0.05, animations: { () -> Void in
            self.cameraPreview.alpha = 0.0
            self.cameraStill.alpha = 1.0
            LeThanhTanLoading.sharedInstance.showLoadingAddedTo(self.view, animated: true)
          })
        }
        
        self.camera?.captureStillImage({ (image) -> Void in
          if image != nil {
            self.cameraStill.image = image
            self.status = .Preview
            if self.customerCode == "" {
              // begin transaction
              self.beginTransaction(self.cameraStill.image!)
            } else {
              // current transaction
              self.currentTransaction(self.cameraStill.image!)
            }
            
            if !self.isEndTransaction {
              // delay
              let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(10 * Double(NSEC_PER_SEC)))
              dispatch_after(delayTime3, dispatch_get_main_queue()) {
                self.isCameraTaken = false
              }
            }
          } else {
            self.status = .Error
            self.isCameraTaken = false
            LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
          }
        })
      }
    }
  }
  
  private func beginTransaction(image: UIImage) {
    self.callApiBeginTransaction(image, completion: { (emotionResponse, error) in
      if let emotionResponse = emotionResponse {
        self.isEndTransaction = false
        // delay
        let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(10 * Double(NSEC_PER_SEC)))
        dispatch_after(delayTime3, dispatch_get_main_queue()) {
          self.isCameraTaken = false
        }
        self.showInfoScren(emotionResponse)
      } else {
        // fail
        self.cameraPreview.alpha = 1.0
        self.cameraStill.image = nil
        self.isCameraTaken = false
        LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
      }
    })
  }
  
  private func currentTransaction(image: UIImage) {
    self.callApiCurrentTransaction(image, customerCode: customerCode)
  }
  
  private func endTransaction() {
    self.callApiEndTransaction(customerCode)
  }
  
  private func initializeCamera() {
    self.camera = Camera(sender: self, position: Camera.Position.Back)
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
  
  private func showInfoScren(emotionResponse: EmotionResponse) {
    let showInforVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("EmotionSuggestionViewController") as! EmotionSuggestionViewController
    showInforVC.delegate = self
    showInforVC.emotions = emotionResponse.emotions!  // emotions data
    if let suggestMessage = emotionResponse.suggestMessages {
      showInforVC.suggestMessages = suggestMessage  // suggest message data
    }
    self.presentViewController(showInforVC, animated: true, completion: {
      // start new thread to take the picture in background
      
    })
  }
  
  typealias EmotionResponse = (emotions: [Emotion]?, suggestMessages: [Message]?) // new type
  private func callApiBeginTransaction(faceImage: UIImage, completion onCompletionHandler: ((emotionResponse: EmotionResponse?, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.beginTransaction(faceImage, employeeId: 5) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        onCompletionHandler!(emotionResponse: nil, error: nil)
        return
      }
      print(response?.response)
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        print("Call api success")
        let data = dict["data"] as! [String : AnyObject]
        
        // customer code
        self.customerCode = data["customerCode"] as! String
        
        // emotions
        let emotions = data["analyzes"] as! [[String : AnyObject]]
        var emotion: [Emotion] = []
        
        if emotions.count > 0 {
          emotion = Emotion.emotions(emotions)
          
          // suggest message
          let suggestMessages = data["messages"] as! [[String : AnyObject]]
          if suggestMessages.count > 0 {
            let messages = Message.messages(suggestMessages)
            onCompletionHandler!(emotionResponse: (emotion, messages), error: nil)
          } else {
            onCompletionHandler!(emotionResponse: (emotion, nil), error: nil)
          }
          
        } else {
          // cannot detect image
          onCompletionHandler!(emotionResponse: nil, error: NSError(domain: "", code: 0, userInfo: ["info" : "Cannot detect image"]))
        }
      } else {
        print("Fail")
        onCompletionHandler!(emotionResponse: nil, error: NSError(domain: "", code: 0, userInfo: ["info" : "Cannot detect image"]))
      }
    }
  }
  
  private func callApiCurrentTransaction(faceImage: UIImage, customerCode: String) {
    APIRequest.shareInstance.processingTransaction(faceImage, customerCode: customerCode) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        return
      }
      print(response?.response)
    }
  }
  
  private func callApiEndTransaction(customerCode: String) {
    APIRequest.shareInstance.endTransaction(customerCode) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        return
      }
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      print(success)
      self.isBeginTransaction = false
      self.customerCode = ""
      self.isEndTransaction = true
    }
  }
  
}

