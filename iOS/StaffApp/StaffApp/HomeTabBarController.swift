//
//  HomeTabBarController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import TanLT1Extensions

class HomeTabBarController: UITabBarController {
  
  static let StoryboardIdentifiter = "tabbarVC"
  
  override func viewDidLoad() {
    super.viewDidLoad()
    UITabBar.appearance().tintColor = UIColor(hex: 0xffffff)
    UITabBar.appearance().barTintColor = UIColor(hex: 0x4F84B0)
    UITabBarItem.appearance().setTitleTextAttributes([NSForegroundColorAttributeName: UIColor.whiteColor()], forState: .Normal)
    self.navigationController?.navigationBar.barTintColor = UIColor(hex: 0x4F84B0)
    self.selectedIndex = 0
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    // Dispose of any resources that can be recreated.
  }
}
