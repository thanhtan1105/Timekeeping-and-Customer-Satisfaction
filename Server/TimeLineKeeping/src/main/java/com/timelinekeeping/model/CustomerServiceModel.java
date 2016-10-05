package com.timelinekeeping.model;

import com.timelinekeeping.constant.ETransaction;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.CustomerServiceEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lethanhtan on 10/5/16.
 */
public class CustomerServiceModel {

    private Long id;
    private Timestamp createTime = new Timestamp(new Date().getTime());
    private String customerCode;
    private Double grade = 0d;
    private ETransaction status = ETransaction.BEGIN;
    private AccountModel createBy;

    public CustomerServiceModel() {
    }

    public CustomerServiceModel(CustomerServiceEntity customerServiceEntity) {
        if (customerServiceEntity != null) {
            this.id = customerServiceEntity.getId();
            this.createTime = customerServiceEntity.getCreateTime();
            this.customerCode = customerServiceEntity.getCustomerCode();
            this.grade = customerServiceEntity.getGrade();
            this.status = customerServiceEntity.getStatus();
            this.createBy = new AccountModel(customerServiceEntity.getCreateBy());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public ETransaction getStatus() {
        return status;
    }

    public void setStatus(ETransaction status) {
        this.status = status;
    }

    public AccountModel getCreateBy() {
        return createBy;
    }

    public void setCreateBy(AccountModel createBy) {
        this.createBy = createBy;
    }
}
