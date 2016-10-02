package com.timelinekeeping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TrungNN on 9/30/2016.
 */
public class AccountTKDetailsModel implements Serializable {
    private Long accountId;
    private Date selectedDate;

    public AccountTKDetailsModel() {
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    @Override
    public String toString() {
        return "AccountTKDetailsModel{" +
                "accountId=" + accountId +
                ", selectedDate=" + selectedDate +
                '}';
    }
}
