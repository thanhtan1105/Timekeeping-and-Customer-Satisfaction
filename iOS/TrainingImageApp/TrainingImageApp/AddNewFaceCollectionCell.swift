//
//  AddNewFaceCollectionCell.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/20/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

protocol AddNewFaceCollectionCellDelegate: class {
  func didAddNewFaceTapped()
}

class AddNewFaceCollectionCell: UICollectionViewCell {
  
  static let ClassName = "AddNewFaceCollectionCell"
  weak var delegate: AddNewFaceCollectionCellDelegate?
  
  override func awakeFromNib() {
    self.layer.borderWidth = 1.0
    self.layer.borderColor = UIColor.lightGrayColor().CGColor
  }
  
  @IBAction func onAddTapped(sender: UIButton) {
    delegate?.didAddNewFaceTapped()
  }
  
}
