package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Entity
@Table(name = "message", schema = "mydb")
public class MessageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "message")
    private String message;

    @Basic
    @Column(name = "fromAge")
    private Double fromAge;

    @Basic
    @Column(name = "toAge")
    private Double toAge;

    @Basic
    @Column(name = "gender")
    private Gender gender;

    @Basic
    @Column(name = "emotion")
    private EEmotion emotion;

    public MessageEntity(Long id, String message, Double fromAge, Double toAge, Gender gender, EEmotion emotion) {
        this.id = id;
        this.message = message;
        this.fromAge = fromAge;
        this.toAge = toAge;
        this.gender = gender;
        this.emotion = emotion;
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

    public Double getFromAge() {
        return fromAge;
    }

    public void setFromAge(Double fromAge) {
        this.fromAge = fromAge;
    }

    public Double getToAge() {
        return toAge;
    }

    public void setToAge(Double toAge) {
        this.toAge = toAge;
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
