//
//  LoginViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class LoginViewController: BaseViewController {

  @IBOutlet weak var usernameLabel: UITextField!
  @IBOutlet weak var passwordLabel: UITextField!
  @IBOutlet weak var loginFailLabel: UILabel!
  @IBOutlet weak var signInButton: UIButton!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    self.navigationController?.setNavigationBarHidden(true, animated: false)
    
//    myTextField.attributedPlaceholder = NSAttributedString(string:"placeholder text",
//                                                           attributes:[NSForegroundColorAttributeName: UIColor.yellowColor()])
    usernameLabel.attributedPlaceholder = NSAttributedString(string: "username", attributes: [NSForegroundColorAttributeName: UIColor.whiteColor()])
    passwordLabel.attributedPlaceholder = NSAttributedString(string: "password", attributes: [NSForegroundColorAttributeName: UIColor.whiteColor()])
  }
  
  override func viewWillAppear(animated: Bool) {
    super.viewWillAppear(animated)
    navigationController?.setNavigationBarHidden(true, animated: true)
  }

  @IBAction func onSignInTapped(sender: UIButton) {
    let username = usernameLabel.text
    let password = passwordLabel.text
    callApiLogin(username!, password: password!) { (account, error) in
      if account != nil {
        Account.saveAccount(account!)
        
        // update token to account 
        let tokenID = NSUserDefaults.standardUserDefaults().objectForKey("token") as! String
        self.callApiUpdateToken(String(account!.id!), token: tokenID, completion: { (isSuccess, error) in
          dispatch_async(dispatch_get_main_queue(), { 
            //save param to login
            NSUserDefaults.standardUserDefaults().setObject(1, forKey: "didLogin")
            NSUserDefaults.standardUserDefaults().synchronize()

            // redirect to main view
            let appDelegate = UIApplication.sharedApplication().delegate as? AppDelegate
            let vc = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier(HomeTabBarController.StoryboardIdentifiter)
            appDelegate?.changeRootView(vc)
          })
        })
      } else {
        self.loginFailLabel.hidden = false
      }
    }
  }
  
  @IBAction func onSettingTapped(sender: UIButton) {
    let settingVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("SettingViewController") as! SettingViewController
    navigationController?.pushViewController(settingVC, animated: true)
  }
}

// MARK: - Private method
extension LoginViewController {
  private func initLayout() {
    
  }
  
  private func callApiLogin(username: String, password: String, completion onCompletionHandler: ((account: Account?, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.login(username, password: password) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        onCompletionHandler!(account: nil, error: NSError(domain: "", code: (error?.code)!, userInfo: ["info" : (error?.error_description)!]))
        return
      }

      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        print("Call api success")
        let accountContent = dict["data"] as! [String : AnyObject]
        let account = Account(accountContent)
        onCompletionHandler!(account: account, error: nil)
      } else {
        print("Fail")
        let message = dict["message"] as? String
        onCompletionHandler!(account: nil, error: NSError(domain: "", code: 0, userInfo: ["info" : message!]))
      }
    }
  }
  
  private func callApiUpdateToken(accountID: String, token: String, completion onCompletionHandler: ((isSuccess: Bool, error: NSError?) -> Void)?) {
    APIRequest.shareInstance.updateToken(accountID, token: token) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail")
        onCompletionHandler!(isSuccess: false, error: NSError(domain: "", code: (error?.code)!, userInfo: ["info" : (error?.error_description)!]))
        return
      }
      
      let dict = response?.response as! [String : AnyObject]
      print(dict)
      onCompletionHandler!(isSuccess: true, error: nil)
    }
  }
}