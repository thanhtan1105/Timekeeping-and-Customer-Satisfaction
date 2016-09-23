package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

/**
 * Created by HienTQSE60896 on 9/23/2016.
 */
public class AccountNotificationModel {
    private Long id;
    private String username;
    private String fullname;

    public AccountNotificationModel() {
    }


    public AccountNotificationModel(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.fullname = entity.getFullname();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
