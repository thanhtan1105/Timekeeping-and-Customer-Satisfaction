//
//  TrainingImageViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/16/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class TrainingImageViewController: BaseViewController {

  override func viewDidLoad() {
    super.viewDidLoad()
  }
  
  override func viewWillAppear(animated: Bool) {
    navigationController?.setNavigationBarHidden(true, animated: false)    
  }
  
  @IBAction func onSettingTapped(sender: UIButton) {
    let alert = UIAlertController(title: "IP address", message: "", preferredStyle: .Alert)
    let okAction = UIAlertAction(title: "OK", style: .Default) { (action: UIAlertAction) in
      if alert.textFields?.count > 0 {
        let textField = alert.textFields?.first
        let newIP = textField?.text
        NSUserDefaults.standardUserDefaults().setObject(newIP, forKey: "ip")
      }
    }
    
    let cancelAction = UIAlertAction(title: "Cancel", style: .Cancel) { (action: UIAlertAction) in
      
    }
    
    alert.addTextFieldWithConfigurationHandler { (textField: UITextField) in
      textField.text = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? ""
    }
    alert.addAction(okAction)
    alert.addAction(cancelAction)
    self.presentViewController(alert, animated: true, completion: nil)
  }
}
