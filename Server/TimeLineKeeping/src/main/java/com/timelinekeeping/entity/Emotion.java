package com.timelinekeeping.entity;

import com.timelinekeeping.constant.Gender;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by lethanhtan on 9/15/16.
 */


@Entity
@Table(name = "emotion")
public class Emotion {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "createTime")
    private Timestamp createTime;
    @Column(name = "createBy")
    private Long createBy;

    @Column(name = "anger")
    private Double anger;

    @Column(name = "contempt")
    private Double contempt;

    @Column(name = "disgust")
    private Double disgust;

    @Column(name = "fear")
    private Double fear;

    @Column(name = "happiness")
    private Double happiness;

    @Column(name = "neutral")
    private Double neutral;

    @Column(name = "sadness")
    private Double sadness;

    @Column(name = "surprise")
    private Double surprise;

    @Column(name = "age")
    private Double age;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "smile")
    private Double smile;

    public Emotion() { }

    public Emotion(Timestamp createTime, Long createBy, Double anger, Double contempt, Double disgust, Double fear, Double happiness, Double neutral, Double sadness, Double surprise, Double age, Gender gender, Double smile) {
        this.createTime = createTime;
        this.createBy = createBy;
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
        this.age = age;
        this.gender = gender;
        this.smile = smile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
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

    public Double getSmile() {
        return smile;
    }

    public void setSmile(Double smile) {
        this.smile = smile;
    }
}