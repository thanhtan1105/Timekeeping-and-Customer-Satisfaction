package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/21/2016.
 */
public enum EConfiguration {

    DO_NOT(0, "DO_NOT"),
    ALLOW(1, "ALLOW");

    private int index;
    private String name;

    EConfiguration(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EConfiguration fromIndex(int index){
        for (EConfiguration ex : values()){
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
