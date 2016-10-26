package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.entity.ReminderMessageEntity;
import com.timelinekeeping.util.UtilApps;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public class ReminderSearchModel {

    private Long id;
    private String title;
    private String message;
    private Date time;
    private AccountNotificationModel manager;

    public ReminderSearchModel() {
    }

    public ReminderSearchModel(ReminderMessageEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.message = entity.getMessage();
            this.time = entity.getTime();
            this.manager = new AccountNotificationModel(entity.getManager());
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

    public AccountNotificationModel getManager() {
        return manager;
    }

    public void setManager(AccountNotificationModel manager) {
        this.manager = manager;
    }
}
