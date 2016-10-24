package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.entity.ReminderMessageEntity;

import java.sql.Timestamp;

/**
 * Created by HienTQSE60896 on 9/23/2016.
 */
public class NotificationCheckInModel {

    private Long id;
    private String title;
    private String message;
    private Long time;
    private String location;

    public NotificationCheckInModel(NotificationEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            if (entity.getReminderMessage() != null && entity.getReminderMessage().getActive() != EStatus.DEACTIVE) {
                ReminderMessageEntity reminder = entity.getReminderMessage();
                this.title = reminder.getTitle();
                this.message = reminder.getMessage();
                this.time = reminder.getTime().getTime();
                String location = (reminder.getRoom() != null ? reminder.getRoom().getName(): "")
                        + "" + (reminder.getLocation() != null ?  ", " + reminder.getLocation() : "");
                this.location = location;
            }
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

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
