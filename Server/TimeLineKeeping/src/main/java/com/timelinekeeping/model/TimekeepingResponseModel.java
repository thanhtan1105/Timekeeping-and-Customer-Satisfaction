package com.timelinekeeping.model;

import java.time.Month;
import java.time.Year;
import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/25/2016.
 */
public class TimekeepingResponseModel {
    private Date nowDate;
    private int year;
    private int month;
    private Integer dayWork;

    private List<TimeKeepingEmployeeModel> listEmpolyee;

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

    public Integer getDayWork() {
        return dayWork;
    }

    public void setDayWork(Integer dayWork) {
        this.dayWork = dayWork;
    }

    public List<TimeKeepingEmployeeModel> getListEmpolyee() {
        return listEmpolyee;
    }

    public void setListEmpolyee(List<TimeKeepingEmployeeModel> listEmpolyee) {
        this.listEmpolyee = listEmpolyee;
    }
}
