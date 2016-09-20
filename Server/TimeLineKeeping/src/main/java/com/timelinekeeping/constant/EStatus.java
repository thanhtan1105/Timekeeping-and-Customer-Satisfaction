package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by lethanhtan on 9/15/16.
 */
public enum EStatus {

    DEACTIVE(0, "DEACTIVE"),
    ACTIVE(1, "ACTIVE"),
    NOSEND(2, "NO_SEND"),
    SENDED(3, "NO_SEND");

    private int index;
    private String name;

    EStatus(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EStatus fromIndex(int index){
        for (EStatus ex : values()){
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
