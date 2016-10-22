//
//  Points.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import RealmSwift

enum PointCategory {
  case Room = 1
  case Outsite
}
class Points: Object {
  
  dynamic var id = 0
  dynamic var name = ""
  dynamic var floor = 0
  dynamic var latitude = 0.0
  dynamic var longitude = 0.0
  dynamic var type = PointCategory.Room
  
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
    let type = info["type"] as? Int
    
    self.id = id!
    self.name = name!
    self.floor = floor!
    self.latitude = latitude!
    self.longitude = longitude!
    switch type {
    case 1:
      self.type = PointCategory.Room
      break
    default:
      break
    }
  }

  static func pointsList(array array: [[String : AnyObject]]) -> [Points] {
    var pointList = [Points]()
    for dictionary in array {
      let item = Points(info: dictionary)
      pointList.append(item)
    }
    return pointList
  }

  
}