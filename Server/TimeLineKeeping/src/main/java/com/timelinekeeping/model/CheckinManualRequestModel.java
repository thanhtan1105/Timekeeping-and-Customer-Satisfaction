package com.timelinekeeping.model;

import java.io.Serializable;

/**
 * Created by TrungNN on 9/25/2016.
 */
public class CheckinManualRequestModel implements Serializable {
    private Long accountId;
    boolean statusCheckin;
    private String note;

    public CheckinManualRequestModel() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public boolean isStatusCheckin() {
        return statusCheckin;
    }

    public void setStatusCheckin(boolean statusCheckin) {
        this.statusCheckin = statusCheckin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "CheckinManualRequestModel{" +
                "accountId=" + accountId +
                ", statusCheckin=" + statusCheckin +
                ", note='" + note + '\'' +
                '}';
    }
}
