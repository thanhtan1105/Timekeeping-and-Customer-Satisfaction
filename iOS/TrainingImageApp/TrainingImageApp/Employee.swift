//
//  Employee.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/17/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Employee: NSObject {
  
  var username: String?
  var departmentId: Int?
  var fullName: String?
  var roleId: Int?
  var employeeCode: String?
  var active: Bool?
  
  init?(_ info: [String: AnyObject]) {
    let active = info["active"] as? Bool
    let roleId = info["roleId"] as? Int
    let userCode = info["userCode"] as? String
    let username = info["username"] as? String
    let fullname = info["fullname"] as? String
    
    self.username = username
    self.roleId = roleId
    self.employeeCode = userCode
    self.active = active
    self.fullName = fullname    
  }

  static func employees(array array: [[String: AnyObject]]) -> [Employee] {
    var employees = [Employee]()
    for dictionary in array {
      let employee = Employee(dictionary)
      employees.append(employee!)
    }
    return employees
  }
  
  // MARK: NSCoding
  required init(coder aDecoder: NSCoder) {
    username = aDecoder.decodeObjectForKey("username") as? String
    active = aDecoder.decodeObjectForKey("active") as? Bool
    roleId = aDecoder.decodeObjectForKey("roleId") as? Int
    employeeCode = aDecoder.decodeObjectForKey("userCode") as? String
    fullName = aDecoder.decodeObjectForKey("fullname") as? String

  }
  
  func encodeWithCoder(aCoder: NSCoder) {
    aCoder.encodeObject(username, forKey: "username")
    aCoder.encodeObject(active, forKey: "active")
    aCoder.encodeObject(roleId, forKey: "roleId")
    aCoder.encodeObject(employeeCode, forKey: "userCode")
    aCoder.encodeObject(fullName, forKey: "fullname")            
  }

  static func getEmployeeFromUserDefault() -> Employee {
    let userDefault = NSUserDefaults.standardUserDefaults()
    let data = userDefault.objectForKey("employee") as? NSData
    let object = NSKeyedUnarchiver.unarchiveObjectWithData(data!) as! Employee
    return object
  }
  
  static func saveToUserDefault(employee employee: Employee) {
    let userDefault = NSUserDefaults.standardUserDefaults()
    let dataObject = NSKeyedArchiver.archivedDataWithRootObject(employee)
    userDefault.setObject(dataObject, forKey: "employee")
    NSUserDefaults.standardUserDefaults().synchronize()
  }
}