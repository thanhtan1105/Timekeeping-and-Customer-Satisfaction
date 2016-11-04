package com.timelinekeeping.model;

import com.timelinekeeping.constant.EEmotion;

/**
 * Created by HienTQSE60896 on 10/7/2016.
 */
public class EmotionCompare {
    private EEmotion emotion;
    private String emotionName;
    private Double value;
    private Double percent;

    public EmotionCompare(EEmotion emotion, Double value) {
        this.emotion = emotion;
        this.value = value;
        this.emotionName = emotion.getName();
    }

    public EmotionCompare(EEmotion emotion, Double value, Double percent) {
        this.emotion = emotion;
        this.value = value;
        this.percent = percent;
        this.emotionName = emotion.getName();
    }

    public EEmotion getEmotion() {
        return emotion;
    }

    public void setEmotion(EEmotion emotion) {
        this.emotion = emotion;
        this.emotionName = emotion.getName();
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getPercent() {
        return percent * 100;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }

    public String getEmotionName() {
        return emotionName;
    }

    public void setEmotionName(String emotionName) {
        this.emotionName = emotionName;
    }

    @Override
    public String toString() {
        return "EmotionCompare{" +
                "emotion=" + emotion +
                ", value=" + value +
                '}';
    }
}
