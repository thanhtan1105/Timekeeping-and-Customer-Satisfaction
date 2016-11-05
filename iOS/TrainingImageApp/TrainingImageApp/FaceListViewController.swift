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
  
  var faceImage: [UIImage] = []
  var oldImageLink: [String] = []
  
  override func viewDidLoad() {
    super.viewDidLoad()
    collectionView.delegate = self
    collectionView.dataSource = self
    let employee = Employee.getEmployeeFromUserDefault()
    getFace(String(employee.id!))
  }
  
  override func viewDidAppear(animated: Bool) {
    super.viewDidAppear(animated)
    collectionView.reloadData()
  }
  
  @IBAction func onFinishTapped(sender: UIBarButtonItem) {
    let alertVC = UIAlertController(title: "Finish", message: "Do you want to finish training image", preferredStyle: .Alert)
    alertVC.addAction(UIAlertAction(title: "OK", style: .Default, handler: { (action: UIAlertAction) in
      
      if self.faceImage.count == 0 {
        self.navigationController?.popToRootViewControllerAnimated(true)
      } else {
        let personGroupId = String(Department.getDepartmentFromUserDefault().id!)
        APIRequest.shareInstance.sendTrainingStatus(personGroupId, onCompletion: { (response: ResponsePackage?, error: ErrorWebservice?) in
          print(response?.response)
          self.navigationController?.popToRootViewControllerAnimated(true)
        })
      }
      
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
        let faceList = Face.faceList(data)
        for face in faceList {
          self.oldImageLink.append(face.storePath)
        }
        self.collectionView.reloadSections(NSIndexSet(index: 1))
      }
    }
  }
}

extension FaceListViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
  
  func collectionView(collectionView: UICollectionView, viewForSupplementaryElementOfKind kind: String, atIndexPath indexPath: NSIndexPath) -> UICollectionReusableView {
    
    var reuseableView = UICollectionReusableView()
    if kind == UICollectionElementKindSectionHeader {
      let headerView = collectionView.dequeueReusableSupplementaryViewOfKind(UICollectionElementKindSectionHeader, withReuseIdentifier: "HeaderCollectionReusableView", forIndexPath: indexPath) as! HeaderCollectionReusableView
      if indexPath.section == 0 {
        let title = "New"
        headerView.titleLabel.text = title
      } else {
        let title = "Added"
        headerView.titleLabel.text = title
      }
      reuseableView = headerView
    }
    
    return reuseableView
  }
  
  func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
    return 2
  }
  
  func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    if section == 0 {
      return faceImage.count + 1
    } else {
      return oldImageLink.count
    }
    
  }
  
  func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
    if indexPath.item == 0 && indexPath.section == 0 {
      // new
      let item = collectionView.dequeueReusableCellWithReuseIdentifier(AddNewFaceCollectionCell.ClassName, forIndexPath: indexPath) as! AddNewFaceCollectionCell
      item.delegate = self
      return item
    } else if indexPath.section == 0 {
      let item = collectionView.dequeueReusableCellWithReuseIdentifier(FaceCollectionCell.ClassName, forIndexPath: indexPath) as! FaceCollectionCell
      item.faceImage.transform = CGAffineTransformMakeRotation((0 * CGFloat(M_PI)) / 180.0)
      item.faceImage.image = faceImage[indexPath.item - 1]
      return item
    } else if indexPath.section == 1 {
      let item = collectionView.dequeueReusableCellWithReuseIdentifier(FaceCollectionCell.ClassName, forIndexPath: indexPath) as! FaceCollectionCell
      
      let URL = NSURL(string: oldImageLink[indexPath.row])
      item.faceImage.downloadedFrom(URL!)
      
      return item
    }
    
    return UICollectionViewCell()
  }
  
  func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize {
    return CGSize(width: screenSize.width / 2 - 10, height: screenSize.height / 3.5 )
  }
}

extension FaceListViewController: CameraViewControllerDelegate, AddNewFaceCollectionCellDelegate {
  
  func cameraViewController(cameraViewController: CameraViewController, didFinishUploadFace image: UIImage) {
    faceImage.insert(image, atIndex: 0)
  }
  
  func didAddNewFaceTapped() {
    let cameraVC = UIStoryboard(name: "Main", bundle: nil).instantiateViewControllerWithIdentifier("CameraViewController") as! CameraViewController
    navigationController?.presentViewController(cameraVC, animated: true, completion: {
      cameraVC.delegate = self
    })
  }
}
