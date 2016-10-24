//
//  Notification.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/7/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import RealmSwift

class Notification: NSObject {
  
  var id: Int?
  var message: String?
  var date: CLong?
  var title: String?
  var location: Point?
  
  init?(_ info: [String: AnyObject]) {
    let id = info["id"] as? Int
    let message = info["message"] as? String
    let time = info["time"] as? CLong
    let title = info["title"] as? String
    let location = info["location"] as? String
    
    self.id = id
    self.message = message
    self.date = time
    self.title = title
    
    // database
    let realm = try! Realm()
    self.location = realm.objects(Point.self).filter({ (point: Point) -> Bool in
      return point.name == location
    }).first
  }

  static func attendances(array array: [[String : AnyObject]]) -> [Notification] {
    var notification = [Notification]()
    for dictionary in array {
      let item = Notification(dictionary)
      notification.append(item!)
    }
    
    notification.sortInPlace { (first: Notification, second: Notification) -> Bool in
      return first.date > second.date
      // first.date > second.date
    }
    return notification
  }
}