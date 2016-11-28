package com.timelinekeeping.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by HienTQSE60896 on 11/14/2016.
 */
@Entity
@Table(name = "configuration")
public class ConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "key_query", nullable = false)
    private String key;

    @Column(name = "value", nullable = false)
    private String value;

    @Basic
    @Column(name = "time_modify", nullable = false)
    private Timestamp timeModify;


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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Timestamp getTimeModify() {
        return timeModify;
    }

    public void setTimeModify(Timestamp timeModify) {
        this.timeModify = timeModify;
    }
}
