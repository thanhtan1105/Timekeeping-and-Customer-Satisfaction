package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 9/20/2016.
 */
public class AccountCreate {
    private String username;
    private String userCode;
    private Integer active;
    private String fullname;
    private Long roleId;
    private Long departmentId;

    public AccountCreate() {
    }

    public AccountCreate(String username, Long roleId, String userCode, Integer active, String fullname) {
        this.username = username;
        this.roleId = roleId;
        this.userCode = userCode;
        this.active = active;
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
}
