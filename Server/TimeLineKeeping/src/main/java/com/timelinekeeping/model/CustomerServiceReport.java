package com.timelinekeeping.model;

import java.time.YearMonth;
import java.util.List;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class CustomerServiceReport {
    private YearMonth timeReport;
    private Integer year;
    private Integer month;
    private DepartmentModel department;
    private Integer totalEmployee;
    private Long totalCustomer;
    private Double gradeAvg;

    private List<AccountReportCustomerService> employeeReport;

    public CustomerServiceReport() {

    }

    public CustomerServiceReport(Integer year, Integer month, DepartmentModel department, List<AccountReportCustomerService> employeeReport) {
        this.year = year;
        this.month = month;
        this.department = department;
        this.employeeReport = employeeReport;
    }

    public void complete() {
        this.timeReport = YearMonth.of(year, month);
        this.totalEmployee = employeeReport.size();
        Double sum = 0d;
        Long sumCustomer = 0l;
        for (AccountReportCustomerService report : employeeReport) {
            sum += report.getGrade() != null ? report.getGrade() *report.getTotalCustomer() : 0;
            sumCustomer += report.getTotalCustomer() ;
        }
        this.gradeAvg = sumCustomer != 0 ? sum / sumCustomer : 0;
        this.totalCustomer = sumCustomer;
    }

    public void setTimeReport(YearMonth timeReport) {
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

    public List<AccountReportCustomerService> getEmployeeReport() {
        return employeeReport;
    }

    public void setEmployeeReport(List<AccountReportCustomerService> employeeReport) {
        this.employeeReport = employeeReport;
    }
}
