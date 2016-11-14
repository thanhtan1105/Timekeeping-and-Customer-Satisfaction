package com.timelinekeeping.model;

import com.timelinekeeping.constant.EDayStatus;
import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.ETypeCheckin;
import com.timelinekeeping.entity.TimeKeepingEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/29/2016.
 */
public class AttendanceDateModel {
    private int day;
    private Date date;
    private ETimeKeeping present = ETimeKeeping.ABSENT;
    private ETypeCheckin type;
    private Date timeCheck;
    private String note;
    private String imagePath;
    private EDayStatus dayStatus = EDayStatus.NORMAL;

    public AttendanceDateModel() {
    }

    public AttendanceDateModel(TimeKeepingEntity entity) {
        if (entity != null) {
            this.present = entity.getStatus();
            this.type = entity.getType();
            this.timeCheck = entity.getTimeCheck();
            this.note = entity.getNote();
            this.imagePath = entity.getImagePath();
        }
    }

    public void from(TimeKeepingEntity entity) {
        if (entity != null) {
            this.present = entity.getStatus();
            this.type = entity.getType();
            this.timeCheck = entity.getTimeCheck();
            this.note = entity.getNote();
            this.imagePath = entity.getImagePath();
        }
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

    public ETypeCheckin getType() {
        return type;
    }

    public void setType(ETypeCheckin type) {
        this.type = type;
    }

    public Date getTimeCheck() {
        return timeCheck;
    }

    public void setTimeCheck(Date timeCheck) {
        this.timeCheck = timeCheck;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
