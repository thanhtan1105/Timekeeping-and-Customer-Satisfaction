//
//  ReminderModel.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/26/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Reminder {
  
  var id: Int?
  var title: String?
  var message: String?
  var time: Int?
  
  init?(_ info: [String: AnyObject]) {
    let id = info["id"] as? Int
    let message = info["message"] as? String
    let title = info["title"] as? String
    let time = info["time"] as? Int
    
    self.id = id
    self.title = title
    self.message = message
    self.time = time
  }
}