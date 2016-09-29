package com.timelinekeeping.model;

import com.timelinekeeping.constant.EDayStatus;
import com.timelinekeeping.constant.ETimeKeeping;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/29/2016.
 */
public class AttendanceDateModel {
    private int day;
    private Date date;
    private ETimeKeeping present;
    private EDayStatus dayStatus;

    public AttendanceDateModel() {
    }

    public AttendanceDateModel(int day, ETimeKeeping present, EDayStatus dayStatus) {
        this.day = day;
        this.present = present;
        this.dayStatus = dayStatus;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public ETimeKeeping getPresent() {
        return present;
    }

    public void setPresent(ETimeKeeping present) {
        this.present = present;
    }

    public EDayStatus getDayStatus() {
        return dayStatus;
    }

    public void setDayStatus(EDayStatus dayStatus) {
        this.dayStatus = dayStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
