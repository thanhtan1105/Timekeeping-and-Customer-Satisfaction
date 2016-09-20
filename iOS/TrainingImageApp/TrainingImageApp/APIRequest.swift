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

  static let shareInstance = APIRequest()
 
  func identifyImage(image: UIImage, onCompletion: ServiceResponse) {
    let url = urlGetUserDetail
    
    let request = NSMutableURLRequest(URL: NSURL(string: url)!)
    request.HTTPMethod = "POST"
    
    Alamofire.upload(request, multipartFormData: { (multipartFormData: MultipartFormData) in
      let imageData = UIImageJPEGRepresentation(image, 0.25)
      let data = NSData()
      multipartFormData.appendBodyPart(data: imageData!, name: "image", fileName: "\(data)", mimeType: "image/png")
      
    }) { (encodingResult: Manager.MultipartFormDataEncodingResult) in
      switch encodingResult {
      case .Success(let upload, _, _):
        upload.responseJSON(completionHandler: { (response: Response<AnyObject, NSError>) in
          // upload successfully
          debugPrint(response)
          do {
            let json = try NSJSONSerialization.JSONObjectWithData(response.data!, options: []) as! [String: AnyObject]
            print(json)
            onCompletion(nil, nil)
          } catch {
            // create error
            // TO-DO
            let errorWebservice = ErrorWebservice.init()
            errorWebservice.code = 0
            errorWebservice.error_description = "Upload fail"
            onCompletion(nil, errorWebservice)
          }
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

  func getDepartmentList(start: Int, top: Int, onCompletion: ServiceResponse) {
    let url = urlGetDepartment
    let params: [String : AnyObject] = [
      "start" : start,
      "top" : top
    ]
    
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func sendTrainingStatus(departmentId: String, onCompletion: ServiceResponse) {
    let url = urlSendTrainingStatus
    let params: [String : AnyObject] = [
      "departmentId" : departmentId
    ]
    
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func getAccountList(departmentId departmentId: Int, start: Int, top: Int, onCompletion: ServiceResponse) {
    let url = urlGetAccountList
    let params: [String : AnyObject] = [
      "departmentID" : departmentId,
      "start" : start,
      "top" : top
    ]
    webservice_GET(url, params: params, headersParams: nil, completion: onCompletion)
  }
  
  func addFaceToPerson(personGroupId: String, personId: Int, imageFace: UIImage, onCompletion: ServiceResponse) {
    let url = urlAddFaceToPerson
    let request = NSMutableURLRequest(URL: NSURL(string: url)!)
    request.HTTPMethod = "POST"
    
    Alamofire.upload(request, multipartFormData: { (multipartFormData: MultipartFormData) in
      let imageData = UIImageJPEGRepresentation(imageFace, 0.25)
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
