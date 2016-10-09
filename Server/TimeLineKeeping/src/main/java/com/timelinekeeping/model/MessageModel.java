package com.timelinekeeping.model;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.entity.MessageEntity;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class MessageModel {

    private Long id;
    private List<String> message;
    private List<String> sugguest;
    private String url;
    private Double ageOfFace;
    private Gender gender;
    private EEmotion emotion;

    public MessageModel() {
    }

    public MessageModel(Long id, List<String> message, List<String> suggest, String url, Double ageOfFace, Gender gender, EEmotion emotion) {
        this.id = id;
        this.message = message;
        this.sugguest = suggest;
        this.url = url;
        this.ageOfFace = ageOfFace;
        this.gender = gender;
        this.emotion = emotion;
    }

    public MessageModel(Long id, String url, Double ageOfFace, Gender gender, EEmotion emotion) {
        this.id = id;
        this.url = url;
        this.ageOfFace = ageOfFace;
        this.gender = gender;
        this.emotion = emotion;
    }

    public MessageModel(EmotionCustomerEntity emotionCustomerEntity) {
        if (emotionCustomerEntity != null) {
            this.ageOfFace = emotionCustomerEntity.getAge();
            this.gender = emotionCustomerEntity.getGender();
            this.emotion = emotion;
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

    public List<String> getSugguest() {
        return sugguest;
    }

    public void setSugguest(List<String> sugguest) {
        this.sugguest = sugguest;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
