//
//  TimeKeepingViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class TimeKeepingViewController: BaseViewController {

  @IBOutlet weak var coverImage: UIImageView!
  @IBOutlet weak var tableView: UITableView!
  @IBOutlet weak var avatarImage: UIImageView!
  @IBOutlet weak var employeeNameLabel: UILabel!
  
  let user = Account.getAccount()
  var attendance: [Attendances] = []
  
  override func viewDidLoad() {
    super.viewDidLoad()
    initLayout()
    tableView.delegate = self
    tableView.dataSource = self
    initLoadData()
  }
  
  override func viewWillAppear(animated: Bool) {
    super.viewWillAppear(animated)
  }
  
  func initLoadData() {
    let components = NSCalendar.currentCalendar().components([NSCalendarUnit.Year, NSCalendarUnit.Month], fromDate: NSDate())
    let month = components.month
    let year = components.year
    callApiGetAttendance(String(user!.id!), month: month, year: year) { (data, error) in
      if error != nil {
        // show error message
      } else {
        self.attendance = data!
        dispatch_async(dispatch_get_main_queue(), {
          self.tableView.reloadData()
        })
      }
    }
  }
  
  @IBAction func onSettingTapped(sender: UIBarButtonItem) {
    
  }
  
}

extension TimeKeepingViewController: UITableViewDelegate, UITableViewDataSource {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return attendance.count
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier("TimekeepingTableCell") as! TimekeepingTableCell
    cell.dataSource = attendance[indexPath.row]
    return cell
  }
}

// MARK: - Private method
extension TimeKeepingViewController {
  
  
  private func initLayout() {
    employeeNameLabel.text = user?.username
    
  }
  
  private func callApiGetAttendance(accountID: String, month: Int, year: Int, completion onCompletion : ((data: [Attendances]?, error: NSError?) -> Void)) {
    APIRequest.shareInstance.getAttendance(accountID, month: month, year: year) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print(error)
        onCompletion(data: nil, error: NSError(domain: "com.staffName", code: 0, userInfo: ["info" : (error?.error_description)!]))
        return
      }
      
      let dict = response?.response as! [String: AnyObject]
      let dataAttendances = dict["attendances"] as! [[String : AnyObject]]
      let attendances = Attendances.attendances(array: dataAttendances)
      onCompletion(data: attendances, error: nil)
    }
  }
}
