//
//  EDayStatus.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/5/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

enum EDayStatus {
  case Normal
  case DayBeforeCreate
  case DayAfterDeactive
  case DayOff
  case DayFuture
 
  static func setDayStatus(dayStatus: Int) -> EDayStatus {
    switch dayStatus {
    case 0:
      return .Normal
    case 1:
      return .DayBeforeCreate
    case 2:
      return .DayAfterDeactive
    case 3:
      return .DayOff
    case 4:
      return .DayFuture
    default:
      return .Normal
    }
  }
}

