package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatusToDoTask;
import com.timelinekeeping.entity.ToDoListEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lethanhtan on 10/9/16.
 */
public class ToDoListModifyModel {

    private Long id;
    private String title;
    private EStatusToDoTask isComplete;
    private Long timeNotify;
    private Long accountCreated;
    private String message;


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

    public EStatusToDoTask getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(Integer complete) {
        this.isComplete = EStatusToDoTask.fromIndex(complete);
    }

    public Long getTimeNotify() {
        return timeNotify;
    }

    public void setTimeNotify(Long timeNotify) {
        this.timeNotify = timeNotify;
    }

    public Long getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Long accountCreated) {
        this.accountCreated = accountCreated;
    }

    public void setIsComplete(EStatusToDoTask isComplete) {
        this.isComplete = isComplete;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
