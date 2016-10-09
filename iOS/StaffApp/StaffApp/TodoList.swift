//
//  TodoList.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/9/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class TodoList: NSObject {
  
  var id: Int?
  var title: String?
  var isComplete: Bool?
  var timeNotify: CLong?
  
  init?(_ info: [String: AnyObject]) {
    let id = info["id"] as? Int
    let title = info["title"] as? String
    let isComplete = info["isComplete"] as? Bool
    let timeNotify = info["timeNotify"] as? CLong
    
    self.id = id
    self.title = title
    self.isComplete = isComplete
    self.timeNotify = timeNotify
  }

  static func todoLists(array array: [[String : AnyObject]]) -> [TodoList] {
    var todoLists = [TodoList]()
    for dictionary in array {
      let item = TodoList(dictionary)
      todoLists.append(item!)
    }
    
    todoLists.sortInPlace { (first: TodoList, second: TodoList) -> Bool in
      return first.timeNotify > second.timeNotify
    }
    return todoLists
  }
  
}
