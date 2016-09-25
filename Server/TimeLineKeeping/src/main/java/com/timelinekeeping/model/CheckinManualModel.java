package com.timelinekeeping.model;

import com.timelinekeeping.constant.ETimeKeeping;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
public class CheckinManualModel {
    private Long accountId;
    private String note;
    private boolean success;
    private String message;

    public CheckinManualModel() {
    }

    public CheckinManualModel(Long accountId) {
        this.accountId = accountId;
    }

    public CheckinManualModel(Long accountId, String note) {
        this.accountId = accountId;
        this.note = note;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
