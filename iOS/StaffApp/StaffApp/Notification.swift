//
//  Notification.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/7/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Notification: NSObject {
  
  var id: Int?
  var message: String?
  var date: CLong?
  var title: String?
  
  init?(_ info: [String: AnyObject]) {
    let id = info["id"] as? Int
    let message = info["message"] as? String
    let time = info["time"] as? CLong
    let title = info["title"] as? String
    
    self.id = id
    self.message = message
    self.date = time
    self.title = title
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