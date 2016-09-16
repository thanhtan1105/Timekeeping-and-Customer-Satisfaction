//
//  Camera.swift
//  dojo-custom-camera
//
//  Created by David McGraw on 11/13/14.
//  Copyright (c) 2014 David McGraw. All rights reserved.
//

import UIKit
import AVFoundation

protocol CameraDelegate: class {
  func cameraSessionConfigurationDidComplete()
  func cameraSessionDidBegin()
  func cameraSessionDidStop()
  func camera(camera: Camera, didShowFaceDetect face: AVMetadataFaceObject)
}

class Camera: NSObject {
    
  weak var delegate: CameraDelegate?
  
  var session: AVCaptureSession!
  var sessionQueue: dispatch_queue_t!
  var stillImageOutput: AVCaptureStillImageOutput?
  var metadataOutput: AVCaptureMetadataOutput?

  init(sender: AnyObject) {
    super.init()
    self.delegate = sender as? CameraDelegate
  
    self.setObservers()
    self.initializeSession()
  }
  
  deinit {
    self.removeObservers()
  }
  
  // MARK: Session
  
  func initializeSession() {
    session = AVCaptureSession()
    session.sessionPreset = AVCaptureSessionPresetPhoto
    sessionQueue = dispatch_queue_create("camera session", DISPATCH_QUEUE_SERIAL)
    
    dispatch_async(self.sessionQueue) {
      self.session.beginConfiguration()
      self.addVideoInput()
      self.addStillImageOutput()
      self.session.commitConfiguration()

      self.metadataOutput = AVCaptureMetadataOutput()
      self.metadataOutput!.setMetadataObjectsDelegate(self, queue: self.sessionQueue)
      self.session.addOutput(self.metadataOutput!)
      self.metadataOutput!.metadataObjectTypes = [AVMetadataObjectTypeFace]
      
      dispatch_async(dispatch_get_main_queue()) {
          print("Session initialization did complete")
          self.delegate?.cameraSessionConfigurationDidComplete()
      }
    }
  }
  
  func startCamera() {
    
      dispatch_async(self.sessionQueue) {
          self.session.startRunning()
      }
  }

  func stopCamera() {
      dispatch_async(self.sessionQueue) {
          self.session.stopRunning()
      }
  }
  
  func captureStillImage(completed: (image: UIImage?) -> Void) {
      // kiểm tra output chỉ duy nhất là hình
      if let imageOutput = self.stillImageOutput {
          // chạy trên 1 session queue khác
          dispatch_async(self.sessionQueue, { () -> Void in
            var videoConnection: AVCaptureConnection?
            videoConnection?.videoOrientation = AVCaptureVideoOrientation.Portrait
            for connection in imageOutput.connections {
                let c = connection as! AVCaptureConnection
                c.videoOrientation = AVCaptureVideoOrientation.Portrait
              
                for port in c.inputPorts {
                    let p = port as! AVCaptureInputPort
                    if p.mediaType == AVMediaTypeVideo {
                        videoConnection = c;
                        break
                    }
                }
                
                if videoConnection != nil {
                    break
                }
            }
            
            if videoConnection != nil {
                imageOutput.captureStillImageAsynchronouslyFromConnection(videoConnection, completionHandler: { (imageSampleBuffer: CMSampleBufferRef!, error) -> Void in
                    let imageData = AVCaptureStillImageOutput.jpegStillImageNSDataRepresentation(imageSampleBuffer)
                    let image: UIImage? = UIImage(data: imageData!)!
                    
                    dispatch_async(dispatch_get_main_queue()) {
                        completed(image: image)
                    }
                })
            } else {
                dispatch_async(dispatch_get_main_queue()) {
                    completed(image: nil)
                }
            }
          })
      } else {
          completed(image: nil)
      }
  }
  
  
  // MARK: Configuration
  func addVideoInput() {
    let device: AVCaptureDevice = self.deviceWithMediaTypeWithPosition(AVMediaTypeVideo, position: AVCaptureDevicePosition.Front)
    do {
        let input = try AVCaptureDeviceInput(device: device)
        if self.session.canAddInput(input) {
            self.session.addInput(input)
        }
    } catch {
        print(error)
    }
  }
  
  func addStillImageOutput() {
      stillImageOutput = AVCaptureStillImageOutput()
      stillImageOutput?.outputSettings = [AVVideoCodecKey: AVVideoCodecJPEG]
      
      if self.session.canAddOutput(stillImageOutput) {
          session.addOutput(stillImageOutput)
      }
  }
  
  func deviceWithMediaTypeWithPosition(mediaType: NSString, position: AVCaptureDevicePosition) -> AVCaptureDevice {
      let devices: NSArray = AVCaptureDevice.devicesWithMediaType(mediaType as String)
      var captureDevice: AVCaptureDevice = devices.firstObject as! AVCaptureDevice
      for device in devices {
          let d = device as! AVCaptureDevice
          if d.position == position {
              captureDevice = d
              break;
          }
      }
      return captureDevice
  }
  
  // MARK: Observers
  
  func setObservers() {
      NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(Camera.sessionDidStart(_:)), name: AVCaptureSessionDidStartRunningNotification, object: nil)
      NSNotificationCenter.defaultCenter().addObserver(self, selector: #selector(Camera.sessionDidStop(_:)), name: AVCaptureSessionDidStopRunningNotification, object: nil)
  }
  
  func removeObservers() {
      NSNotificationCenter.defaultCenter().removeObserver(self)
  }
  
  func sessionDidStart(notification: NSNotification) {
      dispatch_async(dispatch_get_main_queue()) {
          NSLog("Session did start")
          self.delegate?.cameraSessionDidBegin()
      }
  }
  
  func sessionDidStop(notification: NSNotification) {
      dispatch_async(dispatch_get_main_queue()) {
          NSLog("Session did stop")
          self.delegate?.cameraSessionDidStop()
      }
  }
  
}

extension Camera: AVCaptureMetadataOutputObjectsDelegate {
  func captureOutput(captureOutput: AVCaptureOutput!, didOutputMetadataObjects metadataObjects: [AnyObject]!, fromConnection connection: AVCaptureConnection!) {
    for object in metadataObjects {
      if object.type == AVMetadataObjectTypeFace {
        let face = object as! AVMetadataFaceObject
        delegate?.camera(self, didShowFaceDetect: face)
      }
    }
  }
}
