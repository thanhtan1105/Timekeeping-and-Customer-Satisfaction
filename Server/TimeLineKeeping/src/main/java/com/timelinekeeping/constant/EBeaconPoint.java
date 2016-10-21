package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public enum EBeaconPoint {
    CAN_MOVE(0, "CAN_MOVE"),
    ROOM(1, "ROOM"),
    ENTRANCE(2, "ENTRANCE"),
    STAIRS(3, "STAIRS"),
    EXIT(4, "EXIT"),
    CANNOT_MOVE(5, "CANNOT_MOVE"),
    OTHER(6, "OTHER");

    private int index;
    private String name;

    EBeaconPoint(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EBeaconPoint fromIndex(int index){
        for (EBeaconPoint ex : values()){
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
