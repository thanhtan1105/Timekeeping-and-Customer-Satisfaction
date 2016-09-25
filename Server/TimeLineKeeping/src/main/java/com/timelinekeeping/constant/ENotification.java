package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by lethanhtan on 9/15/16.
 */
public enum ENotification {
    NOSEND(0, "NO_SEND"),
    SENDED(1, "SENDED");

    private int index;
    private String name;

    ENotification(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ENotification fromIndex(int index){
        for (ENotification ex : values()){
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
}
