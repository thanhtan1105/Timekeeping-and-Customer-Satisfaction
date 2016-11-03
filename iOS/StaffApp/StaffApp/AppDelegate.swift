//
//  AppDelegate.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import OneSignal


@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {

  var window: UIWindow?
  var navigation: UINavigationController?
  
  func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
  
    OneSignal.initWithLaunchOptions(launchOptions, appId: "dbd7cdd6-9555-416b-bc08-21aa24164299") { (result: OSNotificationOpenedResult!) in
      // This block gets called when the user reacts to a notification received
      let payload = result.notification.payload
//      let id = payload.additionalData["id"] as! Int
      
      // download notification
      dispatch_async(dispatch_get_main_queue(), { 
        let appDelegate = UIApplication.sharedApplication().delegate as? AppDelegate
        let viewController = appDelegate!.window!.rootViewController
        if (viewController as? HomeTabBarController) != nil {
          let homeTabbar = viewController as! HomeTabBarController
          let listVC: [UIViewController] = homeTabbar.viewControllers!
          let navigation = listVC[1] as! UINavigationController   // notificaionViewController
          let notificationVC = navigation.childViewControllers[0] as! NotificationViewController
          notificationVC.reloadNotification()
        }
      })
      
    }
    
    OneSignal.IdsAvailable { (userID: String!, pushToken: String!) in
      print("UserId:%@", userID)
      NSUserDefaults.standardUserDefaults().setObject(userID, forKey: "token")
      if (pushToken != nil) {
        print("pushToken:%@", pushToken)
      }
    }
    
    if !isLogin() {
      let storyboard = UIStoryboard(name: "Main", bundle: nil)
      let loginViewController = storyboard.instantiateViewControllerWithIdentifier("LoginViewController")
      navigation = UINavigationController(rootViewController: loginViewController)
      window?.rootViewController = navigation
      window?.makeKeyAndVisible()
    }
    
    
    
    return true
  }
  
  func isLogin() -> Bool {
    let userDefault = NSUserDefaults.standardUserDefaults()
    let userId = userDefault.objectForKey("didLogin") as? Int
    if userId == 0 || userId == nil {
      return false
    } else {
      return true
    }
  }
  
  func changeRootView(vc: UIViewController) {
    navigation?.setViewControllers([vc], animated: true)
  }
  
  
  func applicationWillResignActive(application: UIApplication) {
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
  }

  func applicationDidEnterBackground(application: UIApplication) {
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later.
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
  }

  func applicationWillEnterForeground(application: UIApplication) {
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
  }

  func applicationDidBecomeActive(application: UIApplication) {
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
  }

  func applicationWillTerminate(application: UIApplication) {
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
  }


}

