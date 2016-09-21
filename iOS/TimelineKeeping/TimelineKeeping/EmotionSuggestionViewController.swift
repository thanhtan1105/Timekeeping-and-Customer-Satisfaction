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

  var emotion: Emotion!
  
  @IBOutlet weak var horizontalBarChart: HorizontalBarChartView!
  
  override func viewDidLoad() {
    super.viewDidLoad()
    let data = [[emotion.anger!, emotion.contempt!, emotion.disgust!, emotion.fear!, emotion.happiness!, emotion.neutral!, emotion.sadness!, emotion.surprise!]]
    horizontalBarChart.delegate = self
    horizontalBarChart.noDataTextDescription = "You need to provide data for the chart."
    horizontalBarChart.drawBarShadowEnabled = false
    horizontalBarChart.leftAxis.startAtZeroEnabled = false

    let verticalData: [Double] = [0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0]
    let horizentalData = ["contempt", "disgust", "fear", "happiness", "neutral", "sadness", "surprise"]
    
//    let data: BarChartData = BarChartData(xVals: horizentalData)
    
  }

  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
  }

}

extension EmotionSuggestionViewController: ChartViewDelegate {
  
}
