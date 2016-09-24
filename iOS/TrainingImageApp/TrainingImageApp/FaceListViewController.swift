//
//  FaceListViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class FaceListViewController: BaseViewController {

  @IBOutlet weak var collectionView: UICollectionView!
  
  var faceImage: [UIImage] = []
  
  override func viewDidLoad() {
    super.viewDidLoad()
    collectionView.delegate = self
    collectionView.dataSource = self
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
  
}

extension FaceListViewController: UICollectionViewDelegate, UICollectionViewDataSource, UICollectionViewDelegateFlowLayout {
  func numberOfSectionsInCollectionView(collectionView: UICollectionView) -> Int {
    return 1
  }
  
  func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
    return faceImage.count + 1
  }
  
  func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell {
    if indexPath.item == 0 {
      // new
      let item = collectionView.dequeueReusableCellWithReuseIdentifier(AddNewFaceCollectionCell.ClassName, forIndexPath: indexPath) as! AddNewFaceCollectionCell
      item.delegate = self
      return item
    }
    
    let item = collectionView.dequeueReusableCellWithReuseIdentifier(FaceCollectionCell.ClassName, forIndexPath: indexPath) as! FaceCollectionCell
    item.faceImage.image = faceImage[indexPath.item - 1]
    return item
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
