//
//  Beacon.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/20/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import RealmSwift

class Beacon: Object {
  dynamic var id = 0
  dynamic var name = ""
  dynamic var floor = 0
  dynamic var latitude = 0.0
  dynamic var longitude = 0.0
  dynamic var minjor = 0
  dynamic var major = 0
  dynamic var areaName = ""
  
  override static func primaryKey() -> String? {
    return "id"
  }
  
  convenience init(info: [String : AnyObject]) {
    self.init()
    let id = info["id"] as? Int
    let name = info["name"] as? String
    let floor = info["floor"] as? Int
    let latitude = info["latitude"] as? Double
    let longitude = info["longitude"] as? Double
    let minjor = info["minjor"] as? Int
    let major = info["major"] as? Int
    let areaName = info["areaName"] as? String

    self.id = id!
    self.name = name!
    self.floor = floor!
    self.latitude = latitude!
    self.longitude = longitude!
    self.minjor = minjor!
    self.major = major!
    self.areaName = areaName!
  }
  
  static func beaconList(array array: [[String : AnyObject]]) -> [Beacon] {
    var beaconList = [Beacon]()
    for dictionary in array {
      let item = Beacon(info: dictionary)
      beaconList.append(item)
    }
    return beaconList
  }
}