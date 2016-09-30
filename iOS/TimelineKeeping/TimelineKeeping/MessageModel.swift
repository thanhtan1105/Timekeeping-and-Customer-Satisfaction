//
//  MessageModel.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/30/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

class Message {
  
  var id: Int?
  var message: String?
  var fromAge: Double?
  var toAge: Double?
  var gender: EGender?
  var emotion: EEmotion?
  
  init?(_ info: [String: AnyObject]) {
    let id = info["id"] as! Int
    let message = info["message"] as! String
    let fromAge = info["fromAge"] as! Double
    let toAge = info["toAge"] as! Double
    let gender = info["gender"] as! Int
    let emotion = info["emotion"] as! Int
    
    self.message = message
    self.id = id
    self.fromAge = fromAge
    self.toAge = toAge
    self.gender = gender == 0 ? EGender.Male : EGender.Female
    self.emotion = EEmotion(emotion)
  }
  
  static func messages(messages: [[String : AnyObject]]) -> [Message] {
    var messageResult: [Message] = []
    for key in messages {
      let message = Message(key)
      messageResult.append(emotion!)
    }
    return messageResult
  }
  
}