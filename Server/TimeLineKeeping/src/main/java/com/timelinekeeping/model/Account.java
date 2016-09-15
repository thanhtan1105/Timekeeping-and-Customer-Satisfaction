package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */
public class Account {
    private Long id;
    private String username;
    private String role;
    private String userCode;
    private Integer active;
    private String fullname;
    private String token;

    public Account() {
    }

    public Account(AccountEntity entity) {
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.role = entity.getRole().getName();
        this.active = entity.getActive();
        this.fullname = entity.getFullname();
        this.token = entity.getToken();
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

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }


}
