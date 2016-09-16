//
//  BaseViewController.swift
//  TrainingImageApp
//
//  Created by Le Thanh Tan on 9/15/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class BaseViewController: UIViewController {

  override func viewDidLoad() {
    super.viewDidLoad()
    navigationItem.backBarButtonItem = UIBarButtonItem(title: "", style:
      .Plain, target: nil, action: nil)

  }

  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
    print("Memory Warning: ", self)
  }
    


}
