package com.timelinekeeping.model;

import com.timelinekeeping.entity.AccountEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/25/2016.
 */
public class TimekeepingResponseModel {

    private Long managerId;
    private String departmentName;
    private String managerName;

    private Date nowDate = new Date();
    private int year;
    private int month;

    private List<AccountTKReportModel> listEmployee;

    public TimekeepingResponseModel() {
    }

    public TimekeepingResponseModel(AccountEntity entity, int year, int month) {
        if (entity != null) {
            this.managerId = entity.getId();
            this.departmentName = entity.getDepartment().getName();
            this.managerName = entity.getFullName();
        }
        this.year = year;
        this.month = month;
    }

    public Date getNowDate() {
        return nowDate;
    }

    public void setNowDate(Date nowDate) {
        this.nowDate = nowDate;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<AccountTKReportModel> getListEmployee() {
        return listEmployee;
    }

    public void setListEmployee(List<AccountTKReportModel> listEmployee) {
        this.listEmployee = listEmployee;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
