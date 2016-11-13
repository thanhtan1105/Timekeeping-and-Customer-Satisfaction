//
//  APIRequest.swift
//  Superb
//
//  Created by Le Thanh Tan on 6/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import Alamofire

// MARK: - Public method
class APIRequest: NSObject {

  var http : String {
    get {
      let ip = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? "192.168.150.174"
      let http = prefixHttp + ip + surfixHttp
      return http
    }
  }
  
  static let shareInstance = APIRequest()
  
  func login(username: String, password: String, onCompletion: ServiceResponse) {
    let url = http + urlLogin
    let header = [
      "Content-Type" : "application/json",
    ]
    
    var dataSent: [String: AnyObject] = [:]
    dataSent["username"] = username
    dataSent["password"] = password
    
    webservice_POST(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
  
  func getToDoList(time: Double, accoundId: String, onCompletion: ServiceResponse) {
    let url = http + urlGetToDoList
    let header = [
      "Content-Type" : "application/json",
      ]
    
    var dataSent: [String: AnyObject] = [:]
    dataSent["time"] = CLong(time)
    dataSent["accountId"] = accoundId
    
    webservice_GET(url, params: dataSent, headersParams: header, completion: onCompletion)

  }
  
  func updateToken(accountID: String, token: String, onCompletion: ServiceResponse) {
    let url = http + urlUpdateToken
    let header = [
      "Content-Type" : "application/json",
    ]
    
    var dataSent: [String: AnyObject] = [:]
    dataSent["accountID"] = accountID
    dataSent["tokenID"] = token
    
    webservice_POST(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
  
  func getNotification(accountID: String, onCompletion: ServiceResponse) {
    let url = http + urlGetReminder
    let header = [
      "Content-Type" : "application/json",
    ]
    
    var dataSent: [String: AnyObject] = [:]
    dataSent["accountId"] = accountID
    
    webservice_POST(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
  
  func selectTask(id: String, onCompletion: ServiceResponse) {
    let url = http + urlSelectTask
    let header = [
      "Content-Type" : "application/json",
      ]
    
    var dataSent: [String: AnyObject] = [:]
    dataSent["id"] = id
    
    webservice_GET(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
  
  func getAttendance(accountID: String, month: Int, year: Int, onCompletion: ServiceResponse) {
    let url = http + urlGetAttance
    let header = [
      "Content-Type" : "application/json",
    ]
    
    var dataSent: [String: AnyObject] = [:]
    dataSent["accountId"] = accountID
    dataSent["month"] = month
    dataSent["year"] = year
    
    webservice_POST(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
  
  func getAllBeacon(onCompletion: ServiceResponse) {
    let url = http + urlGetBeacon
    let header = [
      "Content-Type" : "application/json",
    ]
    webservice_GET(url, params: [:], headersParams: header, completion: onCompletion)    
  }
  
  func getAllRoomPoint(onCompletion: ServiceResponse) {
    let url = http + urlGetRoomPoint
    let header = [
      "Content-Type" : "application/json",
      ]
    webservice_GET(url, params: [:], headersParams: header, completion: onCompletion)
  }
  
  func getAllPoint(onCompletion: ServiceResponse) {
    let url = http + urlGetPoint
    let header = [
      "Content-Type" : "application/json",
      ]
    webservice_GET(url, params: [:], headersParams: header, completion: onCompletion)
  }
  
  func findShorestPart(sourceId: Int, destinateId: Int, onCompletion: ServiceResponse) {
    let url = http + urlFindPath
    let header = [
      "Content-Type" : "application/json",
    ]
    var dataSent: [String: AnyObject] = [:]
    dataSent["from"] = String(sourceId)
    dataSent["to"] = String(destinateId)

    webservice_GET(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
  
  func createTask(title: String, timeNotify: String, accountId: String, onCompletion: ServiceResponse) {
    let url = http + urlCreateTask
    let header = [
      "Content-Type" : "application/json",
    ]
    
    let dataSent = [
      "title": title,
      "timeNotify": timeNotify,
      "accountId": accountId
    ]
    webservice_POST(url, params: dataSent, headersParams: header, completion: onCompletion)
  }
}

// MARK: - Private method
extension APIRequest {
  
  private func webservice_POST(url: String, params: [String: AnyObject]?, headersParams: [String: String]?, completion: (response: ResponsePackage, error: ErrorWebservice?) -> Void) {
    
    print("Request \(params)")
    let responsePackage = ResponsePackage()
    let errorPackage = ErrorWebservice() // for get new token
    let parameterEncode: ParameterEncoding = .URL
    
    Alamofire.request(.POST, url, parameters: params, encoding: parameterEncode, headers: nil).responseJSON { (response: Response<AnyObject, NSError>) in
      if response.result.error != nil {
        errorPackage.code = (response.result.error?.code)!
        errorPackage.error = (response.result.error?.domain)!
        errorPackage.error_description = response.result.description
        completion(response: responsePackage, error: errorPackage)
        return
      }
      
      let JSON = response.result.value
      responsePackage.success = true
      responsePackage.response = JSON
      responsePackage.error = response.result.error
      completion(response: responsePackage, error: nil)
    }
    
  }
  
  
  private func webservice_GET(url: String, params: [String: AnyObject]?, headersParams: [String: String]?, completion: (response: ResponsePackage, error: ErrorWebservice?) -> Void) {
    
    print("Request \(params)")
    let responsePackage = ResponsePackage()
    let errorPackage = ErrorWebservice() // for get new token
    let parameterEncode: ParameterEncoding = .URL
    Alamofire.request(.GET, url, parameters: params, encoding: parameterEncode, headers: nil).responseJSON { (response: Response<AnyObject, NSError>) in
      if response.result.error != nil {
        errorPackage.code = (response.result.error?.code)!
        errorPackage.error = (response.result.error?.domain)!
        errorPackage.error_description = response.result.description
        completion(response: responsePackage, error: errorPackage)
        return
      }
      
      let JSON = response.result.value
      responsePackage.success = true
      responsePackage.response = JSON
      responsePackage.error = response.result.error
      completion(response: responsePackage, error: nil)
    }
  }
  
}
