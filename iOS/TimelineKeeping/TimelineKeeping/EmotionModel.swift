//
//  EmotionModel.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation


enum Gender {
  case Male
  case Female
  
  var description: String {
    switch self {
    case .Female:
      return "Female"
    default:
      return "Male"
    }
  }
}

class Emotion {
  
  var age: Double?
  var anger: Double?
  var contempt: Double?
  var disgust: Double?
  var fear: Double?
  var happiness: Double?
  var neutral: Double?
  var sadness: Double?
  var surprise: Double?
  var emotionMost: Int?
  var gender: Gender!
  
  init?(_ info: [String: AnyObject]) {
    let age = info["age"] as! Double
    let emotionDict = info["emotion"] as! [String : AnyObject]
    
    let anger = emotionDict["anger"] as! Double
    let contempt = emotionDict["contempt"] as! Double
    let disgust = emotionDict["disgust"] as! Double
    let fear = emotionDict["fear"] as! Double
    let happiness = emotionDict["happiness"] as! Double
    let neutral = emotionDict["neutral"] as! Double
    let sadness = emotionDict["sadness"] as! Double
    let surprise = emotionDict["surprise"] as! Double
    let emotionMost = info["emotionMost"] as! Int
    let gender = info["gender"] as! Int
    
    self.age = age
    self.anger = anger
    self.contempt = contempt
    self.disgust = disgust
    self.fear = fear
    self.happiness = happiness
    self.neutral = neutral
    self.sadness = sadness
    self.surprise = surprise
    self.emotionMost = emotionMost
    self.gender = gender == 0 ? Gender.Male : Gender.Female
    
  }
  
  static func emotions(emotions: [[String : AnyObject]]) -> [Emotion] {
    var emotionResult: [Emotion] = []
    for key in emotions {
      let emotion = Emotion(key)
      emotionResult.append(emotion!)
    }
    return emotionResult
  }
  
}













