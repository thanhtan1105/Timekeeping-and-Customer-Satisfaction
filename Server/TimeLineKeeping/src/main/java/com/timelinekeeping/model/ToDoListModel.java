package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatusToDoTask;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.ToDoListEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lethanhtan on 10/9/16.
 */
public class ToDoListModel {

    private Long id;
    private String title;
    private EStatusToDoTask isComplete;
    private Timestamp timeNotify;
    private AccountModel accountCreated;


    public ToDoListModel(ToDoListEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.isComplete = entity.getIsComplete();
            this.timeNotify = entity.getTimeNotify();
            this.accountCreated = new AccountModel(entity.getAccountCreated());
        }
    }

    public static List<ToDoListModel> convertToModel (List<ToDoListEntity> list) {
        List<ToDoListModel> toDoListModels = new ArrayList<>();
        for (ToDoListEntity item : list) {
            if (item != null) {
                toDoListModels.add(new ToDoListModel(item));
            }
        }
        return toDoListModels;
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

    public AccountModel getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(AccountModel accountCreated) {
        this.accountCreated = accountCreated;
    }
}
