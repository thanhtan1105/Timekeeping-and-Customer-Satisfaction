package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

/**
 * Created by HienTQSE60896 on 9/15/2016.
 */

public class AccountModel {

    private Long id;
    private String username;
    private RoleModel role;
    private String userCode;
    private Integer active;
    private String fullname;
    private String token;
    private DepartmentModel department;

    public AccountModel() {
    }

    public AccountModel(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.role = new RoleModel(entity.getRole());
            this.active = entity.getActive().getIndex();
            this.fullname = entity.getFullname();
            this.userCode = entity.getUserCode();
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

    public void setRole(RoleModel role) {
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

    public RoleModel getRole() {
        return role;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }
}
