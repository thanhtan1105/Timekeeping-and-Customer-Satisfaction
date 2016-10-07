package com.timelinekeeping.modelMCS;

import com.timelinekeeping.constant.EEmotion;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public class EmotionRecognizeScores {
    private Double anger;
    private Double contempt;
    private Double disgust;
    private Double fear;
    private Double happiness;
    private Double neutral;
    private Double sadness;
    private Double surprise;

    public EmotionRecognizeScores() {
    }

    public EmotionRecognizeScores(Double anger, Double contempt, Double disgust, Double fear,
                                  Double happiness, Double neutral, Double sadness, Double surprise) {
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
    }

    public Double getAnger() {
        return anger;
    }

    public void setAnger(Double anger) {
        this.anger = anger;
    }

    public Double getContempt() {
        return contempt;
    }

    public void setContempt(Double contempt) {
        this.contempt = contempt;
    }

    public Double getDisgust() {
        return disgust;
    }

    public void setDisgust(Double disgust) {
        this.disgust = disgust;
    }

    public Double getFear() {
        return fear;
    }

    public void setFear(Double fear) {
        this.fear = fear;
    }

    public Double getHappiness() {
        return happiness;
    }

    public void setHappiness(Double happiness) {
        this.happiness = happiness;
    }

    public Double getNeutral() {
        return neutral;
    }

    public void setNeutral(Double neutral) {
        this.neutral = neutral;
    }

    public Double getSadness() {
        return sadness;
    }

    public void setSadness(Double sadness) {
        this.sadness = sadness;
    }

    public Double getSurprise() {
        return surprise;
    }

    public void setSurprise(Double surprise) {
        this.surprise = surprise;
    }

    public void clearData(double except) {
        if (anger < except) anger = 0d;
        if (contempt < except) contempt = 0d;
        if (disgust < except) disgust = 0d;
        if (fear < except) fear = 0d;
        if (happiness < except) happiness = 0d;
        if (neutral < except) neutral = 0d;
        if (sadness < except) sadness = 0d;
        if (surprise < except) surprise = 0d;
    }

    public List<EEmotion> getEmotionExist(){
        return null;
    }
}
