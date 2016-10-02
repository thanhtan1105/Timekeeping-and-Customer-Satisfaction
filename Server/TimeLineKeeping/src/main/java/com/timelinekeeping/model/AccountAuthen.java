package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.util.UtilApps;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 10/1/2016.
 */
public class AccountAuthen {

    private Long id;
    private String username;
    private RoleAuthen role;
    private String token;
    private String fullname;
    private DepartmentModel department;
    private Date lastLogin = new Date();


    public AccountAuthen() {
        this.token = UtilApps.generateToken();
    }

    public AccountAuthen(AccountEntity entity) {
        this.token = UtilApps.generateToken();
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.role = new RoleAuthen(entity.getRole());
            this.fullname = entity.getFullname();
            this.department = new DepartmentModel(entity.getDepartment());
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

    public RoleAuthen getRole() {
        return role;
    }

    public void setRole(RoleAuthen role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
