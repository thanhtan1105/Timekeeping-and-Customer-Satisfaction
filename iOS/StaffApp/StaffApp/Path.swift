//
//  Path.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Path {
  var total: Int!
  var destination: Vertex
  var previous: Path!
  
  init() {
    destination = Vertex()
  }
  
  func processDijkstra(source: Vertex, destination: Vertex) -> Path? {
    var frontier = Array<Path>()
    var finalPaths = Array<Path>()
    
    // use source edges to create the frontier
    for e in source.neighbors {
      let newPath: Path = Path()
      newPath.destination = e.neighbor
      newPath.previous = nil
      newPath.total = e.weight
      frontier.append(newPath)
    }
    
    var bestPath: Path = Path()
    while frontier.count != 0 {
      bestPath = Path()
      var pathIndex: Int = 0
      for x in 0..<frontier.count {
        let itemPath: Path = frontier[x]
        if bestPath.total == nil || itemPath.total < bestPath.total {
          bestPath = itemPath
          pathIndex = x
        }
      } // end for
      
      for e in bestPath.destination.neighbors {
        let newPath: Path = Path()
        newPath.destination = e.neighbor
        newPath.previous = bestPath
        newPath.total = bestPath.total + e.weight
        
        frontier.append(newPath)
      }
      
      // presever the best path
      finalPaths.append(bestPath)
      
      // remove the best path from the frontier
      frontier.removeAtIndex(pathIndex)
    }
    
    var shortestPath: Path! = Path()
    for itemPath in finalPaths {
      if itemPath.destination.key == destination.key {
        if shortestPath.total == nil || itemPath.total < shortestPath.total {
          shortestPath = itemPath
        }
      }
    }
    
    return shortestPath
  }
}