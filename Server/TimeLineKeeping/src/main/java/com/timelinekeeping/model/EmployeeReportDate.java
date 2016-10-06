package com.timelinekeeping.model;

import com.timelinekeeping.constant.EDayStatus;

import java.math.BigInteger;
import java.util.Date;

/**
 * Created by HienTQSE60896 on 10/5/2016.
 */
public class EmployeeReportDate {
    private int day;
    private Date date;
    private int totalCustomer = 0;
    private double grade = 0d;
    private EDayStatus dayStatus = EDayStatus.NORMAL;

    public EmployeeReportDate() {
    }

    public EmployeeReportDate(int day, Date date, int totalCustomer, Double grade, EDayStatus dayStatus) {
        this.day = day;
        this.date = date;
        this.totalCustomer = totalCustomer;
        this.grade = grade;
        this.dayStatus = dayStatus;
    }

    public void from(Object[] obj){
        if (obj!= null && obj.length ==3){
            this.totalCustomer = obj[1] != null ? ((BigInteger)obj[1]).intValue() : 0;
            this.grade = obj[2] != null ? (Double) obj[2] : 0;
        }

    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTotalCustomer() {
        return totalCustomer;
    }

    public void setTotalCustomer(int totalCustomer) {
        this.totalCustomer = totalCustomer;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public EDayStatus getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(EDayStatus dayStatus) {
        this.dayStatus = dayStatus;
    }
}
