//
//  NotificationViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class NotificationViewController: BaseViewController {

  @IBOutlet weak var tableView: UITableView!
  
  let user = Account.getAccount()
  var notifications: [Notification] = []
  override func viewDidLoad() {
    super.viewDidLoad()
  }
  
  override func viewWillAppear(animated: Bool) {
    super.viewWillAppear(animated)
    callApiGetReminder(String(5)) { (data, error) in
      dispatch_async(dispatch_get_main_queue(), {
        if let data = data {
          self.notifications = data
          if data.count == 0 {
            self.tableView.hidden = true
            
          } else {
            self.tableView.hidden = false
            
          }
          self.tableView.reloadData()
        }
      })
    }
  }
}

extension NotificationViewController: UITableViewDelegate, UITableViewDataSource {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return notifications.count
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier("NotificationTableCell") as! NotificationTableCell
    cell.dataSource = notifications[indexPath.row]
    return cell
  }
}

// MARK: - Private Method
extension NotificationViewController {
  private func callApiGetReminder(accoundID: String, completion onCompletion : ((data: [Notification]?, error: NSError?) -> Void)) {
    APIRequest.shareInstance.getNotification(accoundID) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail to get api")
        onCompletion(data: nil, error: NSError(domain: "com.domainName", code: 0, userInfo: ["info" : (error?.error_description)!]))
        return
      }
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        let data = dict["data"] as! [[String : AnyObject]]
        let notificationList = Notification.attendances(array: data)
        onCompletion(data: notificationList, error: nil)
      } else {
        onCompletion(data: nil, error: nil)
      }
      print(dict)
    }
  }
}