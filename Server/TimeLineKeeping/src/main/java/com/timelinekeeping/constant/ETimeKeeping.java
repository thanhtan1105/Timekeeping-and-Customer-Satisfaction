package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/18/2016.
 */
public enum  ETimeKeeping {

    PRESENT(0, "Present"),
    ABSENT(1, "Absent"),
    DEACTIVE(2, "Deactive");

    private int index;
    private String name;

    ETimeKeeping(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ETimeKeeping fromIndex(int index){
        for (ETimeKeeping ex : values()){
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
