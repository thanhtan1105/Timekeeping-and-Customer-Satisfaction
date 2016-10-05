package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by TrungNN on 9/20/2016.
 */
public enum ETransaction {

    BEGIN(0, "BEGIN"),
    BEGIN_MOBILE(1, "BEGIN_MOBILE"),
    PROCESS(2, "PROCESS"),
    END(3, "END"),
    ERROR(4, "ERROR"),
    PAUSE(5, "PAUSE"),
    STOP(6  , "STOP");

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
