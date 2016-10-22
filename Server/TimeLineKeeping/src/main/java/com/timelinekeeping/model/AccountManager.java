package com.timelinekeeping.model;

import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.AccountEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class AccountManager {
    private Long id;
    private String username;
    private Gender gender;
    private String token;
    private String fullname;

    public AccountManager() {
    }

    public AccountManager(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.fullname = entity.getFullname();
            this.username = entity.getUsername();
            this.gender = entity.getGender();
            this.token = entity.getToken();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
