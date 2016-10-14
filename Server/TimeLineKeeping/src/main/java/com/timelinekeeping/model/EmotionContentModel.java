package com.timelinekeeping.model;

import com.timelinekeeping.entity.EmotionContentEntity;

/**
 * Created by HienTQSE60896 on 10/14/2016.
 */
public class EmotionContentModel {
    private Long id;
    private String message;
    private Long vote;

    public EmotionContentModel() {
    }

    public EmotionContentModel(EmotionContentEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.message = entity.getMessage();
            this.vote = entity.getVote();
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

    public Long getVote() {
        return vote;
    }

    public void setVote(Long vote) {
        this.vote = vote;
    }
}
