//
//  APIRequestProtocol.swift
//  Superb
//
//  Created by Le Thanh Tan on 6/22/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation


protocol APIRequestProtocol {
	
	/**
	Get all personality question
		"ApiKey": "20e0a115-c1e8-4b6e-91ed-1f8e676b60b3",
		"userId": "1"
		
	- parameter onCompletion: ServiceResponse
	*/
  
}

typealias ServiceResponse = (ResponsePackage?, ErrorWebservice?) -> Void

class ResponsePackage {
	
	var success = false
	var response: AnyObject? = nil
	var error: NSError? = nil

}

class ErrorWebservice {
	var code: Int = 0
	var error: String = ""
	var error_description: String = ""
	
	init(){
		
	}
	
	init(data: NSDictionary){
		code = data.objectForKey("code") as! Int
		error = data.objectForKey("error") as! String
		error_description = data.objectForKey("error_description") as! String
	}
}