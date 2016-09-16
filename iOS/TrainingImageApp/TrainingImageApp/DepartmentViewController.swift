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
  
  @IBAction func onNextAction(sender: UIButton) {
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
    cell?.accessoryType = .Checkmark
    checkedIndex = indexPath.row    
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
      let success = dict["success"] as! Int
      if success == 1 {
        print("Call api success")
        let str = dict["data"] as! String
        let dataSource : NSData = str.dataUsingEncoding(NSUTF8StringEncoding)!
        do {
          let dataBody = try NSJSONSerialization.JSONObjectWithData(dataSource, options: NSJSONReadingOptions(rawValue: 0)) as! [String : AnyObject]
          let content = dataBody["content"] as? [[String: AnyObject]] ?? []
          self.departmentData = Department.departments(array: content)
          print(self.departmentData)
          
          dispatch_async(dispatch_get_main_queue(), { 
            self.tableView.reloadData()
          })
          
        } catch let error as NSError {
          print(error)
        }
      } else {
        print("Fail")
      }
    }
  }
}