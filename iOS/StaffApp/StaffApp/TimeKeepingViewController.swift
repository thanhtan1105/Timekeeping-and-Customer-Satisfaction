//
//  TimeKeepingViewController.swift
//  StaffApp
//
//  Created by Le Thanh Tan on 10/4/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit

class TimeKeepingViewController: BaseViewController {

  @IBOutlet weak var tableView: UITableView!
  @IBOutlet weak var employeeNameLabel: UILabel!
  @IBOutlet weak var viewMenu: CVCalendarMenuView!
  @IBOutlet weak var viewCalendar: CVCalendarView!
  @IBOutlet weak var labelMonth: UILabel!
  
  let user = Account.getAccount()
  var todoLists: [TodoList] = []
  var shouldShowDaysOut = true
  var animationFinished = true
  var selectedDay:DayView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    initLayout()
    tableView.delegate = self
    tableView.dataSource = self
    initLoadData()
    
    if let selectedDay = selectedDay {
      loadTaskBelongToDate(selectedDay: selectedDay.date)
    } else {
      loadTaskBelongToDate(selectedDay: CVDate(date: NSDate()))
    }
  }
  
  override func viewWillAppear(animated: Bool) {
    super.viewWillAppear(animated)
  }
  
  func initLoadData() {
  }
  
  @IBAction func onSettingTapped(sender: UIBarButtonItem) {
    
  }
  
}

extension TimeKeepingViewController: UITableViewDelegate, UITableViewDataSource {
  
  func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return todoLists.count
  }
  
  func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let cell = tableView.dequeueReusableCellWithIdentifier("ToDoListTableCell") as! ToDoListTableCell
    cell.todoList = todoLists[indexPath.row]
    cell.delegate = self
    return cell
  }
}

// MARK: - Private method
extension TimeKeepingViewController {
  
  private func initLayout() {
    labelMonth.text = CVDate(date: NSDate()).globalDescription    
    viewCalendar.delegate = self
    viewMenu.delegate = self
    viewCalendar.commitCalendarViewUpdate()
    viewMenu.commitMenuViewUpdate()
    
  }
  
  private func callApiGetToDoList(accountId: String, day: Int, month: Int, year: Int, completion onCompletion: ((todoLists: [TodoList]?, error: NSError?) -> Void)) {
    APIRequest.shareInstance.getToDoList(day, month: month, year: year, accoundId: accountId) { (response: ResponsePackage?, error: ErrorWebservice?) in
      guard error == nil else {
        onCompletion(todoLists: nil, error: NSError(domain: "com.thanhtan", code: 0, userInfo: ["info" : (error?.error_description)!]))
        return
      }
      
      let dict = response?.response as! [String : AnyObject]
      let success = dict["success"] as? Int
      if success == 1 {
        let todoListData = dict["data"] as! [[String : AnyObject]]
        let todoListLists = TodoList.todoLists(array: todoListData)
        onCompletion(todoLists: todoListLists, error: nil)
      } else {
        onCompletion(todoLists: nil, error: nil)
      }
    }
  }
  
  private func loadTaskBelongToDate(selectedDay selectedDay: CVDate) {
    let day = selectedDay.day
    let month = selectedDay.month
    let year = selectedDay.year
    
    // TODO
    let id = Account.getAccount()!.id!
    callApiGetToDoList(String(id), day: day, month: month, year: year) { (todoLists, error) in
      if let todoLists = todoLists {
        self.todoLists = todoLists
        dispatch_async(dispatch_get_main_queue(), {
          self.tableView.reloadData()
        })
      }
    }
  }
}

extension TimeKeepingViewController: CVCalendarViewDelegate, CVCalendarMenuViewDelegate {
  
  /// Required method to implement!
  func presentationMode() -> CalendarMode {
    return .MonthView
  }
  
  /// Required method to implement!
  func firstWeekday() -> Weekday {
    return .Sunday
  }
  
  // MARK: Optional methods
  
  func shouldShowWeekdaysOut() -> Bool {
    return shouldShowDaysOut
  }
  
  func shouldAnimateResizing() -> Bool {
    return true // Default value is true
  }
  
