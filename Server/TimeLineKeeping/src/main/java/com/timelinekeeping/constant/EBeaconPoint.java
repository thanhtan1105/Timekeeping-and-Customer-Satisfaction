package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public enum EBeaconPoint {
    CAN_MOVE(0, "CAN_MOVE"),
    ROOM(1, "ROOM"),
    ENTRANCE(2, "ENTRANCE"),
    STAIRS_UP(3, "STAIRS_UP"),
    STAIRS_DOWN(4, "STAIRS_DOWN"),
    EXIT(5, "EXIT"),
    CANNOT_MOVE(6, "CANNOT_MOVE"),
    OTHER(7, "OTHER"),
    BEACON(8, "Beacon");

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
