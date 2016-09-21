package com.timelinekeeping.model;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.ETrainStatus;
import com.timelinekeeping.entity.DepartmentEntity;

/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public class DepartmentModel {
    private Long id;
    private String code;
    private String name;
    private String description;
    private EStatus active;
    private ETrainStatus status;

    public DepartmentModel() {
    }

    public DepartmentModel(DepartmentEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.code = entity.getCode();
            this.name = entity.getName();
            this.description = entity.getDescription();
            this.active = entity.getActive();
            this.status = entity.getStatus();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EStatus getActive() {
        return active;
    }

    public void setActive(EStatus active) {
        this.active = active;
    }

    public ETrainStatus getStatus() {
        return status;
    }

    public void setStatus(ETrainStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "DepartmentModel{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
