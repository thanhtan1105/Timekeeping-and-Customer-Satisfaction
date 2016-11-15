package com.timelinekeeping.model;

import com.timelinekeeping.constant.EGradeReport;
import com.timelinekeeping.entity.AccountEntity;

import java.math.BigInteger;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class AccountReportCustomerService {

    private Long id;
    private String username;
    private RoleModel role;
    private String fullName;
    private long totalCustomer = 0;
    private Double grade = 0d;
    private String evaluation;

    public AccountReportCustomerService() {
    }

    public AccountReportCustomerService(AccountEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.fullName = entity.getFullName();
            this.role = new RoleModel(entity.getRole());
        }
    }

    public void from(ReportCustomerEmotionQuery report) {
        if (report != null) {
            this.totalCustomer = report.getCount() != null ? report.getCount() : 0;
            this.grade = report.getGrade() != null ? report.getGrade(): 0;
            this.evaluation = this.grade != null ? EGradeReport.fromGrade(this.grade).getName() : null;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
