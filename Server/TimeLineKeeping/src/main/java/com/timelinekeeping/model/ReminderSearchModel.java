package com.timelinekeeping.model;

import com.timelinekeeping.entity.ReminderMessageEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public class ReminderSearchModel {

    private Long id;
    private String title;
    private String message;
    private Date time;
    private String room;

    public ReminderSearchModel() {
    }

    public ReminderSearchModel(ReminderMessageEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.message = entity.getMessage();
            this.time = entity.getTime();
            this.room = entity.getRoom() != null ? entity.getRoom().getName() : null;
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
