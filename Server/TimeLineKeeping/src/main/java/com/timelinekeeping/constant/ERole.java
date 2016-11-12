package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;
import com.timelinekeeping.model.RoleAuthen;

/**
 * Created by lethanhtan on 9/15/16.
 */
public enum ERole {

    ADMIN(0, "admin"),
    MANAGER(1, "manager"),
    EMPLOYEE(2, "employee");

    private int index;
    private String name;
    private RoleAuthen role;
    ERole(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ERole fromIndex(int index){
        for (ERole ex : values()) {
            if (ex.getIndex() == index){
                return ex;
            }
        }
        return null;
    }

    @JsonValue
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoleAuthen getRole() {
        return role;
    }

    public void setRole(RoleAuthen role) {
        this.role = role;
    }
}
