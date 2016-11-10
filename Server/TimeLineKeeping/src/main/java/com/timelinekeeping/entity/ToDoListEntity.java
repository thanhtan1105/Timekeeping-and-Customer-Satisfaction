package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.EStatusToDoTask;
import com.timelinekeeping.model.ToDoListModifyModel;
import com.timelinekeeping.util.ValidateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

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
    @Column(name = "title", nullable = false)
    private String title;

    @Basic
    @Column(name = "message")
    private String message;

    @Basic
    @Column(name = "complete", nullable = false)
    private EStatusToDoTask isComplete = EStatusToDoTask.INCOMPLETE;

    @Basic
    @Column(name = "time_notify", nullable = false)
    private Timestamp timeNotify;

    @Basic
    @Column(name = "time_create")
    private Timestamp timeCreate = new Timestamp(new Date().getTime());


    @Basic
    @Column(name = "active", nullable = false)
    private EStatus active = EStatus.ACTIVE;


    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity account_created;

    public ToDoListEntity() {
    }

    public ToDoListEntity(ToDoListModifyModel modifyModel) {
        if (modifyModel != null) {
            this.title = modifyModel.getTitle();
            this.message = modifyModel.getMessage();
            this.timeNotify = new Timestamp(modifyModel.getTimeNotify() * 1000);
        }
    }

    public void update(ToDoListModifyModel modifyModel) {
        if (modifyModel != null) {
            this.title = ValidateUtil.isNotEmpty(modifyModel.getTitle()) ? modifyModel.getTitle() : this.title;
            this.message = ValidateUtil.isNotEmpty(modifyModel.getMessage()) ? modifyModel.getMessage() : this.message;
            this.isComplete = modifyModel.getIsComplete() != null ? modifyModel.getIsComplete() : this.isComplete;
            this.timeNotify = new Timestamp(modifyModel.getTimeNotify());
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

    public Timestamp getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(Timestamp timeCreate) {
        this.timeCreate = timeCreate;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public AccountEntity getAccount_created() {
        return account_created;
    }

    public void setAccount_created(AccountEntity account_created) {
        this.account_created = account_created;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
