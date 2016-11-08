package com.timelinekeeping.model;

import com.timelinekeeping.constant.EHistory;
import com.timelinekeeping.entity.HistoryEntity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by HienTQSE60896 on 11/8/2016.
 */
public class HistoryModel {

    private Long id;

    private String name;

    private Timestamp time = new Timestamp(new Date().getTime());

    private String note;

    private EHistory type;

    public HistoryModel() {
    }

    public HistoryModel(HistoryEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.time = entity.getTime();
            this.note = entity.getNode();
            this.type = entity.getType();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public EHistory getType() {
        return type;
    }

    public void setType(EHistory type) {
        this.type = type;
    }
}
