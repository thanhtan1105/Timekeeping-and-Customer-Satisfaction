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
  
  override func viewDidLoad() {
    super.viewDidLoad()
    initLayout()
  }
  
  override func viewWillAppear(animated: Bool) {
    super.viewWillAppear(animated)
    let components = NSCalendar.currentCalendar().components([NSCalendarUnit.Year, NSCalendarUnit.Month], fromDate: NSDate())
    let month = components.month
    let year = components.year
    callApiGetAttendance(String(user!.id!), month: month, year: year)
  }
  
  @IBAction func onSettingTapped(sender: UIBarButtonItem) {
    
  }
  
}

extension TimeKeepingViewController: UITableViewDelegate, UITableViewDataSource {
  
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

// MARK: - Private method
extension TimeKeepingViewController {
  private func initLayout() {
    employeeNameLabel.text = user?.username
    
  }
  
  private func callApiGetAttendance(accountID: String, month: Int, year: Int) {
    APIRequest.shareInstance.getAttendance(accountID, month: month, year: year) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print(error)
        return
      }
      
      print(response)
    }
  }
}
