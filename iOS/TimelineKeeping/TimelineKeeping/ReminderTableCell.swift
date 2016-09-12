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
  
  override func awakeFromNib() {
      super.awakeFromNib()
  }      
}
