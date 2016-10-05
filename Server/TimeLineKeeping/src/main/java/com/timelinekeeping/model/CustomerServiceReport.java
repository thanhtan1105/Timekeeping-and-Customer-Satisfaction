package com.timelinekeeping.model;

import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class CustomerServiceReport {
    private Date timeReport;
    private Integer year;
    private Integer month;
    private DepartmentModel department;

    private List<CustomerServiceReportEmployee> employeeReport;

    public CustomerServiceReport() {
    }

    public Date getTimeReport() {
        return timeReport;
    }

    public void setTimeReport(Date timeReport) {
        this.timeReport = timeReport;
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

    public DepartmentModel getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentModel department) {
        this.department = department;
    }

    public List<CustomerServiceReportEmployee> getEmployeeReport() {
        return employeeReport;
    }

    public void setEmployeeReport(List<CustomerServiceReportEmployee> employeeReport) {
        this.employeeReport = employeeReport;
    }
}
