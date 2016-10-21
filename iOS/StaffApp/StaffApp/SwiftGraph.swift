//
//  SwiftGraph.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class SwiftGraph {
  
  private var canvas: Array<Vertex> = []
  var isDirected: Bool
  
  init() {
    canvas = Array<Vertex>()
    isDirected = false
  }
  
  func addVertex(key: String) -> Vertex {
    let childVertex: Vertex = Vertex()
    
    childVertex.key = key
    
    canvas.append(childVertex)
    return childVertex
  }
  
  func addEdge(source: Vertex, neighbor: Vertex, weight: Int) {
    // create a new edge
    let newEdge = Edge()
    
    newEdge.neighbor = neighbor
    newEdge.weight = weight
    source.neighbors.append(newEdge)
    if isDirected {
      let reverseEdge = Edge()
      reverseEdge.neighbor = source
      reverseEdge.weight = weight
      neighbor.neighbors.append(reverseEdge)
    }
  }
  
  
  
}