//
//  ShowInfoViewController.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/12/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class ShowInfoViewController: UIViewController {

  @IBOutlet weak var nameLabel: UILabel!
  @IBOutlet weak var tableView: UITableView!
  @IBOutlet weak var closeButton: UIButton!

  var checkImage: UIImage!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    
    APIRequest.shareInstance.identifyImage(checkImage) { (response: ResponsePackage?, error: ErrorWebservice?) in
      print(response?.response)
    }
  }
  
  @IBAction func onCloseButtonTapped(sender: UIButton) {
    dismissViewControllerAnimated(true, completion: nil)
    
  }
  
}

extension ShowInfoViewController: UITableViewDelegate, UITableViewDataSource {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return 5
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier(ReminderTableCell.ClassName) as! ReminderTableCell
    return cell
  }
}