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

  var dataSource : Attendances! {
    didSet {
      // modified data
      let date = NSDate(timeIntervalSince1970: Double(dataSource.date!) / 1000.0)
      let dateFormatter = NSDateFormatter()
      dateFormatter.dateFormat = "dd/MM/yyyy"
      dateFormatter.timeZone = NSTimeZone()
      let localDate = dateFormatter.stringFromDate(date)
      timeLabel.text = localDate
      
      if dataSource.present == EPresent.Present {
        typeCheckinLabel.text = "Đã điểm danh trên hệ thống"
        typeCheckinLabel.textColor = UIColor(hex: 0x000000, alpha: 0.38)
        
        let checkInDate = NSDate(timeIntervalSince1970: Double(dataSource.timeCheck!) / 1000.0)
        let components = NSCalendar.currentCalendar().components([NSCalendarUnit.Hour, NSCalendarUnit.Minute],
                                                                 fromDate: checkInDate)
        let hour = String(components.hour)
        let minute = String(components.minute)
        let additionalText = String(" - " + hour + "h" + minute + "'")
        timeLabel.text = timeLabel.text! + additionalText
      } else {
        typeCheckinLabel.text = "Chưa diểm danh trên hệ thống"
        typeCheckinLabel.textColor = UIColor(hex: 0xFF5851, alpha: 1.0)
      }
      warningLabel.hidden = true
    }
  }
  
  override func awakeFromNib() {
    super.awakeFromNib()
  }

  
  
}
