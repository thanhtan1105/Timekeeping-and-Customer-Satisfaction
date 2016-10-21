//
//  Vertex.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Vertex {
  var key: String?
  var neighbors: Array<Edge>
  
  init() {
    self.neighbors = Array<Edge>()
  }
}