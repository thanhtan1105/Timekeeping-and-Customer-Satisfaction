package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by TrungNN on 9/20/2016.
 */
public enum EEmotion {

    ANGER(0, "ANGER"),
    CONTEMPT(1, "CONTEMPT"),
    DISGUST(2, "DISGUST"),
    FEAR(3, "FEAR"),
    HAPPINESS(4, "HAPPINESS"),
    NEUTRAL(5, "NEUTRAL"),
    SADNESS(6, "SADNESS"),
    SURPRISE(7, "SURPRISE");

    private int index;
    private String name;

    EEmotion(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EEmotion fromIndex(int index) {
        for (EEmotion ee : values()) {
            if (ee.getIndex() == index) {
                return ee;
            }
        }
        return null;
    }

    public static EEmotion fromName(String name) {
        for (EEmotion ee : values()) {
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
