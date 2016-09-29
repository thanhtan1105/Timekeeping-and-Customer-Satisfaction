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

    private List<TimeKeepingEmployeeModel> listEmployee;

    public TimekeepingResponseModel() {
    }

    public TimekeepingResponseModel(AccountEntity entity, int year, int month) {
        if (entity != null) {
            this.managerId = entity.getId();
            this.departmentName = entity.getDepartment().getName();
            this.managerName = entity.getFullname();
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

    public List<TimeKeepingEmployeeModel> getListEmployee() {
        return listEmployee;
    }

    public void setListEmployee(List<TimeKeepingEmployeeModel> listEmployee) {
        this.listEmployee = listEmployee;
    }

}
