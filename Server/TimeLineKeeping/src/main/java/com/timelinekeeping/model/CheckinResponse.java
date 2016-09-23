package com.timelinekeeping.model;

import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/20/2016.
 */
public class CheckinResponse {
    private Date timeCheckIn;
    private AccountModel account;
    private List<NotificationCheckInModel> messageReminder;

    public CheckinResponse() {
    }


    public CheckinResponse(Date timeCheckIn, AccountModel account, List<NotificationCheckInModel> messageReminder) {
        this.timeCheckIn = timeCheckIn;
        this.account = account;
        this.messageReminder = messageReminder;
    }

    public Date getTimeCheckIn() {
        return timeCheckIn;
    }

    public void setTimeCheckIn(Date timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public AccountModel getAccount() {
        return account;
    }

    public void setAccount(AccountModel account) {
        this.account = account;
    }

//


    public List<NotificationCheckInModel> getMessageReminder() {
        return messageReminder;
    }

    public void setMessageReminder(List<NotificationCheckInModel> messageReminder) {
        this.messageReminder = messageReminder;
    }

    @Override
    public String toString() {
        return "CheckinResponse{" +
                "timeCheckIn=" + timeCheckIn +
                ", account=" + account +
                ", messageReminder=" + messageReminder +
                '}';
    }
}
