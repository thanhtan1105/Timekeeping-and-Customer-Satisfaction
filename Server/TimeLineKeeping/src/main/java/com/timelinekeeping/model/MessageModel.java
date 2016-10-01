package com.timelinekeeping.model;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.MessageEntity;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class MessageModel {

    private Long id;
    private String message;
    private Double ageOfFace;
    private Gender gender;
    private EEmotion emotion;

    public MessageModel() {
    }

    public MessageModel(MessageEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.message = entity.getMessage();
            this.ageOfFace = entity.getAgeOfFace();
            this.gender = entity.getGender();
            this.emotion = entity.getEmotion();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
}
