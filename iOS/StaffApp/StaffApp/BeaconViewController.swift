//
//  BeaconViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/10/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import CoreLocation
import RealmSwift

let top_margin = 150
let max_distance = 20

class BeaconViewController: BaseViewController, UIScrollViewDelegate {
  
  @IBOutlet weak var mapView: UIView!
  @IBOutlet weak var informationLabel: UILabel!
  @IBOutlet weak var scrollView: UIScrollView!
  @IBOutlet weak var textMapImageView: UIImageView!
  @IBOutlet weak var currentLocationLabel: UILabel!
  
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
  let realm = try! Realm()
  
  let imageView = UIImageView(image: UIImage(named: "RoomFPTMap"))
  
  let ESTIMOTE_PROXIMITY_UUID = NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
  
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
    self.scrollView.hidden = true
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
  private func identifyArea(beacon1: Beacon, beacon2: Beacon, beacon3: Beacon) -> String {
    self.scrollView.hidden = false
    var areaName : [String : Int] = [:]
    
    let area1Array: [String] = beacon1.areaName.characters.split{$0 == ","}.map(String.init)
    for key in area1Array {
      if areaName[key] == nil {
        areaName[key] = 0
      } else {
        areaName[key] = areaName[key]! + 1
      }
    }
    
    let area2Array: [String] = beacon2.areaName.characters.split{$0 == ","}.map(String.init)
    for key in area2Array {
      if areaName[key] == nil {
        areaName[key] = 0
      } else {
        areaName[key] = areaName[key]! + 1
      }
    }
    
    let area3Array: [String] = beacon3.areaName.characters.split{$0 == ","}.map(String.init)
    for key in area3Array {
      if areaName[key] == nil {
        areaName[key] = 0
      } else {
        areaName[key] = areaName[key]! + 1
      }
    }
    
    var maxCountBeacon: (areaName: String, count: Int) = ("", 0)
    for (key, value) in areaName {
      if value > maxCountBeacon.count {
        maxCountBeacon.areaName = key
        maxCountBeacon.count = value
      }
    }
    
    return maxCountBeacon.areaName
  }
  
  private func calculateDistance(beacons: [CLBeacon]) {
    var beaconFilter = beacons
    var i = 0
    while i != beaconFilter.count {
      let beacon = beaconFilter[i]
      if beacon.accuracy == -1.0 {
        beaconFilter.removeAtIndex(i)
      } else {
        i = i + 1
      }
    }
    
    if beaconFilter.count >= 3 {
      let sortListBeacons = beaconFilter.sort({ (first: CLBeacon, second: CLBeacon) -> Bool in
        return first.accuracy < second.accuracy
      })
      
      let d1 = sortListBeacons[0].accuracy
      print("D1: \(d1)\n")
      let d2 = sortListBeacons[1].accuracy
      print("D2: \(d2)\n")
      let d3 = sortListBeacons[2].accuracy
      print("D3: \(d3)\n")
      
      let beacon1 = realm.objects(Beacon.self).filter("major = \(sortListBeacons[0].major)").first
      let beacon2 = realm.objects(Beacon.self).filter("major = \(sortListBeacons[1].major)").first
      let beacon3 = realm.objects(Beacon.self).filter("major = \(sortListBeacons[2].major)").first
      
      let A = (x: beacon1!.latitude, y: beacon1!.longitude)
      let B = (x: beacon2!.latitude, y: beacon2!.longitude)
      let C = (x: beacon3!.latitude, y: beacon3!.longitude)
      
      if (d1 != -1.0 && d2 != -1.0 && d3 != -1.0) {
        let a1 = 2 * (B.x - A.x)
        let b1 = 2 * (B.y - A.y)
        let c1 = pow(d1, 2) - pow(d2, 2) + pow(B.x, 2) - pow(A.x, 2) + pow(B.y, 2) - pow(A.y, 2)
        
        let a2 = 2 * (C.x - A.x)
        let b2 = 2 * (C.y - A.y)
        let c2 = pow(d1, 2) - pow(d3, 2) + pow(C.x, 2) - pow(A.x, 2) + pow(C.y, 2) - pow(A.y, 2)
        
        let D = a1 * b2 - a2 * b1
        let Dx = c1 * b2 - c2 * b1
        let Dy = a1 * c2 - a2 * c1
        
        if D != 0 {
          let x = Dx / D
          let y = Dy / D
          print("x: \(x)")
          print("y: \(y)")
          // check beacon inside area
          let dAB: Double = pow(A.x - B.x, 2) + pow(A.y - B.y, 2)
          let dAC: Double = pow(A.x - C.x, 2) + pow(A.y - C.y, 2)
          let dBC: Double = pow(B.x - C.x, 2) + pow(B.y - C.y, 2)
          let maxD = max(dAB, dAC, dBC)
          let approximate : Double = 2.0
          switch maxD {
          case dAB:
            if A.x == C.x {
              // hinh 1
              if min(C.y, A.y) - approximate < y && y < max(C.y, A.y) + approximate &&
                min(B.x, C.x) + approximate < x && x < max(B.x, C.x) {
                print("I AM HERE dAB: ")
                currentLocationLabel.text = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                
              } else {
                print("Khong xac dinh")
              }
            } else {
              // hinh 2
              if min(C.y, B.y) - approximate < y && y < max(C.y, B.y) + approximate &&
                min(A.x, C.x) - approximate < x && x < max(A.x, C.x) + approximate {
                print("I AM HERE dAB: ")
                currentLocationLabel.text = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
              } else {
                print("Khong xac dinh")
              }
            }
            break
          case dAC:
            if A.x == B.x {
              // hinh 1
              if min(B.y, A.y) - approximate < y && y < max(B.y, A.y) + approximate &&
                min(C.x, B.x) - approximate < x && x < max(C.x, B.x) + approximate {
                print("I AM HERE dAC: ")
                currentLocationLabel.text = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
              } else {
                print("Khong xac dinh")
              }
              
            } else {
              // hinh 2
              if min(B.y, C.y) - approximate < y && y < max(B.y, C.y) + approximate &&
                min(B.x, A.x) - approximate < x && x < max(B.x, A.x) + approximate {
                print("I AM HERE dAC: ")
                currentLocationLabel.text = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)

              } else {
                print("Khong xac dinh")
              }
            }
            break
          case dBC:
            if A.x == B.x {
              // hinh 1
              if min(A.y, B.y) - approximate < y && y < max(A.y, B.y) + approximate &&
                min(A.x, C.x) - approximate < x && x < max(A.x, C.x) + approximate {
                print("I AM HERE dBC: ")
                currentLocationLabel.text = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
              } else {
                print("Khong xac dinh")
              }
            } else {
              // hinh 2
              if min(A.y, C.y) - approximate < y && y < max(A.y, C.y) + approximate &&
                min(A.x, B.x) - approximate < x && x < max(A.x, B.x) + approximate {
                print("I AM HERE dBC: ")
                currentLocationLabel.text = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
              } else {
                print("Khong xac dinh")
              }
            }
            break
            
          default:
            break
          }
          
        }
      }
    }
    
  }
}