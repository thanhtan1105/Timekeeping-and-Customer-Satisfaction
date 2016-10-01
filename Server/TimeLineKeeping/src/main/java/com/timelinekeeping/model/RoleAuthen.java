package com.timelinekeeping.model;

import java.util.List;

/**
 * Created by HienTQSE60896 on 10/1/2016.
 */
public class RoleAuthen {

    private Long id;
    private String name;
    private List<String> allowPath;
    private String returnPage;

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

    public String getReturnPage() {
        return returnPage;
    }

    public void setReturnPage(String returnPage) {
        this.returnPage = returnPage;
    }
}
