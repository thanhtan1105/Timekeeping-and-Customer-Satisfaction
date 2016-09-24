package com.timelinekeeping.model;

import com.timelinekeeping.constant.ETimeKeeping;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.TimeKeepingEntity;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
public class AccountCheckInModel {

    private Long id;
    private String username;
    private String fullname;
    private ETimeKeeping statusCheckin = ETimeKeeping.NO_CHECKIN;
    private Date timeCheckin; //if check successful has timeCheckin

    public AccountCheckInModel() {
    }

    public AccountCheckInModel(AccountEntity accountEntity, TimeKeepingEntity timeEntity) {
        if (accountEntity != null) {
            this.id = accountEntity.getId();
            this.username = accountEntity.getUsername();
            this.fullname = accountEntity.getFullname();
            if (timeEntity != null) {
                this.statusCheckin = timeEntity.getStatus();
                this.timeCheckin = timeEntity.getTimeCheck();
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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
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
}
