//
//  BaseViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import OneSignal

class BaseViewController: UIViewController {

  override func viewDidLoad() {
    super.viewDidLoad()
    self.navigationController?.navigationBar.tintColor = UIColor.whiteColor()
  }
  
  override func viewDidAppear(animated: Bool) {
    super.viewDidAppear(animated)
    
    if let account = Account.getAccount() {
      let id = account.id!
      isExpire(String(id)) { (key) in
        if let key = key {
          let pushToken = NSUserDefaults.standardUserDefaults().objectForKey("token") as! String
          if (pushToken == key) {
            
          } else {
            // logout
            let userDefault = NSUserDefaults.standardUserDefaults()
            userDefault.setObject(0, forKey: "didLogin")
            userDefault.removeObjectForKey("account") // remove user info
            let storyboard = UIStoryboard(name: "Main", bundle: nil)
            let loginViewController = storyboard.instantiateViewControllerWithIdentifier("LoginViewController")
            let appDelegate = UIApplication.sharedApplication().delegate as! AppDelegate
            appDelegate.navigation = UINavigationController(rootViewController: loginViewController)
            appDelegate.window?.rootViewController = appDelegate.navigation
            appDelegate.window?.makeKeyAndVisible()
          }
        }
      }
    }
  }
  
  private func isExpire(accounID: String, isSuccess: (key: String?) -> Void) {
    APIRequest.shareInstance.checkExpire(accounID) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print(error)
        return
      }
      
      let dict = response?.response as! [String : AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        let data = dict["data"] as! [String : AnyObject]
        let key = data["key"] as! String
        isSuccess(key: key)
      } else {
        isSuccess(key: nil)
      }
    }
  }
}
