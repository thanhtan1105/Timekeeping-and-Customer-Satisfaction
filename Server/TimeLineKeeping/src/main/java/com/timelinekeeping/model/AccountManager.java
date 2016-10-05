package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class AccountManager {
    private Long id;
    private String fullname;

    public AccountManager() {
    }

    public AccountManager(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.fullname = entity.getFullname();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
