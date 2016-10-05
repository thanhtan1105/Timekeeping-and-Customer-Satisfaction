//
//  Attendances.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/5/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Attendances: NSObject {
  
  var day: Int?
  var date: CLong?
  var present: EPresent?
  var timeCheck: CLong?
  var note: String?
  var dayStatus: EDayStatus?
  
  init?(_ info: [String: AnyObject]) {
    let day = info["day"] as? Int
    let date = info["date"] as? CLong
    let present = info["present"] as? Int
    let timeCheck = info["timeCheck"] as? CLong
    let note = info["note"] as? String
    let dayStatus = info["dayStatus"] as? Int
    
    self.day = day
    self.date = date
    self.present = EPresent.setPresent(present ?? 1)
    self.timeCheck = timeCheck
    self.note = note
    self.dayStatus = EDayStatus.setDayStatus(dayStatus ?? 0)
    
  }
  
  static func attendances(array array: [[String : AnyObject]]) -> [Attendances] {
    var attendances = [Attendances]()
    for dictionary in array {
      let item = Attendances(dictionary)
      if item?.dayStatus != EDayStatus.DayFuture {
        attendances.append(item!)
      }
    }
    
    attendances.sortInPlace { (first: Attendances, second: Attendances) -> Bool in
      return first.date > second.date
    }
    return attendances
  }
}