//
//  AddTaskViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/9/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class AddTaskViewController: UIViewController {

  @IBOutlet weak var dateView: UIView!
  @IBOutlet weak var titleDescription: UITextView!
  @IBOutlet weak var dateButton: UIButton!
  @IBOutlet weak var cancelButton: UIButton!
  @IBOutlet weak var doneButton: UIButton!

  @IBOutlet weak var dateTimePicker: UIDatePicker!
  
  override func viewDidLoad() {
      super.viewDidLoad()

      // Do any additional setup after loading the view.
  }


  @IBAction func onDateChange(sender: UIButton) {
    dateView.hidden = !dateView.hidden
    
  }
  
}
