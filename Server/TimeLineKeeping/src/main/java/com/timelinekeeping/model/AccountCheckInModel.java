package com.timelinekeeping.model;

import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.constant.ETypeCheckin;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
public class AccountCheckInModel {

    private Long id;
    private String username;
    private String fullName;
    private ETypeCheckin typeCheckin;
    private ETimeKeeping statusCheckin = ETimeKeeping.ABSENT;
    private Date timeCheckin; //if check successful has timeCheckin
    private Date currentDate = new Date();
    private String note;

    public AccountCheckInModel() {
    }

    public AccountCheckInModel(AccountEntity accountEntity, TimeKeepingEntity timeEntity) {
        if (accountEntity != null) {
            this.id = accountEntity.getId();
            this.username = accountEntity.getUsername();
            this.fullName = accountEntity.getFullName();
            if (timeEntity != null) {
                this.statusCheckin = timeEntity.getStatus();
                this.timeCheckin = timeEntity.getTimeCheck();
                this.typeCheckin = timeEntity.getType();
                this.note = timeEntity.getNote();
            }
        }
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public ETimeKeeping getStatusCheckin() {
        return statusCheckin;
    }

    public void setStatusCheckin(ETimeKeeping statusCheckin) {
        this.statusCheckin = statusCheckin;
    }

    public Date getTimeCheckin() {
        return timeCheckin;
    }

    public void setTimeCheckin(Date timeCheckin) {
        this.timeCheckin = timeCheckin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public ETypeCheckin getTypeCheckin() {
        return typeCheckin;
    }

    public void setTypeCheckin(ETypeCheckin typeCheckin) {
        this.typeCheckin = typeCheckin;
    }
}
