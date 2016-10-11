package com.timelinekeeping.model;

import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.RoleEntity;

import java.util.Date;

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
    private DepartmentModel department;
    private Date timeDeactive;
    private Gender gender;
    private String token;

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
            this.token = entity.getToken();
            this.department = new DepartmentModel(entity.getDepartment());
            this.timeDeactive = entity.getTimeDeactive();
            this.gender = entity.getGender();
        }
    }

    public void replaceRele(RoleEntity roleEntity){
        if (roleEntity != null){
            this.role = new RoleAuthen(roleEntity);
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

    public Date getTimeDeactive() {
        return timeDeactive;
    }

    public void setTimeDeactive(Date timeDeactive) {
        this.timeDeactive = timeDeactive;
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
