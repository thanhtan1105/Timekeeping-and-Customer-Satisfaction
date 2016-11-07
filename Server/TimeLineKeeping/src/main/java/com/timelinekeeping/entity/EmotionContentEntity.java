package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.Gender;

import javax.persistence.*;

/**
 * Created by HienTQSE60896 on 10/14/2016.
 */
@Entity
@Table(name = "emotion_content", schema = "mydb")
public class EmotionContentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "emotion_first")
    private EEmotion emotionFirst = EEmotion.NONE;

    @Column(name = "emotion_second")
    private EEmotion emotionSecond = EEmotion.NONE;

    @Column(name = "emotion_third")
    private EEmotion emotionThird = EEmotion.NONE;

    @Column(name = "from_age")
    private Double fromAge;

    @Column(name = "to_age")
    private Double toAge;

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private EStatus status = EStatus.ACTIVE;

    @Column(name = "vote")
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
