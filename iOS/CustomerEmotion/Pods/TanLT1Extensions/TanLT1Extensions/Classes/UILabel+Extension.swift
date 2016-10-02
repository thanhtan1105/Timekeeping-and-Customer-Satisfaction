//
//  UILabel+Extension.swift
//  LeThanhTanFramework
//
//  Created by Le Thanh Tan on 7/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

extension UILabel {
  
  func requiredHeight() -> CGFloat {
    let label: UILabel = UILabel(frame: CGRect(x: 0, y: 0, width: self.frame.width, height: CGFloat.max))
    label.numberOfLines = 0
    label.font = self.font
    label.text = self.text
    label.sizeToFit()
    return label.frame.height
  }
}

