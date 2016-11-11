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
    @Column(name = "message", length = Integer.MAX_VALUE)
    private String message;

    @Basic
    @Column(name = "age_of_face")
    private Double ageOfFace;

    @Basic
    @Column(name = "gender")
    private Gender gender;

    @Basic
    @Column(name = "emotion")
    private EEmotion emotion;

    public MessageEntity() { }

    public MessageEntity(String message, Double ageOfFace, Gender gender, EEmotion emotion) {
        this.message = message;
        this.ageOfFace = ageOfFace;
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
