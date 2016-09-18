//
//  DepartmentViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class DepartmentViewController: BaseViewController {

  @IBOutlet weak var tableView: UITableView!
  
  var departmentData: [Department] = []
  var checkedIndex : Int?
  
  override func viewDidLoad() {
    super.viewDidLoad()
    callApiDepartment(start: 0, top: 100)
  }
  
  @IBAction func onNextAction(sender: UIBarButtonItem) {
    if checkedIndex == nil {
      let alert = UIAlertController(title: "Error", message: "Choice least one department", preferredStyle: .Alert)
      alert.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
        
      }))
      navigationController?.presentViewController(alert, animated: true, completion: nil)
      return
    }

    let data = departmentData[checkedIndex!]
    Department.saveToUserDefault(department: data)
    
    let storyboard = UIStoryboard(name: "Main", bundle: nil)
    let vc = storyboard.instantiateViewControllerWithIdentifier("EmployeeListViewController") as! EmployeeListViewController
    self.navigationController?.pushViewController(vc, animated: true)
  }

}

extension DepartmentViewController: UITableViewDelegate, UITableViewDataSource {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return departmentData.count
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier(DepartmentTableCell.ClassName) as! DepartmentTableCell
    cell.departmentLabel.text = departmentData[indexPath.row].name
    
    if checkedIndex == indexPath.row {
      cell.accessoryType = .Checkmark
    } else {
      cell.accessoryType = .None
    }
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
extension DepartmentViewController {
  
  func callApiDepartment(start start: Int, top: Int) {
    APIRequest.shareInstance.getDepartmentList(start, top: top) { (response: ResponsePackage?, error: ErrorWebservice?) in
      print(response?.response)
      guard error == nil else {
        print("Fail to get api")
        return
      }
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        print("Call api success")
        let data = dict["data"] as! [String : AnyObject]
        let content = data["content"] as! [[String : AnyObject]]
        self.departmentData = Department.departments(array: content)
        print(self.departmentData)

        dispatch_async(dispatch_get_main_queue(), {
          self.tableView.reloadData()
        })
      } else {
        print("Fail")
      }
    }
  }
  
  
  func callAPI2(start start:Int, top: Int, completion:((Int)-> Void?)) {
    
  }
}