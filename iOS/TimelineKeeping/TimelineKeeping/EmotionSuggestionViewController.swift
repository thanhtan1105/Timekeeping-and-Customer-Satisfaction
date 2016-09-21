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

//  var emotionData = []
  var emotionData = [0.000629107262, 0.0001229907, 0.000005002479, 0.000003291769, 0.00006865142, 0.998241961, 0.00147524744, 0.000082200575]

  var emotion: [Emotion]? {
    didSet {
      //       emotionData = [emotion.anger!, emotion.contempt!, emotion.disgust!, emotion.fear!, emotion.happiness!, emotion.neutral!, emotion.sadness!, emotion.surprise!]
    }
  }
  let horizentalData = ["anger", "contempt", "disgust", "fear", "happiness", "neutral", "sadness", "surprise"]
  let verticalData: [Double] = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]
  
  @IBOutlet weak var horizontalBarChart: HorizontalBarChartView!
  
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
    
    horizontalBarChart.delegate = self
    horizontalBarChart.descriptionText = "Customer emotion chart's"
    horizontalBarChart.drawBarShadowEnabled = false
    horizontalBarChart.setVisibleXRangeMaximum(0.5)
    horizontalBarChart.drawGridBackgroundEnabled = false
    horizontalBarChart.borderLineWidth = 0.1
    horizontalBarChart.xAxis.drawLimitLinesBehindDataEnabled = true
    horizontalBarChart.xAxis.drawLabelsEnabled = true
    horizontalBarChart.xAxis.wordWrapEnabled = true
    horizontalBarChart.drawBordersEnabled = true
    horizontalBarChart.xAxis.setLabelsToSkip(0)
    horizontalBarChart.scaleYEnabled = false
    horizontalBarChart.scaleXEnabled = false
    horizontalBarChart.pinchZoomEnabled = false
    horizontalBarChart.doubleTapToZoomEnabled = false
    horizontalBarChart.xAxis.axisLineColor = UIColor.clearColor()
    horizontalBarChart.animate(yAxisDuration: 1.5, easingOption: .EaseInOutQuart)
    setDataCount()
    
  }

  func setDataCount() {
    
    var xVals = [NSObject]()
    for i in 0..<verticalData.count {
      xVals.append(verticalData[i] * 100)
    }
    
    var yVals: [ChartDataEntry] = []
    for i in 0..<horizentalData.count {
      let val: Double = emotionData[i]
//      yVals.append(BarChartDataEntry(value: val * 100, xIndex: i, data: horizentalData[i]))
      yVals.append(BarChartDataEntry(values: [val], xIndex: i, label: horizentalData[i]))
    }
    
    let set1 = BarChartDataSet(yVals: yVals, label: "Emotion point")
    set1.barSpace = 0.3
    var dataSets = [IChartDataSet]()
    dataSets.append(set1)
    
    let data = BarChartData(xVals: xVals, dataSets: dataSets)
    self.horizontalBarChart.data = data
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
  }

}

extension EmotionSuggestionViewController: ChartViewDelegate {
  
}
