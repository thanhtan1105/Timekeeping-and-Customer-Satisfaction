package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/28/2016.
 */
public class EmotionSurvey {

    private String imageName;
    private String Code;
    private String Name;
    private String age;
    private String gender;
    private String emotionMost;
    private Boolean anger;
    private Boolean contempt;
    private Boolean disgust;
    private Boolean fear;
    private Boolean happiness;
    private Boolean neutral;
    private Boolean sadness;
    private Boolean surprise;

    public EmotionSurvey() {
    }

    public EmotionSurvey(String imageName, String code, String name, String age, String gender, String emotionMost, Boolean anger, Boolean contempt, Boolean disgust, Boolean fear, Boolean happiness, Boolean neutral, Boolean sadness, Boolean surprise) {
        this.imageName = imageName;
        Code = code;
        Name = name;
        this.age = age;
        this.gender = gender;
        this.emotionMost = emotionMost;
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmotionMost() {
        return emotionMost;
    }

    public void setEmotionMost(String emotionMost) {
        this.emotionMost = emotionMost;
    }

    public Boolean getAnger() {
        return anger;
    }

    public void setAnger(Boolean anger) {
        this.anger = anger;
    }

    public Boolean getContempt() {
        return contempt;
    }

    public void setContempt(Boolean contempt) {
        this.contempt = contempt;
    }

    public Boolean getDisgust() {
        return disgust;
    }

    public void setDisgust(Boolean disgust) {
        this.disgust = disgust;
    }

    public Boolean getFear() {
        return fear;
    }

    public void setFear(Boolean fear) {
        this.fear = fear;
    }

    public Boolean getHappiness() {
        return happiness;
    }

    public void setHappiness(Boolean happiness) {
        this.happiness = happiness;
    }

    public Boolean getNeutral() {
        return neutral;
    }

    public void setNeutral(Boolean neutral) {
        this.neutral = neutral;
    }

    public Boolean getSadness() {
        return sadness;
    }

    public void setSadness(Boolean sadness) {
        this.sadness = sadness;
    }

    public Boolean getSurprise() {
        return surprise;
    }

    public void setSurprise(Boolean surprise) {
        this.surprise = surprise;
    }
}