  func didSelectDayView(dayView: CVCalendarDayView, animationDidFinish: Bool) {
    print("\(dayView.date.commonDescription) is selected!")
    selectedDay = dayView
    loadTaskBelongToDate(selectedDay: selectedDay.date)
  }
  
  func presentedDateUpdated(date: CVDate) {
    if labelMonth.text != date.globalDescription && self.animationFinished {
      let updatedMonthLabel = UILabel()
      updatedMonthLabel.textColor = labelMonth.textColor
      updatedMonthLabel.font = labelMonth.font
      updatedMonthLabel.textAlignment = .Center
      updatedMonthLabel.text = date.globalDescription
      updatedMonthLabel.sizeToFit()
      updatedMonthLabel.alpha = 0
      updatedMonthLabel.center = self.labelMonth.center
      
      let offset = CGFloat(48)
      updatedMonthLabel.transform = CGAffineTransformMakeTranslation(0, offset)
      updatedMonthLabel.transform = CGAffineTransformMakeScale(1, 0.1)
      
      UIView.animateWithDuration(0.35, delay: 0, options: UIViewAnimationOptions.CurveEaseIn, animations: {
        self.animationFinished = false
        self.labelMonth.transform = CGAffineTransformMakeTranslation(0, -offset)
        self.labelMonth.transform = CGAffineTransformMakeScale(1, 0.1)
        self.labelMonth.alpha = 0
        
        updatedMonthLabel.alpha = 1
        updatedMonthLabel.transform = CGAffineTransformIdentity
        
      }) { _ in
        
        self.animationFinished = true
        self.labelMonth.frame = updatedMonthLabel.frame
        self.labelMonth.text = updatedMonthLabel.text
        self.labelMonth.transform = CGAffineTransformIdentity
        self.labelMonth.alpha = 1
        updatedMonthLabel.removeFromSuperview()
      }
      
      self.view.insertSubview(updatedMonthLabel, aboveSubview: self.labelMonth)
    }
  }
  
  func topMarker(shouldDisplayOnDayView dayView: CVCalendarDayView) -> Bool {
    return true
  }
  
  func dotMarker(shouldShowOnDayView dayView: CVCalendarDayView) -> Bool {
    return true
  }
  
  func dotMarker(colorOnDayView dayView: CVCalendarDayView) -> [UIColor] {
    
    let red = CGFloat(86.0 / 255.0)
    let green = CGFloat(183.0 / 255.0)
    let blue = CGFloat(44.0 / 255)
    
    let color = UIColor(red: red, green: green, blue: blue, alpha: 1)
    return [color]
   }
  
  func dotMarker(shouldMoveOnHighlightingOnDayView dayView: CVCalendarDayView) -> Bool {
    return true
  }
  
  func dotMarker(sizeOnDayView dayView: DayView) -> CGFloat {
    return 5
  }
  
  
  func weekdaySymbolType() -> WeekdaySymbolType {
    return .Short
  }
  
  
  func shouldShowCustomSingleSelection() -> Bool {
    return false
  }
  
  func preliminaryView(shouldDisplayOnDayView dayView: DayView) -> Bool {
    if (dayView.isCurrentDay) {
      return true
    }
    return false
  }
  
  func supplementaryView(shouldDisplayOnDayView dayView: DayView) -> Bool {
    return false
  }
}


// MARK: - CVCalendarViewAppearanceDelegate

extension TimeKeepingViewController: CVCalendarViewAppearanceDelegate {
  func dayLabelPresentWeekdayInitallyBold() -> Bool {
    return false
  }
  
  func spaceBetweenDayViews() -> CGFloat {
    return 2
  }
}

// MARK: - ToDoListTableViewCell 
extension TimeKeepingViewController: ToDoListTableCellDelegate {
  // change status
  func todoListTableCell(todoListTableCell: ToDoListTableCell, didTapTask isComplete: Bool, withId id: Int) {
    let index = todoLists.indexOf { (todoList: TodoList) -> Bool in
      return todoList.id == id
    }
    
    if let index = index {
      todoLists[index].isComplete = isComplete
      tableView.reloadRowsAtIndexPaths([NSIndexPath(forItem: index, inSection: 0)], withRowAnimation: UITableViewRowAnimation.None)
    }
  }
}
