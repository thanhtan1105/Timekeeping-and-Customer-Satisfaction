package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/21/2016.
 */
public enum ETypeCheckin {

    CHECKIN_CAMERA(0, "Checkin Camera"),
    CHECKIN_MANUAL(1, "Checkin Manual");

    private int index;
    private String name;

    ETypeCheckin(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ETypeCheckin fromIndex(int index){
        for (ETypeCheckin ex : values()){
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
