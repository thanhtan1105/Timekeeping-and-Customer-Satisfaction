package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.Gender;

import javax.persistence.*;

/**
 * Created by HienTQSE60896 on 10/14/2016.
 */
@Entity
@Table(name = "emotion_content")
public class EmotionContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "emotion_first", nullable = false)
    private EEmotion emotionFirst = EEmotion.NONE;

    @Basic
    @Column(name = "emotion_second")
    private EEmotion emotionSecond = EEmotion.NONE;

    @Basic
    @Column(name = "emotion_third")
    private EEmotion emotionThird = EEmotion.NONE;

    @Basic
    @Column(name = "from_age")
    private Double fromAge;

    @Basic
    @Column(name = "to_age")
    private Double toAge;

    @Basic
    @Column(name = "gender")
    private Gender gender;

    @Basic
    @Column(name = "message", length = Integer.MAX_VALUE)
    private String message;

    @Basic
    @Column(name = "status", nullable = false)
    private EStatus status = EStatus.ACTIVE;

    @Basic
    @Column(name = "vote", nullable = false)
    private Long vote = 0l;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EEmotion getEmotionFirst() {
        return emotionFirst;
    }

    public void setEmotionFirst(EEmotion emotionFirst) {
        this.emotionFirst = emotionFirst;
    }

    public EEmotion getEmotionSecond() {
        return emotionSecond;
    }

    public void setEmotionSecond(EEmotion emotionSecond) {
        this.emotionSecond = emotionSecond;
    }

    public EEmotion getEmotionThird() {
        return emotionThird;
    }

    public void setEmotionThird(EEmotion emotionThird) {
        this.emotionThird = emotionThird;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public Long getVote() {
        return vote;
    }

    public void setVote(Long vote) {
        this.vote = vote;
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
}
