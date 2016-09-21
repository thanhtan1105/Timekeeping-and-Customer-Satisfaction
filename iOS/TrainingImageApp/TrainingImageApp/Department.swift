//
//  Department.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/16/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation


class Department: NSObject {
  
  var id: Int?
  var code: String?
  var name: String?
  var createTime: NSDate?
  var descriptions: String?
  var active: Bool?
  
  init?(_ info: [String: AnyObject]) {
    let active = info["active"] as? Bool
    let code = info["code"] as? String
//    let createTime = info["createTime"] as? NSTimeInterval
    let descriptions = info["description"] as? String
    let id = info["id"] as? Int
    let name = info["name"] as? String
    
    self.id = id
    self.active = active
    self.code = code
//    self.createTime = NSDate(timeIntervalSince1970: createTime!)
    self.name = name
    self.descriptions = descriptions
  }

  static func departments(array array: [[String: AnyObject]]) -> [Department] {
    var department = [Department]()
    for dictionary in array {
      let food = Department(dictionary)
      department.append(food!)
    }
    return department
  }

  // MARK: NSCoding
  required init(coder aDecoder: NSCoder) {
    id = aDecoder.decodeObjectForKey("id") as? Int
    code = aDecoder.decodeObjectForKey("code") as? String
    name = aDecoder.decodeObjectForKey("name") as? String
    createTime = aDecoder.decodeObjectForKey("createTime") as? NSDate
    descriptions = aDecoder.decodeObjectForKey("descriptions") as? String
    active = aDecoder.decodeObjectForKey("active") as? Bool
  }
  
  func encodeWithCoder(aCoder: NSCoder) {
    aCoder.encodeObject(id, forKey: "id")
    aCoder.encodeObject(code, forKey: "code")
    aCoder.encodeObject(name, forKey: "name")
    aCoder.encodeObject(createTime, forKey: "createTime")
    aCoder.encodeObject(descriptions, forKey: "descriptions")
    aCoder.encodeObject(active, forKey: "active")
  }
  
  static func getDepartmentFromUserDefault() -> Department {
    let userDefault = NSUserDefaults.standardUserDefaults()
    let data = userDefault.objectForKey("department") as? NSData
    let object = NSKeyedUnarchiver.unarchiveObjectWithData(data!) as! Department
    return object
  }
  
  static func saveToUserDefault(department department: Department) {
    let userDefault = NSUserDefaults.standardUserDefaults()
    let dataObject = NSKeyedArchiver.archivedDataWithRootObject(department)
    userDefault.setObject(dataObject, forKey: "department")
    NSUserDefaults.standardUserDefaults().synchronize()
  }

  
}