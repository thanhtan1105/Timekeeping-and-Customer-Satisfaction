package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatusToDoTask;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by lethanhtan on 10/9/16.
 */
@Entity
@Table(name = "todo_list", schema = "mydb")
public class ToDoListEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @NotNull
    @Column(name = "title")
    private String title;

    @Basic
    @NotNull
    @Column(name = "complete")
    private EStatusToDoTask isComplete = EStatusToDoTask.INCOMPLETE;

    @Basic
    @NotNull
    @Column(name = "time_notify")
    private Timestamp timeNotify;


    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account_created;

    public ToDoListEntity() { }

    public ToDoListEntity(String title, EStatusToDoTask isComplete, Timestamp timeNotify, AccountEntity accountCreated) {
        this.title = title;
        this.isComplete = isComplete;
        this.timeNotify = timeNotify;
        this.account_created = accountCreated;
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

    public EStatusToDoTask getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(EStatusToDoTask isComplete) {
        this.isComplete = isComplete;
    }

    public Timestamp getTimeNotify() {
        return timeNotify;
    }

    public void setTimeNotify(Timestamp timeNotify) {
        this.timeNotify = timeNotify;
    }

    public AccountEntity getAccountCreated() {
        return account_created;
    }

    public void setAccountCreated(AccountEntity accountCreated) {
        this.account_created = accountCreated;
    }
}
