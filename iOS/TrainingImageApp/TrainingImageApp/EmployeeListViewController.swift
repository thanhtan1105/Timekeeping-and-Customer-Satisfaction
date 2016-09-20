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
  
  var employees: [Employee] = []
  var checkedIndex : Int?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    callEmployeeListApi(start: 0, top: 100)
  }
  
  @IBAction func onNextTapped(sender: UIBarButtonItem) {
    if checkedIndex == nil {
      let alert = UIAlertController(title: "Error", message: "Choice least one Employee", preferredStyle: .Alert)
      alert.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
        
      }))
      navigationController?.presentViewController(alert, animated: true, completion: nil)
      return
    }
    
    let data = employees[checkedIndex!]
    Employee.saveToUserDefault(employee: data)
    
    let storyboard = UIStoryboard(name: "Main", bundle: nil)
    let vc = storyboard.instantiateViewControllerWithIdentifier("FaceListViewController") as! FaceListViewController
    self.navigationController?.pushViewController(vc, animated: true)
  }
}

extension EmployeeListViewController: UITableViewDataSource, UITableViewDelegate {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return employees.count
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier(EmployeeTableCell.ClassName) as! EmployeeTableCell
    let data = employees[indexPath.row]
    cell.employeeNameLabel.text = data.username
    return cell
  }
  
  func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
    tableView.deselectRowAtIndexPath(indexPath, animated: true)
    let cell = tableView.cellForRowAtIndexPath(indexPath)
    if cell?.accessoryType == .Checkmark {
      cell?.accessoryType = .None
      checkedIndex = nil
    } else {
      cell?.accessoryType = .Checkmark
      checkedIndex = indexPath.row
    }
    
    tableView.reloadData()

  }
}

// MARK: - Private Method
extension EmployeeListViewController {
  
  func callEmployeeListApi(start start: Int, top: Int) {
    let department = Department.getDepartmentFromUserDefault();
    APIRequest.shareInstance.getAccountList(departmentId: department.id!, start: start, top: top) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail to get api")
        return
      }
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        print("Call api success")
        let data = dict["data"] as! [[String : AnyObject]]
        self.employees = Employee.employees(array: data)
        dispatch_async(dispatch_get_main_queue(), {
          self.tableView.reloadData()
        })
      } else {
        print("Fail")
      }

    }
  }
}