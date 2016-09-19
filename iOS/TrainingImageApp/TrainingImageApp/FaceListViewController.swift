//
//  FaceListViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class FaceListViewController: BaseViewController {

  override func viewDidLoad() {
    super.viewDidLoad()
  }

  
  @IBAction func onFinishTapped(sender: UIBarButtonItem) {
    let alertVC = UIAlertController(title: "Finish", message: "Do you want to finish training image", preferredStyle: .Alert)
    alertVC.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
      self.navigationController?.popToRootViewControllerAnimated(true)
    }))
    alertVC.addAction(UIAlertAction(title: "Cancel", style: .Cancel, handler: { (action: UIAlertAction) in
      
    }))
    presentViewController(alertVC, animated: true, completion: nil)
      
  }
  
  
  @IBAction func onAddFaceTapped(sender: UIBarButtonItem) {
    let cameraVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("CameraViewController") as! CameraViewController    
    navigationController?.presentViewController(cameraVC, animated: true, completion: {
      cameraVC.delegate = self
    })
  }
  
}

extension FaceListViewController: UITableViewDelegate, UITableViewDataSource {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    return UITableViewCell()
  }
}

extension FaceListViewController: CameraViewControllerDelegate {
  func cameraViewController(cameraViewController: CameraViewController, didUsePhoto image: UIImage) {
    // get data
    let personGroupId = String(Department.getDepartmentFromUserDefault().id!)
    let personId = Employee.getEmployeeFromUserDefault().employeeCode!
    
    APIRequest.shareInstance.addFaceToPerson(personGroupId, personId: personId, imageFace: image) { (response: ResponsePackage?, error: ErrorWebservice?) in
      // error of network
      guard error == nil else {
        print("Fail")
        return
      }
      
      print(response?.response)

    }
  }
}
