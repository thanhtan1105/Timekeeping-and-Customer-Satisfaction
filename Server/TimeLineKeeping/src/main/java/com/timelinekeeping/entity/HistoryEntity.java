package com.timelinekeeping.entity;

import com.timelinekeeping.constant.EHistory;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by HienTQSE60896 on 11/8/2016.
 */
@Entity
@Table(name = "history", schema = "mydb")
public class HistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "time", nullable = false)
    private Timestamp time = new Timestamp(new Date().getTime());

    @Basic
    @Column(name = "note", length = Integer.MAX_VALUE)
    private String note;

    @Basic
    @Column(name = "type", nullable = false)
    private EHistory type = EHistory.SYNCHRONIZED;

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

    public void setNote(String node) {
        this.note = node;
    }

    public EHistory getType() {
        return type;
    }

    public void setType(EHistory type) {
        this.type = type;
    }
}
