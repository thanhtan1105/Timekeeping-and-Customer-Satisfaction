//
//  DrawPath.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import UIKit

class DrawPath: UIView {
  
  let scale: Double = 20.0
  var dataSource: [(x: Float, y: Float)] = []
  
  override func drawRect(rect: CGRect) {
    // Drawing code
    let context = UIGraphicsGetCurrentContext()
    CGContextSetLineWidth(context, 5.0)
    CGContextSetStrokeColorWithColor(context, UIColor.blueColor().CGColor)
    CGContextMoveToPoint(context, CGFloat(Double(dataSource[0].x) * scale) , CGFloat(Double(dataSource[0].y) * scale))
    for i in 1..<dataSource.count {
      CGContextAddLineToPoint(context, CGFloat(Double(dataSource[i].x) * scale) , CGFloat(Double(dataSource[i].y) * scale))
    }
    CGContextStrokePath(context)
  }
  
}