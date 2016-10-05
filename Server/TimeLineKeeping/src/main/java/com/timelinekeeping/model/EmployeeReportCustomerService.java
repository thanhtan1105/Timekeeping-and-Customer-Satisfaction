package com.timelinekeeping.model;

import java.time.YearMonth;
import java.util.List;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class EmployeeReportCustomerService {
    private YearMonth yearMonth;
    private Integer year;
    private Integer month;

    private Long id;
    private String username;
    private RoleModel role;
    private String fullname;
    private DepartmentModel department;
    private Long totalCustomer;
    private Double grade;

    private List<EmployeeReportDate> reportDate;

    public EmployeeReportCustomerService() {
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
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

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
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

    public List<EmployeeReportDate> getReportDate() {
        return reportDate;
    }

    public void setReportDate(List<EmployeeReportDate> reportDate) {
        this.reportDate = reportDate;
    }
}
