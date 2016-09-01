//
//  NSNumber+Extension.swift
//  LeThanhTanFramework
//
//  Created by Le Thanh Tan on 7/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

extension NSNumber {
  // return like 12k, 12.3k
  public class func suffixNumber(number:NSNumber) -> NSString {
    
    var num:Double = number.doubleValue;
    let sign = ((num < 0) ? "-" : "" );
    
    num = fabs(num);
    
    if (num < 1000.0){
      return "\(sign)\(Int(num))";
    }
    
    let exp:Int = Int(log10(num) / 3.0 ); //log10(1000));
    
    let units:[String] = ["K","M","G","T","P","E"];
    
    let roundedNum:Double = round(10 * num / pow(1000.0,Double(exp))) / 10;
    
    
    return "\(sign)\(roundedNum)\(units[exp-1])";
  }
}