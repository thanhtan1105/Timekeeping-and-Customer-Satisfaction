//
//  Edge.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Edge {
  var neighbor: Vertex
  var weight: Int
  
  init() {
    weight = 0
    self.neighbor = Vertex()
  }
}