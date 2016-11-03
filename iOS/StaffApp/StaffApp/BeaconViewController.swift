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
let var_distance = 20
let scale = 25.0

class BeaconViewController: BaseViewController, UIScrollViewDelegate {
  
  // layout variable
  @IBOutlet weak var mapView: UIView!
  @IBOutlet weak var informationLabel: UILabel!
  @IBOutlet weak var scrollView: UIScrollView!
  @IBOutlet weak var textMapImageView: UIImageView!
  @IBOutlet weak var currentLocationLabel: UILabel!
  @IBOutlet weak var topView: UIView!
  @IBOutlet weak var nextButton: UIButton!

  // beacon variable
  var beaconManager: ESTBeaconManager? = nil
  var region: CLBeaconRegion!
  var beacons: [CLBeacon] = [] {
    didSet {
      // update layout
      calculateDistance(beacons)
    }
  }
  let ESTIMOTE_PROXIMITY_UUID = NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
  
  // point
  var sourcePoint: Point!
  var destinationPoint: Point!
  var sourcePointName: String!
  var destinationPointId: Int!
  
  // database
  let realm = try! Realm()
  
  // map image
  var imageView = UIImageView(image: UIImage(named: "floor1"))
  var currentFloor = -1
  var pathOnFloor: [PathOnFloor] = []
  
