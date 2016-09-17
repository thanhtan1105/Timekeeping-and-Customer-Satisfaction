//
//  EmployeeTableViewCell.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/17/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class EmployeeTableCell: UITableViewCell {
  
  static let ClassName = "EmployeeTableCell"
  
  @IBOutlet weak var employeeNameLabel: UILabel!
  
  override func awakeFromNib() {
    super.awakeFromNib()
    // Initialization code
  }
  
  override func setSelected(selected: Bool, animated: Bool) {
    super.setSelected(selected, animated: animated)
    
    // Configure the view for the selected state
  }
  
}
