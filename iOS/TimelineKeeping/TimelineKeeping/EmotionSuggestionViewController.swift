//
//  EmotionSuggestionViewController.swift
//  TimelineKeeping
//
//  Created by Le Thanh Tan on 9/21/16.
//  Copyright © 2016 Le Thanh Tan. All rights reserved.
//

import UIKit
import Charts

class EmotionSuggestionViewController: UIViewController {

  var emotionData : [Double] = []
  let emotionStatus = ["anger", "contempt", "disgust", "fear", "happiness", "neutral", "sadness", "surprise"]
  let verticalData: [Double] = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]
  
  var emotions: [Emotion] = [] {
    didSet {
      let emotion = emotions.first    
      emotionData = [emotion!.anger!, emotion!.contempt!, emotion!.disgust!, emotion!.fear!, emotion!.happiness!, emotion!.neutral!, emotion!.sadness!, emotion!.surprise!]
    }
  }
  
  
  @IBOutlet weak var barChart: BarChartView!
  @IBOutlet weak var horizentalChart: HorizontalBarChartView!
  
  @IBOutlet weak var ageLabel: UILabel!
  @IBOutlet weak var genderLabel: UILabel!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    for i in 0..<emotionData.count {
      if emotionData[i] < 0.000001 {
        emotionData[i] /= 0.00001
      } else if emotionData[i] < 0.00001 {
        emotionData[i] /= 0.0001
      } else if emotionData[i] < 0.0001 {
        emotionData[i] /= 0.001
      } else if emotionData[i] < 0.001 {
        emotionData[i] /= 0.01
      }
    }
    
    barChart.descriptionText = "";
    barChart.xAxis.labelPosition = .Bottom
    barChart.xAxis.setLabelsToSkip(0)
    barChart.leftAxis.axisMinValue = 0.0
    barChart.leftAxis.axisMaxValue = 1.2
    barChart.rightAxis.axisMinValue = 0.0
    barChart.rightAxis.axisMaxValue = 1.2
    
    barChart.scaleYEnabled = true
    barChart.scaleXEnabled = true
    barChart.pinchZoomEnabled = false
    barChart.doubleTapToZoomEnabled = false
    barChart.xAxis.drawGridLinesEnabled = false
    barChart.xAxis.wordWrapEnabled = true
    
    barChart.animate(yAxisDuration: 1.5, easingOption: .EaseInOutQuart)
    setDataCount()

    if let age = emotions.first?.age {
      ageLabel.text = "Age of face: " + String(format: "%.1f", age)
    }
    
    genderLabel.text = emotions.first?.gender.description
    
  }
  
  @IBAction func onCloseTapped(sender: UIButton) {
    dismissViewControllerAnimated(true, completion: nil)
  }
  

  func setDataCount() {
    
    // Initialize an array to store chart data entries (values; y axis)
    var emotionEntries = [ChartDataEntry]()
    
    for i in 0..<emotionStatus.count {
      let emotionEntry = BarChartDataEntry(value: emotionData[i], xIndex: i)
      emotionEntries.append(emotionEntry)
    }
    
    // Create bar chart data set containing salesEntries
    let chartDataSet = BarChartDataSet(yVals: emotionEntries, label: "")
    chartDataSet.colors = [.redColor(), .yellowColor(), .greenColor()]
    chartDataSet.colors = ChartColorTemplates.joyful()
    
    // Create bar chart data with data set and array with values for x axis
    let chartData = BarChartData(xVals: emotionStatus, dataSets: [chartDataSet])

    // Set bar chart data to previously created data
    barChart.data = chartData
    
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
  }

}

extension EmotionSuggestionViewController: ChartViewDelegate {
  
}
