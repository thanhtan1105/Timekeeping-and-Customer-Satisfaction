//
//  UIViewController+Extension.swift
//  LeThanhTanFramework
//
//  Created by Le Thanh Tan on 7/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

extension UIViewController {
  public class func topViewController() -> UIViewController {
    var topVC = UIApplication.sharedApplication().keyWindow?.rootViewController
    while (topVC!.presentedViewController != nil) {
      topVC = topVC!.presentedViewController
    }
    return topVC!
  }
}
