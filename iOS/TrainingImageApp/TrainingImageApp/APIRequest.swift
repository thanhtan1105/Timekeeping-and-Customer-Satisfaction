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
      let ip = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? "192.168.43.93"
      let http = prefixHttp + ip + surfixHttp      
      return http
    }
  }
  static let shareInstance = APIRequest()
 
  func getDepartmentList(start: Int, top: Int, onCompletion: ServiceResponse) {
    let url = http + urlGetDepartment

    let params: [String : AnyObject] = [
      "start" : start,
      "top" : top
    ]
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func getListFace(userId: String, onCompletion: ServiceResponse) {
    let url = http + urlGetFace
    let params: [String : AnyObject] = [
      "accountId" : userId
    ]
    
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func deleteFace(userId: String, faceId: String, onCompletion: ServiceResponse) {
    let url = http + urlDelteFace
    let params: [String : AnyObject] = [
      "accountId": userId,
      "faceId" : faceId
    ]
    
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func deleteAllFace(userId: String, onCompletion: ServiceResponse) {
    let url = http + urlDeleteAllFace
    let params: [String : AnyObject] = [
      "accountId" : userId
    ]
    
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func sendTrainingStatus(departmentId: String, onCompletion: ServiceResponse) {
    let url = http + urlSendTrainingStatus
    let params: [String : AnyObject] = [
      "departmentId" : departmentId
    ]
    
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func getAccountList(departmentId departmentId: Int, start: Int, top: Int, onCompletion: ServiceResponse) {
    let url = http + urlGetAccountList
    let params: [String : AnyObject] = [
      "departmentID" : departmentId,
      "start" : start,
      "top" : top
    ]
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  
  
  func addFaceToPerson(personGroupId: String, personId: Int, imageFace: UIImage, onCompletion: ServiceResponse) {
    let url = http + urlAddFaceToPerson
    let request = NSMutableURLRequest(URL: NSURL(string: url)!)
    request.HTTPMethod = "POST"
    
    Alamofire.upload(request, multipartFormData: { (multipartFormData: MultipartFormData) in
      let imageData = UIImageJPEGRepresentation(imageFace, 0.1)
      let data = NSData()
      multipartFormData.appendBodyPart(data: imageData!, name: "image", fileName: "\(data)", mimeType: "image/png")
      multipartFormData.appendBodyPart(data: personGroupId.dataUsingEncoding(NSUTF8StringEncoding)!, name: "departmentID")
      multipartFormData.appendBodyPart(data: "\(personId)".dataUsingEncoding(NSUTF8StringEncoding)!, name: "accountId")
      

    }) { (encodingResult: Manager.MultipartFormDataEncodingResult) in
      switch encodingResult {
      case .Success(let upload, _, _):
        upload.responseJSON(completionHandler: { (response: Response<AnyObject, NSError>) in
          // upload successfully
          debugPrint(response)
          let responsePackage = ResponsePackage()
          let JSON = response.result.value
          responsePackage.success = true
          responsePackage.response = JSON
          responsePackage.error = response.result.error
          onCompletion(responsePackage, nil)
        })
      case .Failure(let encodingError):
        // coudn't upload file
        print(encodingError)
        let errorWebservice = ErrorWebservice.init()
        errorWebservice.code = 0
        errorWebservice.error_description = "Upload fail"
        onCompletion(nil, errorWebservice)
      }
    }

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
