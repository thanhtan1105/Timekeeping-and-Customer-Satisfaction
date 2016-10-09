//
//  ToDoListTableCell.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/9/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

protocol ToDoListTableCellDelegate: class  {
  func todoListTableCell(todoListTableCell: ToDoListTableCell, didTapTask isComplete: Bool, withId id: Int)
}

class ToDoListTableCell: UITableViewCell {

  static let ClassName = "ToDoListTableCell"
  
  @IBOutlet weak var completeButton: UIButton!
  @IBOutlet weak var titleLabel: UILabel!
  
  
  var todoList: TodoList! {
    didSet {
      titleLabel.text = todoList.title
      let imageName = todoList.isComplete == true ? "radio_check" : "radio_uncheck"
      completeButton.setBackgroundImage(UIImage(named: imageName), forState: UIControlState.Normal)
    }
  }
  
  weak var delegate: ToDoListTableCellDelegate?
  
  override func awakeFromNib() {
    super.awakeFromNib()    
  }

  override func setSelected(selected: Bool, animated: Bool) {
      super.setSelected(selected, animated: animated)
    
  }

  @IBAction func onDoneTapped(sender: UIButton) {

    callApiSelectedTask(String(todoList.id!)) { (isComplete, error) in
      guard error == nil else {
        return
      }
      
      if let isComplete = isComplete {
        if self.delegate != nil {
          self.todoList.isComplete = isComplete
          let imageName = self.todoList.isComplete == true ? "radio_check" : "radio_uncheck"
          self.completeButton.setBackgroundImage(UIImage(named: imageName), forState: UIControlState.Normal)
          self.delegate?.todoListTableCell(self, didTapTask: isComplete, withId: self.todoList.id!)
        }
      }
    }
  }
}

// MARK: - Private method
extension ToDoListTableCell {
  private func callApiSelectedTask(id: String, completion onCompletion: ((isComplete: Bool?, error: NSError?) -> Void)) {
    APIRequest.shareInstance.selectTask(id) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        print(error)
        onCompletion(isComplete: nil, error: NSError(domain: "com.lethanhtan", code: 0, userInfo: ["info" : (error?.error_description)!]))
        return
      }
      
      let dict = response?.response as! [String : AnyObject]
      let data = dict["data"] as! Int
      onCompletion(isComplete: Bool(data), error: nil)
    }
  }
}
