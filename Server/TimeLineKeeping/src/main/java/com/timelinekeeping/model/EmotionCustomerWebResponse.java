package com.timelinekeeping.model;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.EmotionCustomerEntity;

import java.io.Serializable;

/**
 * Created by TrungNN on 10/4/2016.
 */
public class EmotionCustomerWebResponse implements Serializable {

    private Double anger;

    private Double contempt;

    private Double disgust;

    private Double fear;

    private Double happiness;

    private Double neutral;

    private Double sadness;

    private Double surprise;

    private Double age;

    private Gender gender;

    private EEmotion emotionMost;

    public EmotionCustomerWebResponse() {
    }

    public EmotionCustomerWebResponse(EmotionCustomerEntity entity) {
        this.anger = entity.getAnger();
        this.contempt = entity.getContempt();
        this.disgust = entity.getDisgust();
        this.fear = entity.getFear();
        this.happiness = entity.getHappiness();
        this.neutral = entity.getNeutral();
        this.sadness = entity.getSadness();
        this.surprise = entity.getSurprise();
        this.age = entity.getAge();
        this.gender = entity.getGender();
        this.emotionMost = entity.getEmotionMost();
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

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EEmotion getEmotionMost() {
        return emotionMost;
    }

    public void setEmotionMost(EEmotion emotionMost) {
        this.emotionMost = emotionMost;
    }

    @Override
    public String toString() {
        return "EmotionCustomerWebResponse{" +
                "anger=" + anger +
                ", contempt=" + contempt +
                ", disgust=" + disgust +
                ", fear=" + fear +
                ", happiness=" + happiness +
                ", neutral=" + neutral +
                ", sadness=" + sadness +
                ", surprise=" + surprise +
                ", age=" + age +
                ", gender=" + gender +
                ", emotionMost=" + emotionMost +
                '}';
    }
}
