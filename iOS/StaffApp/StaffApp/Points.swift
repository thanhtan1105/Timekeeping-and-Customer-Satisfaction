//
//  Points.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import RealmSwift

enum PointCategory: String {
  case Room
  case Outsite
  
  var descriptions: String {
    switch self {
    case .Room:
      return "Room"
    case .Outsite:
      return "Outsite"
    }
  }
}

class Point: Object {
  
  dynamic var id = 0
  dynamic var name = ""
  dynamic var floor = 0
  dynamic var latitude = 0.0
  dynamic var longitude = 0.0
  dynamic var typeRaw = ""
  
  var type: PointCategory! {
    didSet {
      self.typeRaw = self.type.descriptions
    }
  }
  
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
    let type1 = info["type"] as! Int
    
    self.id = id!
    self.name = name ?? ""
    self.floor = floor!
    self.latitude = latitude!
    self.longitude = longitude!
    switch type1 {
    case 1:
      self.type = .Room
      self.typeRaw = self.type.descriptions
      break
    case 0:
      self.type = .Outsite
      self.typeRaw = self.type.descriptions
      break
    default:
      break
    }
  }

  static func pointsList(array array: [[String : AnyObject]]) -> [Point] {
    var pointList = [Point]()
    for dictionary in array {
      let item = Point(info: dictionary)
      pointList.append(item)
    }
    return pointList
  }

  func toString() -> String {
    if name.characters.count > 0 {
      return name + ", lầu " + String(floor)
    } else {
      return "lầu " + String(floor)
    }
    
  }
}