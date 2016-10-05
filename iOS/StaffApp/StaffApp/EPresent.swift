//
//  EPresent.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/5/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation


enum EPresent {
  case Present
  case Absent
  
  static func setPresent(present: Int) -> EPresent {
    switch present {
    case 0:
      return .Present
    default:
      return .Absent
    }
  }
}
