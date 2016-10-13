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

let urlCheckIn: String = "/account/check_in"
let urlGetEmotion: String = "/emotion/get_customer_emotion"
let urlBeginTransaction: String = "/emotion/begin_transaction"
let urlStartTransaction: String = "/emotion/start_transaction"
let urlProcessTransaction: String = "/emotion/process_transaction"
let urlEndTransaction: String = "/emotion/end_transaction"
let urlEmotionUpload: String = "/emotion/upload"
