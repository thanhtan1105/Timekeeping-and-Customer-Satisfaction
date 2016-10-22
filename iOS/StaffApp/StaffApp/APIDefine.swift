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
let urlLogin: String = "/account/login"
let urlUpdateToken: String = "/account/update_token_id_mobile"
let urlGetReminder: String = "/account/get_reminder"
let urlGetAttance: String = "/time/get_attendance"
let urlGetToDoList: String = "/todo_list/get"
let urlSelectTask : String = "/todo_list/select_task"
let urlGetBeacon: String = "/beacon/list_beacon_point"
let urlGetRoomPoint: String = "/coordinate/list_room_point"
let urlGetPoint: String = "/coordinate/list_point"

