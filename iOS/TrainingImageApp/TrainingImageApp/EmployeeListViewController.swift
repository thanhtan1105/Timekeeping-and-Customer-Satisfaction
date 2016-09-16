//
//  EmployeeListViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class EmployeeListViewController: BaseViewController {

  @IBOutlet weak var tableView: UITableView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    callEmployeeListApi(start: 0, top: 100)
  }
}

extension EmployeeListViewController: UITableViewDataSource, UITableViewDelegate {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    return UITableViewCell()
  }
}

// MARK: - Private Method
extension EmployeeListViewController {
  
  func callEmployeeListApi(start start: Int, top: Int) {
    let department = Department.getDepartmentFromUserDefault();
    APIRequest.shareInstance.getAccountList(departmentId: department.code!, start: start, top: top) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail to get api")
        return
      }
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as! Int
      if success == 1 {
        print("Call api success")
        let str = dict["data"] as! String
        let dataSource : NSData = str.dataUsingEncoding(NSUTF8StringEncoding)!
        do {
          let dataBody = try NSJSONSerialization.JSONObjectWithData(dataSource, options: NSJSONReadingOptions(rawValue: 0)) as! [String : AnyObject]
          let content = dataBody["content"] as? [[String: AnyObject]] ?? []
          print(content)
          
        } catch let error as NSError {
          print(error)
        }
      } else {
        print("Fail")
      }
    }
  }
}