  override func viewDidLoad() {
    super.viewDidLoad()
    configureBeacon()
    
    imageView.translatesAutoresizingMaskIntoConstraints = true
    scrollView.addSubview(imageView)
    scrollView.contentSize = imageView.frame.size    
//    self.scrollView.maximumZoomScale = 5.0
//    self.scrollView.minimumZoomScale = 0.5
    self.scrollView.delegate = self
    self.scrollView.hidden = true
    nextButton.hidden = true
    topView.hidden = true
    topView.layer.borderColor = UIColor(hex: 0xff0202).CGColor
    nextButton.layer.borderColor = UIColor(hex: 0xff0202).CGColor
    
    // show loading
    LeThanhTanLoading.sharedInstance.showLoadingAddedTo(self.view, title: "Loading...", animated: true)
    
    // hide loading
//    let delayTime3 = dispatch_time(DISPATCH_TIME_NOW, Int64(1 * Double(NSEC_PER_SEC)))
//    dispatch_after(delayTime3, dispatch_get_main_queue()) {
//      LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
//      self.scrollView.hidden = false
//    }

////     init source Point
//    sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
//      return point.name == "107"
//    }).first
//    currentFloor = sourcePoint.floor
//    showSourcePoint(sourcePoint)

    // dumping
    destinationPoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
      return point.id == destinationPointId
    }).first
    
  }
  
  @IBAction func onDirectTapped(sender: UIBarButtonItem) {
    // compaire
    if sourcePoint.floor != destinationPoint.floor {
      nextButton.hidden = false
    }
    // show find path
    if let destinationPoint = destinationPoint {
      showFindPath(sourcePoint, destinatePoint: destinationPoint)
    } else {
      // print 
      print("You don't have destination point")
    }
  }
  
  @IBAction func onNextFloorTapped(sender: UIButton) {
    // remove path
    let viewPath = imageView.viewWithTag(3)
    if viewPath != nil {
      viewPath?.removeFromSuperview()
    }

    let viewPath2 = imageView.viewWithTag(4)
    if viewPath2 != nil {
      viewPath2?.removeFromSuperview()
    }
    
    let viewPath3 = imageView.viewWithTag(5)
    if viewPath3 != nil {
      viewPath3?.removeFromSuperview()
    }
    
    currentFloor = pathOnFloor[0].floor == currentFloor ? pathOnFloor[1].floor : pathOnFloor[0].floor
    let floorName = "floor" + String(currentFloor)
    imageView.image = UIImage(named: floorName)

    showSourcePoint(sourcePoint)
    showDestinatePoint(destinationPoint)

    let index = pathOnFloor.indexOf { (pathOnFloor: PathOnFloor) -> Bool in
      return currentFloor == pathOnFloor.floor
    }
    let pathIds: [Int] = self.pathOnFloor[index!].pathId
    drawPathToMap(pathIds)
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
                let location = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
                  return point.name == location.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                }).first
                showSourcePoint(sourcePoint)
              } else {
                print("Khong xac dinh")
              }
            } else {
              // hinh 2
              if min(C.y, B.y) - approximate < y && y < max(C.y, B.y) + approximate &&
                min(A.x, C.x) - approximate < x && x < max(A.x, C.x) + approximate {
                print("I AM HERE dAB: ")
                let location = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
                  return point.name == location.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                }).first
                showSourcePoint(sourcePoint)
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
                let location = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
                  return point.name == location.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                }).first
                showSourcePoint(sourcePoint)

              } else {
                print("Khong xac dinh")
              }
              
            } else {
              // hinh 2
              if min(B.y, C.y) - approximate < y && y < max(B.y, C.y) + approximate &&
                min(B.x, A.x) - approximate < x && x < max(B.x, A.x) + approximate {
                print("I AM HERE dAC: ")
                let location = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
                  return point.name == location.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                }).first
                showSourcePoint(sourcePoint)

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
                let location = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
                  return point.name == location.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                }).first
                showSourcePoint(sourcePoint)
              } else {
                print("Khong xac dinh")
              }
            } else {
              // hinh 2
              if min(A.y, C.y) - approximate < y && y < max(A.y, C.y) + approximate &&
                min(A.x, B.x) - approximate < x && x < max(A.x, B.x) + approximate {
                print("I AM HERE dBC: ")
                let location = identifyArea(beacon1!, beacon2: beacon2!, beacon3: beacon3!)
                sourcePoint = realm.objects(Point.self).filter({ (point: Point) -> Bool in
                  return point.name == location.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
                }).first
                showSourcePoint(sourcePoint)
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
  
  private func identifyArea(beacon1: Beacon, beacon2: Beacon, beacon3: Beacon) -> String {
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
  
  private func showSourcePoint(sourcePoint: Point) {
    // hide loading animation
    dispatch_async(dispatch_get_main_queue()) { 
      LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
      self.scrollView.hidden = false
      self.topView.hidden = false
      // disable next floor
      if sourcePoint.floor == self.destinationPoint.floor {
        self.nextButton.hidden = true
      }
    }
    
    let point = realm.objects(Point.self).filter { (point: Point) -> Bool in
      return point.name == sourcePoint.name && point.typeRaw == PointCategory.Room.descriptions
    }.first
    
    if let point = point {
      if currentFloor == -1 || point.floor == currentFloor {
        currentFloor = point.floor
        // show point on Map
        let templeView2 = view.viewWithTag(4)
        if templeView2 != nil {
          templeView2?.removeFromSuperview()
        }
        let pointInMap = UIView(frame: CGRect(x: point.latitude * scale - 10.0 , y: point.longitude * scale - 10.0, width: 20.0, height: 20.0))
        pointInMap.layer.cornerRadius = 10.0
        pointInMap.backgroundColor = UIColor.blueColor()
        
        // add image to pointInMap View
        let imagePoint = UIImageView(frame: CGRect(x: 0, y: 0, width: 20, height: 20))
        imagePoint.image = UIImage(named: "points")
        pointInMap.addSubview(imagePoint)
        
        // create the image inside current point
        pointInMap.tag = 4
        self.imageView.addSubview(pointInMap)
        
        // change map
        let floorName = "floor" + String(point.floor)
        self.imageView.image = UIImage(named: floorName)
        
        // update topView
        currentLocationLabel.text = point.toString()
        
        // animation 
        resizeAnimation(pointInMap)
        
        // show destination point
        showDestinatePoint(destinationPoint)
      }      
    }
  }
  
  private func showDestinatePoint(sourcePoint: Point) {
    let point = realm.objects(Point.self).filter { (point: Point) -> Bool in
      return point.name == sourcePoint.name && point.typeRaw == PointCategory.Room.descriptions
      }.first
    
    if point != nil {
      if point?.floor == currentFloor {
        // show point on Map
        let templeView2 = view.viewWithTag(5)
        if templeView2 != nil {
          templeView2?.removeFromSuperview()
        }
        let pointInMap = UIView(frame: CGRect(x: (point?.latitude)! * scale - 10.0 , y: (point?.longitude)! * scale - 10.0, width: 20.0, height: 20.0))
        pointInMap.layer.cornerRadius = 10.0
        pointInMap.backgroundColor = UIColor.greenColor()
        pointInMap.tag = 5
        self.imageView.addSubview(pointInMap)
        
      }
    }
  }
  
  private func showFindPath(sourcePoint: Point, destinatePoint: Point) {
    APIRequest.shareInstance.findShorestPart(sourcePoint.id, destinateId: destinatePoint.id) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("show find path")
        return
      }
      
      let responseData = response?.response as! [String : AnyObject]
      let success = responseData["success"] as! Int
      if success == 1 {
        let data = responseData["data"] as! [String : AnyObject]
        let paths = data["paths"] as! [[String : AnyObject]]
        self.pathOnFloor = PathOnFloor.pathOnFloorArray(array: paths)
        self.nextButton.enabled = self.pathOnFloor.count > 1 ? true : false
//        let distance = data["distance"] as? Double
        let pathIds: [Int] = self.pathOnFloor[0].pathId
        self.currentFloor = self.pathOnFloor[0].floor
        self.drawPathToMap(pathIds)
      }
    }
  }
  
  private func drawPathToMap(pathId: [Int]) {
    // get data Source
    var points: [(x: Float, y: Float)] = []
    for id in pathId {
      let point = realm.objects(Point.self).filter({ (point: Point) -> Bool in
        return point.id == id
      }).first
      points.append((Float((point?.latitude)!), Float((point?.longitude)!)))
    }

    let viewPath = view.viewWithTag(3)
    if viewPath != nil {
      viewPath?.removeFromSuperview()
    }
    let drawPath: DrawPath = DrawPath(frame: imageView.frame)
    drawPath.tag = 3
    drawPath.backgroundColor = UIColor.clearColor()
    drawPath.dataSource = points
    drawPath.layer.cornerRadius = 5
    drawPath.layer.masksToBounds = true
    
    if view.viewWithTag(4) != nil {
      imageView.insertSubview(drawPath, belowSubview: view.viewWithTag(4)!)
    } else {
      imageView.addSubview(drawPath)
    }
    
  }
  
  private func configureBeacon() {
    beaconManager = ESTBeaconManager()
    self.region = CLBeaconRegion(proximityUUID: ESTIMOTE_PROXIMITY_UUID!, identifier: "EstimoteSampleRegion")
    beaconManager!.delegate = self
    beaconManager!.requestAlwaysAuthorization()
  }
}

// MARK: - Animation
extension BeaconViewController {
  private func resizeAnimation(component: UIView) {
    UIView.animateWithDuration(0.6, animations: {
      component.transform = CGAffineTransformMakeScale(1.5, 1.5)
      }, completion: { finish in
        UIView.animateWithDuration(0.6, animations: {
          component.transform = CGAffineTransformIdentity
          }, completion: { (finish: Bool) in
            self.resizeAnimation(component)
        })
    })
  }
}