package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

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
    private String fullName;
    private DepartmentModel department;
    private Long totalCustomer;
    private Double gradeAvg;

    private List<EmployeeReportDate> reportDate;

    public EmployeeReportCustomerService() {
    }

    public EmployeeReportCustomerService(Integer year, Integer month, AccountEntity entity) {
        this.year = year;
        this.month = month;
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.role = new RoleModel(entity.getRole());
            this.fullName = entity.getFullName();
            this.department = new DepartmentModel(entity.getDepartment());
        }
    }

    public void complete() {
        this.yearMonth = YearMonth.of(year, month);
        Double sum = 0d;
        Long sumCustomer = 0l;
        for (EmployeeReportDate report : reportDate) {
            sum += report.getGrade() != null ? report.getGrade() * report.getTotalCustomer() : 0;
            sumCustomer += report.getTotalCustomer();
        }
        this.gradeAvg = sumCustomer !=0 ? sum / sumCustomer : 0;
        this.totalCustomer = sumCustomer;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public Double getGradeAvg() {
        return gradeAvg;
    }

    public void setGradeAvg(Double gradeAvg) {
        this.gradeAvg = gradeAvg;
    }

    public List<EmployeeReportDate> getReportDate() {
        return reportDate;
    }

    public void setReportDate(List<EmployeeReportDate> reportDate) {
        this.reportDate = reportDate;
    }

    @Override
    public String toString() {
        return "EmployeeReportCustomerService{" +
                "yearMonth=" + yearMonth +
                ", year=" + year +
                ", month=" + month +
                ", id=" + id +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", fullName='" + fullName + '\'' +
                ", department=" + department +
                ", totalCustomer=" + totalCustomer +
                ", gradeAvg=" + gradeAvg +
                ", reportDate=" + reportDate +
                '}';
    }
}
