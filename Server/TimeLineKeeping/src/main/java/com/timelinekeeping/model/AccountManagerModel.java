package com.timelinekeeping.model;

import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.AccountEntity;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class AccountManagerModel {
    private Long id;
    private String username;
    private String email;
    private Gender gender;
    private String fullName;

    public AccountManagerModel() {
    }

    public AccountManagerModel(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.fullName = entity.getFullName();
            this.username = entity.getUsername();
            this.gender = entity.getGender();
            this.email = entity.getEmail();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
