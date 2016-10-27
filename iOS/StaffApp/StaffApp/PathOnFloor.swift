//
//  PathOnFloor.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/27/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class PathOnFloor {
  var floor: Int = 0
  var pathId: [Int] = []
  
  init?(_ info: [String: AnyObject]) {
    let floor = info["active"] as! Int
    let pathId = info["pathId"] as! [Int]
    
    self.floor = floor
    self.pathId = pathId
  }
  
  static func pathOnFloorArray(array array: [[String : AnyObject]]) -> [PathOnFloor] {
    var pathOnFloors = [PathOnFloor]()
    for dictionary in array {
      let item = PathOnFloor(dictionary)
      pathOnFloors.append(item!)
    }
    return pathOnFloors
  }
}