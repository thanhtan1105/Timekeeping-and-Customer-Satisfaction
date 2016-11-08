//
//  FaceListViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import AlamofireImage

class FaceListViewController: BaseViewController {

  @IBOutlet weak var collectionView: UICollectionView!
  var faceList: [Face] = []
  var faceCount = 0
  let employee = Employee.getEmployeeFromUserDefault()
  
  override func viewDidLoad() {
    super.viewDidLoad()
    collectionView.delegate = self
    collectionView.dataSource = self
    getFace(String(employee.id!))
  }
  
  override func viewDidAppear(animated: Bool) {
    super.viewDidAppear(animated)
    collectionView.reloadData()
  }
  
  @IBAction func onFinishTapped(sender: UIBarButtonItem) {
    let alertVC = UIAlertController(title: "Finish", message: "Do you want to finish training image", preferredStyle: .Alert)
    alertVC.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
      
//      if self.faceImage.count == 0 {
//        self.navigationController?.popToRootViewControllerAnimated(true)
//      } else {
        let personGroupId = String(Department.getDepartmentFromUserDefault().id!)
        APIRequest.shareInstance.sendTrainingStatus(personGroupId, onCompletion: { (response: ResponsePackage?, error: ErrorWebservice?) in
          print(response?.response)
          self.navigationController?.popToRootViewControllerAnimated(true)
        })
//      }
      
    }))
    alertVC.addAction(UIAlertAction(title: "Cancel", style: .Cancel, handler: { (action: UIAlertAction) in
      
    }))
    presentViewController(alertVC, animated: true, completion: nil)
      
  }
}

// MARK: - Private method
extension FaceListViewController {
  private func getFace(userId: String) {
    APIRequest.shareInstance.getListFace(userId) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail to get api")
        return
      }
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        let data = dict["data"] as! [[String : AnyObject]]
        self.faceList = Face.faceList(data)
        self.collectionView.reloadData()
      }
    }
  }
  
<<<<<<< HEAD
  private func deleteFace(userId: String, faceId: String, onCompletion: (isSuccess: Bool) -> Void ) {
    APIRequest.shareInstance.deleteFace(userId, faceId: faceId) { (response: ResponsePackage?, error: ErrorWebservice?) in
=======
  private func deleteFace(userId: String, persistentedId: String, onCompletion: (isSuccess: Bool) -> Void ) {
    APIRequest.shareInstance.deleteFace(userId, persistentedId: persistentedId) { (response: ResponsePackage?, error: ErrorWebservice?) in
>>>>>>> w10-hien-fix-bug
      guard error == nil else {
        print("Fail to delete face")
        onCompletion(isSuccess: false)
        return
      }
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        onCompletion(isSuccess: true)
      }
    }
  }
}

extension FaceListViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
  
  func collectionView(collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, atIndexPath indexPath: NSIndexPath) -> UICollectionReusableView {
    
    var reuseableView = UICollectionReusableView()
    if kind == UICollectionElementKindSectionHeader {
      let headerView = collectionView.dequeueReusableSupplementaryViewOfKind(UICollectionElementKindSectionHeader, withReuseIdentifier: "HeaderCollectionReusableView", forIndexPath: indexPath) as! HeaderCollectionReusableView
      let title = "Added"
      headerView.titleLabel.text = title
      reuseableView = headerView
    }
    
    return reuseableView
  }
  
  func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
    return 1
  }
  
  func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    return faceList.count + 1
  }
  
  func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
    if indexPath.item == 0 {
      // new
      let item = collectionView.dequeueReusableCellWithReuseIdentifier(AddNewFaceCollectionCell.ClassName, forIndexPath: indexPath) as! AddNewFaceCollectionCell
      item.delegate = self
      return item
    } else {
      let item = collectionView.dequeueReusableCellWithReuseIdentifier(FaceCollectionCell.ClassName, forIndexPath: indexPath) as! FaceCollectionCell
      let URL = NSURL(string: faceList[indexPath.item - 1].storePath)
      item.faceImage.downloadedFrom(URL!)
      return item
    }      
  }
  
  func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
    return CGSize(width: screenSize.width / 2 - 10, height: screenSize.height / 3.5 )
  }
  
  func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath) {
    
    let actionSheet = UIAlertController(title: "Do you want to delete face", message: "", preferredStyle: .ActionSheet)
    let deleteButton = UIAlertAction(title: "Delete", style: .Destructive) { (action: UIAlertAction) in
      LeThanhTanLoading.sharedInstance.showLoadingAddedTo(self.view, animated: true)
      let id = String(self.employee.id!)   // send person code
      let faceId = String(self.faceList[indexPath.item - 1].id)
      
      self.deleteFace(id, faceId: faceId, onCompletion: { (isSuccess) in
        LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
        if isSuccess == true {
          self.faceList.removeAtIndex(indexPath.item - 1)
          self.collectionView.reloadData()
        }
      })
    }
    
    let cancelButton = UIAlertAction(title: "Cancel", style: .Cancel) { (action: UIAlertAction) in
      
    }
    actionSheet.addAction(deleteButton)
    actionSheet.addAction(cancelButton)
    presentViewController(actionSheet, animated: true, completion: nil)
    
  }
}

extension FaceListViewController: CameraViewControllerDelegate, AddNewFaceCollectionCellDelegate {
  
  func cameraViewController(cameraViewController: CameraViewController, didFinishUploadFace image: UIImage) {
    getFace(String(employee.id!))
  }
  
  func didAddNewFaceTapped() {
    let cameraVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("CameraViewController") as! CameraViewController
    navigationController?.presentViewController(cameraVC, animated: true, completion: {
      cameraVC.delegate = self
    })
  }
}
