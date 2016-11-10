//
//  UIImageView+Extension.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 11/5/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import UIKit

extension UIImageView {
  func downloadedFrom(url: NSURL, placeHolder imageName: String) {
    self.image = UIImage(named: imageName)
    NSURLSession.sharedSession().dataTaskWithURL(url) { (data: NSData?, response: NSURLResponse?, error: NSError?) in
      guard
        let httpURLResponse = response as? NSHTTPURLResponse where httpURLResponse.statusCode == 200,
        let data = data where error == nil,
        let image = UIImage(data: data)
        else {
          return
      }
      dispatch_async(dispatch_get_main_queue(), {
        // should be return image
//        self.transform = CGAffineTransformMakeRotation((90.0 * CGFloat(M_PI)) / 180.0)
        self.image = image
      })
      
      }.resume()
  }
}
