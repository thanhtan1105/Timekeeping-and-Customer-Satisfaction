package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by HienTQSE60896 on 9/20/2016.
 */

@Entity
@Table(name = "notification", schema = "mydb")
public class NotificationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Basic
    @Column(name = "time_notify")
    private Timestamp timeNotify;

    @Basic
    @Column(name = "status")
    private EStatus status = EStatus.NOSEND;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity accountReceive;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reminder_message_id", referencedColumnName = "id", nullable = false)
    private ReminderMessageEntity reminderMessage;

    public NotificationEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReminderMessageEntity getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(ReminderMessageEntity reminderMessage) {
        this.reminderMessage = reminderMessage;
    }

    public Timestamp getTimeNotify() {
        return timeNotify;
    }

    public void setTimeNotify(Timestamp timeNotify) {
        this.timeNotify = timeNotify;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public AccountEntity getAccountReceive() {
        return accountReceive;
    }

    public void setAccountReceive(AccountEntity accountReceive) {
        this.accountReceive = accountReceive;
    }
}
