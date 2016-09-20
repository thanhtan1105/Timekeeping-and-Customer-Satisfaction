package com.timelinekeeping.entity;

import com.timelinekeeping.constant.ETimeKeeping;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by lethanhtan on 9/14/16.
 */

@Entity
@Table(name = "time_keeping", schema = "mydb")
public class TimeKeepingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "time_check")
    private Timestamp timeCheck = new Timestamp(new Date().getTime());

    @Basic
    @Column(name = "status")
    private ETimeKeeping status = ETimeKeeping.LATE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account;


    public TimeKeepingEntity() { }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getTimeCheck() {
        return timeCheck;
    }

    public void setTimeCheck(Timestamp timeCheck) {
        this.timeCheck = timeCheck;
    }

    public ETimeKeeping getStatus() {
        return status;
    }

    public void setStatus(ETimeKeeping status) {
        this.status = status;
    }

    public AccountEntity getAccount() {
        return account;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }
}
