package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/21/2016.
 */
public enum ETrainStatus {

    NOT_STARTED(0, "DEACTIVE"),
    RUNNING(1, "ACTIVE"),
    SUCCES(2, "NO_SEND"),
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
