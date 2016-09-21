package com.timelinekeeping.model;

import com.timelinekeeping.entity.RoleEntity;

/**
 * Created by HienTQSE60896 on 9/17/2016.
 */
public class RoleModel {
    private Long id;
    private String name;

    public RoleModel() {
    }

    public RoleModel(RoleEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
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

    @Override
    public String toString() {
        return "RoleModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
