//
//  NotificationDetailViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class NotificationDetailViewController: BaseViewController {

  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var timeLabel: UILabel!
  @IBOutlet weak var locationLabel: UILabel!
  @IBOutlet weak var reminderLabel: UILabel!
  @IBOutlet weak var boundView: UIView!
  
  var notification: Notification!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    initData()
  }

  @IBAction func onDirectionTapped(sender: UIButton) {
    let beaconVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("BeaconViewController") as! BeaconViewController
    beaconVC.destinationPointId = notification.location?.id
    self.navigationController?.pushViewController(beaconVC, animated: true)
  }
  
}

// MARK: - Private method
extension NotificationDetailViewController {
  private func initData() {
    titleLabel.text = notification.title
    
    // date
    let checkInDate = NSDate(timeIntervalSince1970: Double(notification.date!) / 1000.0)
    let components = NSCalendar.currentCalendar().components([NSCalendarUnit.Hour, NSCalendarUnit.Minute],
                                                             fromDate: checkInDate)
    let hour = String(components.hour)
    let minute = String(components.minute)
    timeLabel.text = String(hour + "h" + minute)

    reminderLabel.text = notification.message
    locationLabel.text = notification.location?.name
      
    boundView.layer.borderColor = UIColor.lightGrayColor().CGColor
    boundView.layer.borderWidth = 2.0
    boundView.layer.cornerRadius = 5.0
    
  }
}
