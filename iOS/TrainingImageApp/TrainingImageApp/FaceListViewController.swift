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
  
  @IBAction func onDeleteAllTapped(sender: UIBarButtonItem) {
    let alertVC = UIAlertController(title: "Delete all face ", message: "Do you want to delete all face", preferredStyle: .Alert)
    alertVC.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
      // show confirm again
      self.showDeleteAgain()
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
  

  private func deleteFace(userId: String, faceId: String, onCompletion: (isSuccess: Bool) -> Void ) {
    APIRequest.shareInstance.deleteFace(userId, faceId: faceId) { (response: ResponsePackage?, error: ErrorWebservice?) in
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
  
  private func deleteAllFace(userId: String, onCompletion: (isSuccess: Bool) -> Void) {
    APIRequest.shareInstance.deleteAllFace(userId) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print("Fail to remove face")
        onCompletion(isSuccess: false)
        return
      }
      
      let dict = response?.response as! [String: AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        onCompletion(isSuccess: true)
      } else {
        onCompletion(isSuccess: false)
      }

    }
  }
  
  private func showDeleteAgain() {
    let alertVC = UIAlertController(title: "Remove all face ", message: "Are you sure you want to delete all face", preferredStyle: .Alert)
    
    alertVC.addAction(UIAlertAction(title: "Cancel", style: .Cancel, handler: { (action: UIAlertAction) in
      
    }))
    
    alertVC.addAction(UIAlertAction(title: "Remove", style: .Destructive, handler: { (action: UIAlertAction) in
      // call api delete
      let id = String(self.employee.id!)   // send person code
      LeThanhTanLoading.sharedInstance.showLoadingAddedTo(self.view, animated: true)
      
      self.deleteAllFace(id, onCompletion: { (isSuccess) in
        LeThanhTanLoading.sharedInstance.hideLoadingAddedTo(self.view, animated: true)
        if isSuccess == true {
          self.faceList.removeAll()
          self.collectionView.reloadData()
        }
      })
      
    }))
    
    presentViewController(alertVC, animated: true, completion: nil)

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
      
      var http : String {
        get {
          let ip = NSUserDefaults.standardUserDefaults().objectForKey("ip") as? String ?? "192.168.43.93"
          let http = prefixHttp + ip + ":8080"
          return http
        }
      }
      
      let URL = NSURL(string: http + faceList[indexPath.item - 1].storePath)
      item.faceImage.image = UIImage(named: "placeholder")
      if let URL = URL {
        item.faceImage.af_setImageWithURL(URL)
        item.faceImage.transform = CGAffineTransformMakeRotation(CGFloat(M_PI / 2));
      }
//      item.faceImage.downloadedFrom(URL!, placeHolder: "placeholder")
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
