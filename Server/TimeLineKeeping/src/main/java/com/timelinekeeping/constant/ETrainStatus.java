package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/21/2016.
 */
public enum ETrainStatus {

    NOT_STARTED(0, "notstarted"),
    RUNNING(1, "running"),
    SUCCEEDED(2, "succeeded"),
    FAILED(3, "failed");

    private int index;
    private String name;

    ETrainStatus(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ETrainStatus fromIndex(int index){
        for (ETrainStatus ex : values()){
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
