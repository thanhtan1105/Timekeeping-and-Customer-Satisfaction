package com.timelinekeeping.entity;

import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.model.CustomerTransactionModel;
import com.timelinekeeping.util.ValidateUtil;

import javax.persistence.*;

/**
 * Created by HienTQSE60896 on 11/14/2016.
 */
@Entity
@Table(name = "customer")
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "code")
    private String code;

    @Basic
    @Column(name = "year_birth")
    private Integer yearBirth;

    @Basic
    @Column(name = "gender")
    private Gender gender;

    @Basic
    @Column(name = "description")
    private String description;


    public void update(CustomerTransactionModel customerTransactionModel) {
        this.name = ValidateUtil.isNotEmpty(customerTransactionModel.getName()) ? customerTransactionModel.getName() : null;
        this.yearBirth = customerTransactionModel.getYearBirth() != null ? customerTransactionModel.getYearBirth() : null;
        this.gender = customerTransactionModel.getGender() != null ? customerTransactionModel.getGender() : null;
        this.description = customerTransactionModel.getDescription() != null ? customerTransactionModel.getDescription() : null;
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

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
