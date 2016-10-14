package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.EStatus;

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
    private EEmotion emotionFirst;

    @Column(name = "emotion_second")
    private EEmotion emotionSecond;

    @Column(name = "emotion_third")
    private EEmotion emotionThird;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private EStatus status = EStatus.ACTIVE;

    @Column(name = "vote")
    private Long vote;

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
}