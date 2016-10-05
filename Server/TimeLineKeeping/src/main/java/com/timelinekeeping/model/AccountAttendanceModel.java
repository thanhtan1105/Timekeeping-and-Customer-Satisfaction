package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;

import java.util.Date;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/29/2016.
 */
public class AccountAttendanceModel {
    private Long id;
    private String username;
    private String fullname;
    private EStatus active;
    private Date dateDeactive;
    private String timeSystem = IContanst.TIME_CHECK_IN_SYSTEM; // time to compare employee absent or present
    private Integer year;
    private Integer month;

    private Integer totalTimeKeeping;
    private Integer dayWork = 0;

    private List<AttendanceDateModel> attendances;

    public AccountAttendanceModel() {
    }

    public AccountAttendanceModel(AccountEntity entity, int year, int month) {
        if (entity != null) {
            this.id = entity.getId();
            this.username = entity.getUsername();
            this.fullname = entity.getFullname();
            this.active = entity.getActive();
            this.dateDeactive = entity.getTimeDeactive();
        }
        this.year = year;
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public Date getDateDeactive() {
        return dateDeactive;
    }

    public void setDateDeactive(Date dateDeactive) {
        this.dateDeactive = dateDeactive;
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

    public List<AttendanceDateModel> getAttendances() {
        return attendances;
    }

    public Integer getTotalTimeKeeping() {
        return totalTimeKeeping;
    }

    public void setTotalTimeKeeping(Integer totalTimeKeeping) {
        this.totalTimeKeeping = totalTimeKeeping;
    }

    public void setAttendances(List<AttendanceDateModel> attendances) {
        this.attendances = attendances;
    }

    public Integer getDayWork() {
        return dayWork;
    }

    public void setDayWork(Integer dayWork) {
        this.dayWork = dayWork;
    }

    public String getTimeSystem() {
        return timeSystem;
    }

    public void setTimeSystem(String timeSystem) {
        this.timeSystem = timeSystem;
    }
}
