package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

import java.math.BigInteger;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class AccountReportCustomerService {

    private Long id;
    private String username;
    private RoleModel role;
    private String fullname;
    private long totalCustomer = 0;
    private Double grade = 0d;

    public AccountReportCustomerService() {
    }

    public AccountReportCustomerService(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.fullname = entity.getFullname();
            this.role = new RoleModel(entity.getRole());
        }
    }

    public void from(Object[] obj){
        if (obj.length == 3){
            this.totalCustomer = obj[1] != null ? ((BigInteger)obj[1]).longValue() : 0;
            this.grade = obj[2] != null ? (Double) obj[2] : 0;
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

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Long getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(Long totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
