package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
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
    private String userCode;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private Gender gender;
    private EStatus active;
    private RoleModel role;
    private DepartmentModel department;
    private Date timeDeactive;
    private String token;
    private AccountManagerModel manager;

    public AccountModel() {
    }

    public AccountModel(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.userCode = entity.getUserCode();
            this.fullName = entity.getFullName();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.address = entity.getAddress();
            this.note = entity.getNote();
            this.gender = entity.getGender();
            this.active = entity.getActive();
            this.token = entity.getToken();
            this.role = new RoleModel(entity.getRole());
            this.department = new DepartmentModel(entity.getDepartment());
            this.timeDeactive = entity.getTimeDeactive();
            if (entity.getManager() != null){
                this.manager = new AccountManagerModel(entity.getManager());
            }
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

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public AccountManagerModel getManager() {
        return manager;
    }

    public void setManager(AccountManagerModel manager) {
        this.manager = manager;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
