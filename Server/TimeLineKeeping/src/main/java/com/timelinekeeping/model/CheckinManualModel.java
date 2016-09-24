package com.timelinekeeping.model;

import com.timelinekeeping.constant.ETimeKeeping;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
public class CheckinManualModel {
    private Long accountId;
    private boolean success;
    private String message;

    public CheckinManualModel() {
    }

    public CheckinManualModel(Long accountId) {
        this.accountId = accountId;
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
}
