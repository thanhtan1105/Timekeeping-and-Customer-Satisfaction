package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by lethanhtan on 9/15/16.
 */
public enum EDayStatus {

    NORMAL(0, "NORMAL"),
    DAY_BEFORE_CREATE(1, "DAY_BEFORE_CREATE"),
    DAY_AFTER_DEACTIVE(2, "DAY_AFTER_DEACTIVE"),
    DAY_OFF(3, "DAY_OFF");

    private int index;
    private String name;

    EDayStatus(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EDayStatus fromIndex(int index){
        for (EDayStatus ex : values()){
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
