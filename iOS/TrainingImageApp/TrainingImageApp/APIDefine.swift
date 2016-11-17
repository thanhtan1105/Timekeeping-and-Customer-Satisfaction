//
//  APIDefine.swift
//  Superb
//
//  Created by Le Thanh Tan on 6/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation

let prefixHttp: String = "http://"
let surfixHttp: String = ":8080/api"



// MARK :- URL API
/* {
 "http://localhost:8080/api"
 Content-Type: application/json
 
 "ApiKey": "20e0a115-c1e8-4b6e-91ed-1f8e676b60b3",
 "userId": "1"
} */

let urlGetUserDetail: String = "/face/identifyImage"
let urlGetDepartment: String = "/department/list"
let urlGetAccountList: String = "/account/search_department"
let urlAddFaceToPerson: String = "/account/add_face"
let urlSendTrainingStatus: String = "/department/training"
let urlGetFace: String = "/account/list_face"
let urlDelteFace: String = "/account/remove_face"
let urlDeleteAllFace: String = "/account/remove_all_face"


