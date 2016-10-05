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
    beginTransaction()
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
        if self.cameraStill.image != nil {
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

// MARK: - Private method
extension CameraEmotionViewController {
  
  private func capture() {
    if isBeginTransaction {
      if self.status == .Preview {
        self.camera?.captureStillImage({ (image) -> Void in
          if image != nil {
            self.cameraStill.image = image
            self.status = .Preview
            self.currentTransaction(self.cameraStill.image!)   // current transaction
            
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
          }
        })
      }
    }
  }
  
  private func beginTransaction() {
    self.callApiBeginTransaction("5") { (shouldBeginTransaction, error) in
      if shouldBeginTransaction == true {
        self.startTransaction()   // send notify to server
      } else {
        let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(5 * Double(NSEC_PER_SEC)))
        dispatch_after(delayTime3, dispatch_get_main_queue()) {
          self.beginTransaction()
        }
      }
    }
  }
  
  private func startTransaction() {
    self.callApiStartTransaction(self.customerCode, completion: { (shouldStartTransaction, error) in
      if shouldStartTransaction == true {
        self.isBeginTransaction = true
        self.isEndTransaction = false
      } else {
        let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(5 * Double(NSEC_PER_SEC)))
        dispatch_after(delayTime3, dispatch_get_main_queue()) {
          self.startTransaction()
        }
      }
    })
  }
  
  private func currentTransaction(image: UIImage) {
    self.callApiCurrentTransaction(image, customerCode: customerCode) { (shouldEndTransaction, error) in
      if shouldEndTransaction == true {
        // end transaction
        self.isBeginTransaction = false
        self.isEndTransaction = true
        self.isCameraTaken = false
        self.customerCode = ""
        self.beginTransaction()
      }
    }
  }

  private func initializeCamera() {
    self.camera = Camera(sender: self, position: Camera.Position.Front)
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
  
  private func callApiBeginTransaction(employeeID: String, completion onCompletionHandler: ((shouldBeginTransaction: Bool?, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.beginTransaction(employeeID) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        onCompletionHandler!(shouldBeginTransaction: nil, error: nil)
        return
      }
      
      print(response?.response)

      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        let data = dict["data"] as! [String : AnyObject]
        let customerService = data["customerService"] as! [String : AnyObject]
        self.customerCode = customerService["customerCode"] as! String
        onCompletionHandler!(shouldBeginTransaction: true, error: nil)
        
      } else {
        print("Fail")
        onCompletionHandler!(shouldBeginTransaction: false, error: nil)
      }
      
    }
  }
  
  private func callApiStartTransaction(customerCode: String, completion onCompletionHandler: ((shouldStartTransaction: Bool?, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.startTransaction(customerCode) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        onCompletionHandler!(shouldStartTransaction: nil, error: nil)
        return
      }
      
      print(response?.response)
      
      let dict = response?.response as! [String: AnyObject]
      
      let success = dict["success"] as? Int
      if success == 1 {
        onCompletionHandler!(shouldStartTransaction: true, error: nil)
      } else {
        print("Fail")
        onCompletionHandler!(shouldStartTransaction: false, error: nil)
      }
    }
  }
  
  
  private func callApiCurrentTransaction(faceImage: UIImage, customerCode: String, completion onCompletionHandler: ((shouldEndTransaction: Bool?, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.processingTransaction(faceImage, customerCode: customerCode) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        return
      }
      print(response?.response)
      let dict = response?.response as! [String : AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        let data = dict["data"] as! [String : AnyObject]
        let shouldEndTransaction = data["shouldEndTransaction"] as! Bool
        onCompletionHandler!(shouldEndTransaction: shouldEndTransaction, error: nil)
      } else {
        print("Fail")
        onCompletionHandler!(shouldEndTransaction: false, error: nil)
      }
    }
  }
  
  
}

