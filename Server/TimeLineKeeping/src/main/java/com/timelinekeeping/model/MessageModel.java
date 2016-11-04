package com.timelinekeeping.model;

import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.entity.MessageEntity;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class MessageModel {

    private Long id;
    private List<String> message;
    private List<EmotionContentModel> sugguest;
    private List<Pair<String, Double>> emotionPercent;
    private Double ageOfFace;
    private Gender gender;
    private EEmotion emotion;
    private String predict;

    public MessageModel() {
    }

    public MessageModel(EmotionCustomerEntity emotionCustomerEntity) {
        if (emotionCustomerEntity != null) {
            this.ageOfFace = emotionCustomerEntity.getAge();
            this.gender = emotionCustomerEntity.getGender();
            this.predict = String.format("%s - %s", (int)(ageOfFace - IContanst.AGE_AMOUNT), (int)(ageOfFace + IContanst.AGE_AMOUNT));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAgeOfFace() {
        return ageOfFace;
    }

    public void setAgeOfFace(Double ageOfFace) {
        this.ageOfFace = ageOfFace;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EEmotion getEmotion() {
        return emotion;
    }

    public void setEmotion(EEmotion emotion) {
        this.emotion = emotion;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

    public List<EmotionContentModel> getSugguest() {
        return sugguest;
    }

    public void setSugguest(List<EmotionContentModel> sugguest) {
        this.sugguest = sugguest;
    }

    public String getPredict() {
        return predict;
    }

    public void setPredict(String predict) {
        this.predict = predict;
    }

    public List<Pair<String, Double>> getEmotionPercent() {
        return emotionPercent;
    }

    public void setEmotionPercent(List<Pair<String, Double>> emotionPercent) {
        this.emotionPercent = emotionPercent;
    }
}
