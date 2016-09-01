//
//  Array+Extension.swift
//  LeThanhTanFramework
//
//  Created by Le Thanh Tan on 7/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

extension SequenceType {
  public func findElement (match: Generator.Element->Bool) -> Generator.Element? {
    for element in self where match(element) {
      return element
    }
    return nil
  }
}