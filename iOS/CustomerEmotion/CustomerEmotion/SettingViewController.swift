//
//  SettingViewController.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/28/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class SettingViewController: BaseViewController {

  @IBOutlet weak var ipTextField: UITextField!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    let ip = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? "192.168.43.93"
    ipTextField.text = ip
    
  }

  @IBAction func onSaveTapped(sender: UIBarButtonItem) {
    let ip = ipTextField.text
    NSUserDefaults.standardUserDefaults().setObject(ip, forKey: "ip")
    NSUserDefaults.standardUserDefaults().synchronize()
    ipTextField.resignFirstResponder()
  }
  
  
}
