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
    private double anger;

    @Column(name = "contempt")
    private double contempt;

    @Column(name = "disgust")
    private double disgust;

    @Column(name = "fear")
    private double fear;

    @Column(name = "happiness")
    private double happiness;

    @Column(name = "neutral")
    private double neutral;

    @Column(name = "sadness")
    private double sadness;

    @Column(name = "surprise")
    private double surprise;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "smile")
    private double smile;

    public Emotion(Timestamp createTime,
                   Long createBy,
                   double anger,
                   double contempt,
                   double disgust,
                   double fear,
                   double happiness,
                   double neutral,
                   double sadness,
                   double surprise,
                   int age,
                   Gender gender,
                   double smile) {
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

    public double getAnger() {
        return anger;
    }

    public void setAnger(double anger) {
        this.anger = anger;
    }

    public double getContempt() {
        return contempt;
    }

    public void setContempt(double contempt) {
        this.contempt = contempt;
    }

    public double getDisgust() {
        return disgust;
    }

    public void setDisgust(double disgust) {
        this.disgust = disgust;
    }

    public double getFear() {
        return fear;
    }

    public void setFear(double fear) {
        this.fear = fear;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public double getNeutral() {
        return neutral;
    }

    public void setNeutral(double neutral) {
        this.neutral = neutral;
    }

    public double getSadness() {
        return sadness;
    }

    public void setSadness(double sadness) {
        this.sadness = sadness;
    }

    public double getSurprise() {
        return surprise;
    }

    public void setSurprise(double surprise) {
        this.surprise = surprise;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public double getSmile() {
        return smile;
    }

    public void setSmile(double smile) {
        this.smile = smile;
    }
}
