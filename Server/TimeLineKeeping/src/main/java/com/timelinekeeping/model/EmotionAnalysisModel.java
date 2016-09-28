package com.timelinekeeping.model;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.modelMCS.RectangleImage;

import java.io.Serializable;

/**
 * Created by TrungNN on 9/20/2016.
 */
public class EmotionAnalysisModel implements Serializable {
    private EEmotion emotionMost;
    private Double age;
    private Gender gender;
    private Double smile;
    private RectangleImage rectangleImage;
    private EmotionRecognizeScores emotion;

    public EmotionAnalysisModel() {
    }

    public EmotionAnalysisModel(EEmotion emotionMost, Double age, Gender gender,
                                Double smile, EmotionRecognizeScores emotion) {
        this.emotionMost = emotionMost;
        this.age = age;
        this.gender = gender;
        this.smile = smile;
        this.emotion = emotion;
    }

    public EmotionAnalysisModel(EEmotion emotionMost, EmotionRecognizeScores emotion) {
        this.emotionMost = emotionMost;
        this.emotion = emotion;
    }

    public EEmotion getEmotionMost() {
        return emotionMost;
    }

    public void setEmotionMost(EEmotion emotionMost) {
        this.emotionMost = emotionMost;
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

    public Double getSmile() {
        return smile;
    }

    public void setSmile(Double smile) {
        this.smile = smile;
    }

    public EmotionRecognizeScores getEmotion() {
        return emotion;
    }

    public void setEmotion(EmotionRecognizeScores emotion) {
        this.emotion = emotion;
    }

    public RectangleImage getRectangleImage() {
        return rectangleImage;
    }

    public void setRectangleImage(RectangleImage rectangleImage) {
        this.rectangleImage = rectangleImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmotionAnalysisModel that = (EmotionAnalysisModel) o;

        if (emotionMost != that.emotionMost) return false;
        if (age != null ? !age.equals(that.age) : that.age != null) return false;
        if (gender != that.gender) return false;
        if (smile != null ? !smile.equals(that.smile) : that.smile != null) return false;
        return !(emotion != null ? !emotion.equals(that.emotion) : that.emotion != null);

    }

    @Override
    public int hashCode() {
        int result = emotionMost != null ? emotionMost.hashCode() : 0;
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (smile != null ? smile.hashCode() : 0);
        result = 31 * result + (emotion != null ? emotion.hashCode() : 0);
        return result;
    }
}
