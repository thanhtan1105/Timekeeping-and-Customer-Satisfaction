//
//  LeThanhTanLoading.swift
//  Superb
//
//  Created by Le Thanh Tan on 7/8/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import Foundation
import UIKit

class LeThanhTanLoading: UIView {
	
	static var sharedInstance: LeThanhTanLoading {
		return LeThanhTanLoading()
	}
	
	var isShow: Bool = false
	var index: Int = 0
	var imageNameList: [String] = ["ic_loading_inside"]
  var backGroundImage: String = "ic_loading"
  var title: String = ""
	let duration = 2
	
	override init(frame: CGRect) {
		super.init(frame: frame)
	}
	
	func initView(frame: CGRect) {
		self.frame = frame
		self.tag = 280395
		self.backgroundColor = UIColor.clearColor()
		
		let backgroundView = UIView(frame: frame)
		backgroundView.backgroundColor = UIColor.blackColor()
		backgroundView.alpha = 0.5
		self.addSubview(backgroundView)
		
		// white screen
		let temple = UIView(frame: CGRect(x: 0, y: 0, width: 110, height: 110))
		temple.backgroundColor = UIColor.whiteColor()
		temple.layer.cornerRadius = 18
		temple.tag = 0
		
		// avatar image
		let templeView = UIView(frame: temple.frame)
		templeView.frame.size.width = templeView.frame.size.width / 2
		templeView.frame.size.height = templeView.frame.size.height / 2
		templeView.center = temple.center
		
    templeView.tag = 1
    let imageView = UIImageView(frame: templeView.frame)
    imageView.image = UIImage(named: imageNameList[index])
    imageView.layer.cornerRadius = imageView.frame.height / 2
    imageView.tag = 2
    templeView.center = temple.center
    temple.addSubview(imageView)
    
    
    if self.title.characters.count > 0 {
      imageView.center = CGPoint(x: temple.center.x, y: temple.center.y - 20)
      
      // add title
      let titleLabel = UILabel(frame: CGRect(x: 0, y: 0, width: 100, height: 60))
      titleLabel.font = UIFont(name: "System", size: 13.0)
      titleLabel.textAlignment = NSTextAlignment.Center
      titleLabel.tag = 3
      titleLabel.text = self.title
      titleLabel.center = CGPoint(x: templeView.center.x, y: templeView.center.y + 30)
      temple.addSubview(titleLabel)
    } else {
      

    }
    
    temple.center = self.center
    let backgroundImageView = UIImageView(frame: imageView.frame)
    backgroundImageView.image = UIImage(named: backGroundImage)
    backgroundImageView.tag = 1080
    temple.addSubview(backgroundImageView)
		self.addSubview(temple)
		
		
	}
	
	required init?(coder aDecoder: NSCoder) {
		fatalError("init(coder:) has not been implemented")
	}
	
	func showLoadingAddedTo(view: UIView, animated: Bool) {
		if isShow {
			self.removeFromSuperview()
		}
		isShow = true
		initView(view.frame)
		view.addSubview(self)
		showAnimated()
	}
  
  func showLoadingAddedTo(view: UIView, title: String, animated: Bool) {
    if isShow {
      self.removeFromSuperview()
    }
    isShow = true
    self.title = title
    initView(view.frame)
    view.addSubview(self)    
    showAnimated()
  }
	
	func hideLoadingAddedTo(view: UIView, animated: Bool) {
		isShow = false
		view.viewWithTag(280395)?.removeFromSuperview()
		self.removeFromSuperview()		
	}
	
  private func showAnimated() {
		dispatch_async(dispatch_get_main_queue()) {
			let spin = CABasicAnimation(keyPath: "transform.rotation.z")
			spin.fromValue = 0
			spin.toValue = CGFloat(M_PI * 2.0)
			spin.duration = Double(self.duration)
			
			spin.delegate = self
			
			for view in self.subviews {
				if view.tag == 0 {
					for imageView in view.subviews {
						imageView.layer.addAnimation(spin, forKey: "spin")
						break
					}
				}
			}
		}
	}
  
	
	override func animationDidStop(anim: CAAnimation, finished flag: Bool) {
		for view in self.subviews {
			if view.tag == 0 {
				for imageView in view.subviews {
					if imageView.tag == 2 {
						let image = imageView as! UIImageView
						index = index == imageNameList.count - 1 ? 0 : index + 1
						image.image = UIImage(named: imageNameList[index])
						if isShow {
							showAnimated()
						}
					}
				}
			}
		}
	}
	
	
}
