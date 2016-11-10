//
//  AddTaskViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/9/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class AddTaskViewController: UIViewController {

  @IBOutlet weak var dateTimePicker: UIDatePicker!
  @IBOutlet weak var titleDescription: UITextView!
  @IBOutlet weak var dateButton: UIButton!
  
  var date = NSDate()
  
  override func viewDidLoad() {
    super.viewDidLoad()
    titleDescription.layer.cornerRadius = 5
    titleDescription.layer.borderWidth = 1
    titleDescription.layer.borderColor = UIColor.blackColor().CGColor
    
    // add default param
    let dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
    let currentDate = dateFormatter.stringFromDate(NSDate())
    dateButton.setTitle(currentDate, forState: .Normal)
    
    // add gesture
    let gesture = UITapGestureRecognizer(target: self, action: #selector(AddTaskViewController.hideKeyboard(_:)))
    self.view.addGestureRecognizer(gesture)
  }

  func hideKeyboard(sender: UITapGestureRecognizer) {
    titleDescription.resignFirstResponder()
  }

  @IBAction func onDatePickerChange(sender: UIDatePicker) {
    let dateFormatter = NSDateFormatter()
    dateFormatter.dateFormat = "yyyy-MM-dd HH:mm:ss"
    let selectedDate = dateFormatter.stringFromDate(sender.date)
    date = sender.date
    dateButton.setTitle(selectedDate, forState: .Normal)
  }

  
  @IBAction func onSaveButtonTapped(sender: UIBarButtonItem) {
    // Call api
    let title = titleDescription.text
    let timeStamp: Int = Int(date.timeIntervalSince1970)
    let timeNotify = String(timeStamp)
    let accountId = String(Account.getAccount()!.id!)
    LeThanhTanLoading.sharedInstance.showLoadingAddedTo(self.view, animated: true)
    APIRequest.shareInstance.createTask(title, timeNotify: timeNotify, accountId: accountId) { (response: ResponsePackage?, error: ErrorWebservice?) in
      LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
      guard error == nil else {
        self.showPopUp(error?.error_description ?? "Fail" )
        return
      }
      
      let dict = response?.response as! [String : AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        self.showPopUp("Successfully")
      } else {
        self.showPopUp("Fail")
      }
    }
  }
}

extension AddTaskViewController {
  private func showPopUp(message: String) {
    let alertVC = UIAlertController(title: "Add Task", message: message, preferredStyle: .Alert)
    let okButton = UIAlertAction(title: "OK", style: .Default) { (action: UIAlertAction) in
      
    }
    alertVC.addAction(okButton)
    presentViewController(alertVC, animated: true, completion: nil)
  }
}
