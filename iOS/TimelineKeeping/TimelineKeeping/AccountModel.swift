//
//  AccountModel.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Account {
  
  var id : Int?
  var role: Role?
  var token: Int?
  var userCode: String?
  var username: String?
  var timeCheckin: Int?
  var active: Bool?
  var fullName: String?
  
  init?(_ info: [String: AnyObject]) {
    let active = info["active"] as? Bool
    let fullname = info["fullname"] as? String
    let id = info["id"] as? Int
    let token = info["token"] as? Int
    let userCode = info["userCode"] as? String
    let username = info["username"] as? String
    let timeCheckin = info["timeCheckIn"] as? Int
    
    self.active = active
    self.fullName = fullname
    self.id = id
    self.token = token
    self.userCode = userCode
    self.username = username
    self.timeCheckin = timeCheckin
  }

  
  
}



class Role {
  
  var id: String?
  var name: String?
  
}