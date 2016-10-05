//
//  TimekeepingTableCell.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class TimekeepingTableCell: UITableViewCell {

  static let ClassName = "TimekeepingTableCell"
  
  @IBOutlet weak var timeLabel: UILabel!
  @IBOutlet weak var typeCheckinLabel: UILabel!
  @IBOutlet weak var warningLabel: UILabel!

  override func awakeFromNib() {
    super.awakeFromNib()
  }

  
  
}
