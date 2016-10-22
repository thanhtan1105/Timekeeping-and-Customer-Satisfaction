package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.model.ReminderModifyModel;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

/**
 * Created by HienTQSE60896 on 9/20/2016.
 */
@Entity
@Table(name = "reminder_message", schema = "mydb")
public class ReminderMessageEntity implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "message", length = Integer.MAX_VALUE)
    private String message;

    @Basic
    @Column(name = "time", nullable = false)
    private Timestamp time;

    @Basic
    @Column(name = "location", nullable = false)
    private String location;

    @Basic
    @Column(name = "create_date", nullable = false)
    private Timestamp createDate = new Timestamp(new Date().getTime());

    @Basic
    @Column(name = "active")
    private EStatus active = EStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private AccountEntity manager;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private CoordinateEntity room;

    @OneToMany(mappedBy = "reminderMessage", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<NotificationEntity> notificationSet;

    public ReminderMessageEntity() {
    }

    public ReminderMessageEntity(ReminderModifyModel modifyModel) {
        this.title = modifyModel.getTitle();
        this.message = modifyModel.getMessage();
        this.time = new Timestamp(modifyModel.getTime());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Set<NotificationEntity> getNotificationSet() {
        return notificationSet;
    }

    public void setNotificationSet(Set<NotificationEntity> notificationSet) {
        this.notificationSet = notificationSet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public AccountEntity getManager() {
        return manager;
    }

    public void setManager(AccountEntity account) {
        this.manager = account;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public CoordinateEntity getRoom() {
        return room;
    }

    public void setRoom(CoordinateEntity room) {
        this.room = room;
    }
}
