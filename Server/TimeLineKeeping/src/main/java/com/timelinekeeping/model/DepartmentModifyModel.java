package com.timelinekeeping.model;

/**
 * Created by HienTQSE60896 on 11/1/2016.
 */
public class DepartmentModifyModel {
    private Long id;
    private String code;
    private String name;
    private String description;



    public DepartmentModifyModel() {
    }

    public DepartmentModifyModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public DepartmentModifyModel(String code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
