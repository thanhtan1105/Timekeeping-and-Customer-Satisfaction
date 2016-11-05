//
//  Face.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 11/5/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

struct Face {
  
  var id = 0
  var persistedFaceId = ""
  var storePath = ""
  
  init(_ info: [String : AnyObject]) {
    let id = info["id"] as! Int
    let persistedFaceId = info["persistedFaceId"] as! String
    let storePath = info["storePath"] as! String
    
    self.id = id
    self.persistedFaceId = persistedFaceId
    self.storePath = storePath
  }
  
  static func faceList(faces: [[String : AnyObject]]) -> [Face] {
    var faceList = [Face]()
    for dictionary in faces {
      let face = Face(dictionary)
      faceList.append(face)
    }
    return faceList
  }
}