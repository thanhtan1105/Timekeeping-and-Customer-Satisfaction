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
    navigationController?.setNavigationBarHidden(false, animated: true)
    ipTextField.text = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? ""    
  }

  @IBAction func onSaveTapped(sender: UIBarButtonItem) {
    let ip = ipTextField.text
    NSUserDefaults.standardUserDefaults().setObject(ip, forKey: "ip")
    NSUserDefaults.standardUserDefaults().synchronize()
    ipTextField.resignFirstResponder()
  }

  @IBAction func onLogoutTapped(sender: UIButton) {
    let userDefault = NSUserDefaults.standardUserDefaults()
    userDefault.setObject(0, forKey: "didLogin")
    
    let storyboard = UIStoryboard(name: "Main", bundle: nil)
    let loginViewController = storyboard.instantiateViewControllerWithIdentifier("LoginViewController")
    let appDelegate = UIApplication.sharedApplication().delegate as? AppDelegate
    appDelegate?.changeRootView(loginViewController)
    appDelegate?.window?.makeKeyAndVisible()
  }
}
