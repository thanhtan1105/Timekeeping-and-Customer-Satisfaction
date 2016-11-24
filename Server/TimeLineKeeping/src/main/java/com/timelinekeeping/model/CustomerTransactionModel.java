package com.timelinekeeping.model;

import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.CustomerEntity;

/**
 * Created by HienTQSE60896 on 11/14/2016.
 */
public class CustomerTransactionModel {

    private Long id;

    private String name;

    private String code;

    private Integer yearBirth;

    private Gender gender;

    private String description;

    private String content;

    private String customerCode;

    public CustomerTransactionModel() {
    }

    public CustomerTransactionModel(CustomerEntity customerEntity) {
        if (customerEntity != null) {
            this.id = customerEntity.getId();
            this.name = customerEntity.getName();
            this.code = customerEntity.getCode();
            this.yearBirth = customerEntity.getYearBirth();
            this.gender = customerEntity.getGender();
            this.description = customerEntity.getDescription();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getYearBirth() {
        return yearBirth;
    }

    public void setYearBirth(Integer yearBirth) {
        this.yearBirth = yearBirth;
    }

    public Gender getGender() {
        return gender;
    }

//    public void setGender(Gender gender) {
//        this.gender = gender;
//    }

    public void setGender(Integer gender) {
        this.gender = gender != null ? Gender.fromIndex(gender) : null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
}
