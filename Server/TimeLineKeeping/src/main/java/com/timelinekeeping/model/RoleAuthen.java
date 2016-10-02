package com.timelinekeeping.model;

import com.timelinekeeping.entity.RoleEntity;

import java.util.List;

/**
 * Created by HienTQSE60896 on 10/1/2016.
 */
public class RoleAuthen {

    private Long id;
    private String name;
    private List<String> allowPath;
    private String authenPage;

    public RoleAuthen() {
    }

    public RoleAuthen(RoleEntity entity) {
        if (entity != null) {
            this.id = entity.getId();
            this.name = entity.getName();
            this.allowPath = allowPath;
            this.authenPage = entity.getAuthenPage();
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

    public List<String> getAllowPath() {
        return allowPath;
    }

    public void setAllowPath(List<String> allowPath) {
        this.allowPath = allowPath;
    }

    public String getAuthenPage() {
        return authenPage;
    }

    public void setAuthenPage(String authenPage) {
        this.authenPage = authenPage;
    }
}
