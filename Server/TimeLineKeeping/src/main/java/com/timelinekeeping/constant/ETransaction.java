package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by TrungNN on 9/20/2016.
 */
public enum ETransaction {

    BEGIN(0, "BEGIN"),
    PROCESS(1, "PROCESS"),
    END(2, "END"),
    ERROR(3, "ERROR"),
    PAUSE(4, "PAUSE"),
    STOP(5, "STOP");

    private int index;
    private String name;

    ETransaction(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ETransaction fromIndex(int index) {
        for (ETransaction ee : values()) {
            if (ee.getIndex() == index) {
                return ee;
            }
        }
        return null;
    }

    public static ETransaction fromName(String name) {
        for (ETransaction ee : values()) {
            if (ee.getName().equals(name.toUpperCase())) {
                return ee;
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
