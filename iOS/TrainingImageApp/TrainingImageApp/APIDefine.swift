//
//  APIDefine.swift
//  Superb
//
//  Created by Le Thanh Tan on 6/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

// Server
enum Enviroment {
	case dev
	case test
	case production
  case home
  case Hien
  case Tan
}

let enviroment = Enviroment.Tan

let ip: String = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? ""

let serverUrl: String = "http://" + ip + ":8080/api"

//let serverUrl: String = {
//	switch enviroment {
//	case .dev:
//		return "http://192.168.150.81:8080/api"
//	case .test:
//		return "Enviroment.TEST HERE"
//	case .production:
//		return "Enviroment.PRODUCTION HERE"
//  case .home:
//    return "http://192.168.1.102:8080/api"
//  case .Hien:
//    return "http://192.168.43.79:8080/api"
//  case .Tan:
//    return "http://192.168.1.107:8080/api"
//	}
//
//}()


// MARK :- URL API
/* {
 "http://localhost:8080/api"
 Content-Type: application/json
 
 "ApiKey": "20e0a115-c1e8-4b6e-91ed-1f8e676b60b3",
 "userId": "1"
} */

let urlGetUserDetail: String = serverUrl.stringByAppendingString("/face/identifyImage")
let urlGetDepartment: String = serverUrl.stringByAppendingString("/department/list")
let urlGetAccountList: String = serverUrl.stringByAppendingString("/account/search_department")
//let urlGetAccountList: String = serverUrl.stringByAppendingString("/account/list")
let urlAddFaceToPerson: String = serverUrl.stringByAppendingString("/account/add_face")
let urlSendTrainingStatus: String = serverUrl.stringByAppendingString("/department/training")



