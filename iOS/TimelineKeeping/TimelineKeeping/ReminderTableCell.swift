//
//  ReminderTableCell.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/12/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class ReminderTableCell: UITableViewCell {
  
  static let ClassName = "ReminderTableCell"
  
  @IBOutlet weak var titleLabel: UILabel!
  @IBOutlet weak var timeLabel: UILabel!
  @IBOutlet weak var messageLabel: UILabel!
  
  var reminder : Reminder? {
    didSet {
      if let reminder = reminder {
        titleLabel.text = reminder.title
        messageLabel.text = reminder.message
        
        let date = NSDate(timeIntervalSince1970: Double(reminder.time!))
        let dateFormatter = NSDateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        let date24 = dateFormatter.stringFromDate(date)
        timeLabel.text = date24
      }
    }
  }
  
  override func awakeFromNib() {
      super.awakeFromNib()
  }      
}
