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

class BeaconViewController: BaseViewController {
  
  @IBOutlet weak var mapView: UIView!
  @IBOutlet weak var informationLabel: UILabel!
  
  @IBOutlet weak var textMapImageView: UIImageView!
  var beacon: CLBeacon? = nil
  var beaconManager: ESTBeaconManager? = nil
  var positionDot: UIImageView!
  var utilityManager: ESTUtilityManager!
  var region: CLBeaconRegion!
  var beacons: [CLBeacon] = [] {
    didSet {
      // update layout
      calculateDistance(beacons)
    }
  }
  
  
  let ESTIMOTE_PROXIMITY_UUID = NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
  let A = (x: 54.0, y: 88.0)
  let B = (x: 300.0, y: 190.0)
  let C = (x: 120.0, y: 260.0)
  
  override func viewDidLoad() {
    super.viewDidLoad()
    beaconManager = ESTBeaconManager()
    self.region = CLBeaconRegion(proximityUUID: ESTIMOTE_PROXIMITY_UUID!, identifier: "EstimoteSampleRegion")
    beaconManager!.delegate = self
    beaconManager!.requestAlwaysAuthorization()
    
    let line1 = UIView(frame: CGRect(x: A.x, y: A.y, width: 5.0, height: 5.0))
    line1.backgroundColor = UIColor.blackColor()
    line1.tag = 0
    self.textMapImageView.addSubview(line1)
    
    let line2 = UIView(frame: CGRect(x: B.x, y: B.y, width: 5.0, height: 5.0))
    line2.backgroundColor = UIColor.redColor()
    line2.tag = 1
    self.textMapImageView.addSubview(line2)
    
    let line3 = UIView(frame: CGRect(x: C.x, y: C.y, width: 5.0, height: 5.0))
    line3.backgroundColor = UIColor.blueColor()
    line3.tag = 0
    self.textMapImageView.addSubview(line3)
    
    
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


      // dump
      let indexD1 = beacons.indexOf { (beacon: CLBeacon) -> Bool in
        return beacon.major == 1
      }
      let d1 = beacons[indexD1!].accuracy * 128
      print("D1: \(d1)\n")
      let indexD2 = beacons.indexOf { (beacon: CLBeacon) -> Bool in
        return beacon.major == 2
      }
      let d2 = beacons[indexD2!].accuracy * 128
      print("D2: \(d2)\n")
      let indexD3 = beacons.indexOf { (beacon: CLBeacon) -> Bool in
        return beacon.major == 3
      }
      let d3 = beacons[indexD3!].accuracy * 128
      print("D3: \(d3)\n")
      
      if (d1 != -1.0 && d2 != -1.0 && d3 != -1.0) {
        let aContant: Double = (pow(A.x, 2) + pow(A.y, 2) - pow(B.x, 2) - pow(B.y, 2) - pow(d1, 2) + pow(d2, 2)) / 2
        let aInFunction: Double = (pow(A.x - B.x, 2)) / (pow(A.y - B.y, 2))
        var bInFunction: Double = ( (2 * A.y * (A.x - B.x)) / (A.y - B.y) ) - (2 * A.x)
        bInFunction = bInFunction - ( (2 * aContant * (A.x - B.x)) / (pow(A.y - B.y, 2)))
        
        var cInFunction: Double = pow(A.x, 2) + pow(A.y, 2) - ((2 * A.y * aContant) / (A.y - B.y)) - pow(d1, 2)
        cInFunction = cInFunction + ( (pow(aContant, 2)) / (pow(A.y - B.y, 2)))

        let x1: Double = (-bInFunction + sqrt((bInFunction * bInFunction) - 4 * aInFunction * cInFunction)) / (2 * aInFunction)
        let x2: Double = (-bInFunction - sqrt((bInFunction * bInFunction) - 4 * aInFunction * cInFunction)) / (2 * aInFunction)
        
        let y1: Double = (aContant - (x1 * (A.x - B.x))) / (A.y - B.y)
        let y2: Double = (aContant - (x2 * (A.x - B.x))) / (A.y - B.y)
        
        let checkAgainD3X1: Double = (C.x - x1) * (C.x - x1) - (C.y - y1) * (C.y - y1)
        let checkAgainD3X2: Double = (C.x - x2) * (C.x - x2) - (C.y - y2) * (C.y - y2)
        
        print("X1: \(x1) \n X2: \(x2)\n")
        print("Check Again X1: \(checkAgainD3X1) \nCheck Again X1: \(checkAgainD3X2) \n")
//        if (!x1.isNaN) {
//          let templeView1 = view.viewWithTag(2)
//          if templeView1 != nil {
//            templeView1?.removeFromSuperview()
//          }
//          let line3 = UIView(frame: CGRect(x: x1, y: y1, width: 10.0, height: 10.0))
//          line3.backgroundColor = UIColor.redColor()
//          line3.tag = 2
//          self.textMapImageView.addSubview(line3)
//        }
        
        if (!x2.isNaN) {
          let templeView2 = view.viewWithTag(3)
          if templeView2 != nil {
            templeView2?.removeFromSuperview()
          }
          let line4 = UIView(frame: CGRect(x: x2 / 50 , y: y2 / 50, width: 10.0, height: 10.0))
          line4.backgroundColor = UIColor.greenColor()
          line4.tag = 3
          self.textMapImageView.addSubview(line4)
        }
      }

    }
    
  }
}




