package com.timelinekeeping.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by TrungNN on 10/7/2016.
 */
public class AccountCustomerServiceDetails implements Serializable {
    private Long accountId;
    private Date selectedDate;

    public AccountCustomerServiceDetails() {
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
        return "AccountCustomerServiceDetails{" +
                "accountId=" + accountId +
                ", selectedDate=" + selectedDate +
                '}';
    }
}
