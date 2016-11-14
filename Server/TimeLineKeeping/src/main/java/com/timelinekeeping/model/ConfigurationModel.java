package com.timelinekeeping.model;

import com.timelinekeeping.entity.ConfigurationEntity;

/**
 * Created by HienTQSE60896 on 11/14/2016.
 */
public class ConfigurationModel {

    private Long id;

    private String name;

    private String key;

    private String value;

    public ConfigurationModel() {
    }

    public ConfigurationModel(ConfigurationEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.key = entity.getKey();
            this.value = entity.getValue();
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
}
