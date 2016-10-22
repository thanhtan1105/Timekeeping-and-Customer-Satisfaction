package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.entity.ReminderMessageEntity;
import com.timelinekeeping.util.UtilApps;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public class ReminderModel {

    private Long id;
    private String title;
    private String message;
    private Date time;
    private Date createTime;
    private String location;
    private EStatus active;
    private AccountNotificationModel manager;
    private CoordinateModel room;
    private List<AccountNotificationModel> listEmployee;

    public ReminderModel() {
    }

    public ReminderModel(ReminderMessageEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.message = entity.getMessage();
            this.time = entity.getTime();
            this.createTime = entity.getCreateDate();
            this.active = entity.getActive();
            this.manager = new AccountNotificationModel(entity.getManager());
            if (entity.getNotificationSet() != null) {
                this.listEmployee = entity.getNotificationSet().stream().map(UtilApps::getAccountFromNotify).collect(Collectors.toList());
            }
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "ReminderModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", time=" + time +
                ", createTime=" + createTime +
                ", active=" + active +
                '}';
    }

    public AccountNotificationModel getManager() {
        return manager;
    }

    public void setManager(AccountNotificationModel manager) {
        this.manager = manager;
    }

    public List<AccountNotificationModel> getListEmployee() {
        return listEmployee;
    }

    public void setListEmployee(List<AccountNotificationModel> listEmployee) {
        this.listEmployee = listEmployee;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CoordinateModel getRoom() {
        return room;
    }

    public void setRoom(CoordinateModel room) {
        this.room = room;
    }
}
