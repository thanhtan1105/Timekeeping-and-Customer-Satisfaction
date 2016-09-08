package com.timelinekeeping.model;

import java.text.DateFormat;

/**
 * Created by HienTQSE60896 on 9/9/2016.
 */
public class PersonGroupTrainingStatus {
    private String status;
    private String createdDateTime;
    private String lastActionDateTime;
    private String message;

    public PersonGroupTrainingStatus() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getLastActionDateTime() {
        return lastActionDateTime;
    }

    public void setLastActionDateTime(String lastActionDateTime) {
        this.lastActionDateTime = lastActionDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
