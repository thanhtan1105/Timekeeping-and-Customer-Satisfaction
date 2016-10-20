//
//  BeaconViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/10/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import CoreLocation

let top_margin = 150
let max_distance = 20

class BeaconViewController: BaseViewController, UIScrollViewDelegate {
  
  @IBOutlet weak var mapView: UIView!
  @IBOutlet weak var informationLabel: UILabel!
  @IBOutlet weak var scrollView: UIScrollView!
  @IBOutlet weak var textMapImageView: UIImageView!
  
  var beacon: CLBeacon? = nil
  var beaconManager: ESTBeaconManager? = nil
  var positionDot: UIImageView!
  var utilityManager: ESTUtilityManager!
  var region: CLBeaconRegion!
  let scale = 120.0
  var beacons: [CLBeacon] = [] {
    didSet {
      // update layout
      calculateDistance(beacons)
    }
  }
  
  let imageView = UIImageView(image: UIImage(named: "RoomFPTMap"))
  
  let ESTIMOTE_PROXIMITY_UUID = NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
  let C = (x: 108.0, y: 174.0)
  let B = (x: 216.0, y: 126.0)
  let A = (x: 372.0, y: 216.0)
  
  override func viewDidLoad() {
    super.viewDidLoad()
    beaconManager = ESTBeaconManager()
    self.region = CLBeaconRegion(proximityUUID: ESTIMOTE_PROXIMITY_UUID!, identifier: "EstimoteSampleRegion")
    beaconManager!.delegate = self
    beaconManager!.requestAlwaysAuthorization()
    
    imageView.translatesAutoresizingMaskIntoConstraints = true
    scrollView.addSubview(imageView)
    scrollView.contentSize = imageView.frame.size    
    self.scrollView.maximumZoomScale = 5.0
    self.scrollView.minimumZoomScale = 0.5
    self.scrollView.delegate = self
    
    
    let line1 = UIView(frame: CGRect(x: A.x, y: A.y, width: 10.0, height: 10.0))
    line1.backgroundColor = UIColor.redColor()
    line1.tag = 0
    self.imageView.addSubview(line1)

    let line2 = UIView(frame: CGRect(x: B.x, y: B.y, width: 10.0, height: 10.0))
    line2.backgroundColor = UIColor.greenColor()
    line2.tag = 1
    self.imageView.addSubview(line2)
    
    let line3 = UIView(frame: CGRect(x: C.x, y: C.y, width: 10.0, height: 10.0))
    line3.backgroundColor = UIColor.blueColor()
    line3.tag = 0
    self.imageView.addSubview(line3)

  }
  

  override func viewDidDisappear(animated: Bool) {
    
  }

}

extension BeaconViewController: ESTBeaconManagerDelegate {
  func beaconManager(manager: AnyObject, didChangeAuthorizationStatus status: CLAuthorizationStatus) {
    beaconManager?.startRangingBeaconsInRegion(self.region)
  }
  func beaconManager(manager: AnyObject, didRangeBeacons beacons: [CLBeacon], inRegion region: CLBeaconRegion) {
    if beacons.count > 0 {
      print("Number of beacon: \(beacons.count)")
      self.beacons = beacons
    }
  }
}

// MARK :- Private method
extension BeaconViewController {
  private func calculateDistance(beacons: [CLBeacon]) {
    if beacons.count >= 3 {
      let indexD1 = beacons.indexOf { (beacon: CLBeacon) -> Bool in
        return beacon.major == 1
      }
      let d1 = beacons[indexD1!].accuracy * scale
      print("D1: \(d1)\n")
      let indexD2 = beacons.indexOf { (beacon: CLBeacon) -> Bool in
        return beacon.major == 2
      }
      let d2 = beacons[indexD2!].accuracy * scale
      print("D2: \(d2)\n")
      let indexD3 = beacons.indexOf { (beacon: CLBeacon) -> Bool in
        return beacon.major == 3
      }
      let d3 = beacons[indexD3!].accuracy * scale
      print("D3: \(d3)\n")
      
      if (d1 != -1.0 * scale && d2 != -1.0 * scale && d3 != -1.0 * scale) {
        let a1 = 2 * (B.x - A.x)
        let b1 = 2 * (B.y - A.y)
        let c1 = pow(d1, 2) - pow(d2, 2) + pow(B.x, 2) - pow(A.x, 2) + pow(B.y, 2) - pow(A.y, 2)
        
        let a2 = 2 * (C.x - A.x)
        let b2 = 2 * (C.y - A.y)
        let c2 = pow(d1, 2) - pow(d2, 2) + pow(C.x, 2) - pow(A.x, 2) + pow(C.y, 2) - pow(A.y, 2)
        
        let D = a1 * b2 - a2 * b1
        let Dx = c1 * b2 - c2 * b1
        let Dy = a1 * c2 - a2 * c1
        
        if D != 0 {
          let x = Dx / D
          let y = Dy / D
          let templeView2 = view.viewWithTag(3)
          if templeView2 != nil {
            templeView2?.removeFromSuperview()
          }
          let line4 = UIView(frame: CGRect(x: x , y: y, width: 10.0, height: 10.0))
          line4.backgroundColor = UIColor.greenColor()
          line4.tag = 3
          self.imageView.addSubview(line4)
        }
      }

    }
    
  }
}




