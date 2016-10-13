package com.timelinekeeping.model;

import com.timelinekeeping.entity.RoleEntity;
import com.timelinekeeping.util.ValidateUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/17/2016.
 */
public class RoleAuthen extends RoleModel {
    private String redirect;
    private List<String> allows;


    public RoleAuthen() {
    }

    public RoleAuthen(RoleEntity entity) {
        super(entity);
        if (entity != null) {
            this.redirect = entity.getRedirect();
            this.setAllows(entity.getAllowPage());
        }
    }


    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }

    public List<String> getAllows() {
        return allows;
    }

    public void setAllows(List<String> allows) {
        this.allows = allows;
    }

    public void setAllows(String allow) {
        if (ValidateUtil.isNotEmpty(allow)) {
            String[] value = allow.split(";");
            if (value.length > 0) {
                this.allows = Arrays.asList(value);
            }

        }
    }


    @Override
    public String toString() {
        return "RoleAuthen{" +
                "redirect='" + redirect + '\'' +
                ", allows=" + allows +
                '}';
    }
}
