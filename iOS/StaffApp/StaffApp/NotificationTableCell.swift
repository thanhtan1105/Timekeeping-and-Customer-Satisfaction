//
//  NotificationTableCell.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class NotificationTableCell: UITableViewCell {

  static let ClassName = "NotificationTableCell"
  
  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var descriptionLabel: UILabel!
  
  var dataSource: Notification! {
    didSet {
      // modified data
      let checkInDate = NSDate(timeIntervalSince1970: Double(dataSource.date!) / 1000.0)
      let components = NSCalendar.currentCalendar().components([NSCalendarUnit.Hour, NSCalendarUnit.Minute],
                                                               fromDate: checkInDate)
      let hour = String(components.hour)
      let minute = String(components.minute)
      let additionalText = String(" - " + hour + "h" + minute + "'")
      
      titleLabel.text = dataSource.title
      descriptionLabel.text = additionalText + " " + dataSource.message! ?? ""
    }
  }
  
  override func awakeFromNib() {
      super.awakeFromNib()
      // Initialization code
  }

}
