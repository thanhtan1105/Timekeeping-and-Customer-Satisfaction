package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by TrungNN on 9/20/2016.
 */
public enum EEmotion {

    /*ANGER(0, 0d, "ANGER"),
    CONTEMPT(1, 2.5d, "CONTEMPT"),
    DISGUST(2, 2.5d, "DISGUST"),
    FEAR(3, 4d, "FEAR"),
    HAPPINESS(4, 10d, "HAPPINESS"),
    NEUTRAL(5, 5d, "NEUTRAL"),
    SADNESS(6, 2.5d, "SADNESS"),
    SURPRISE(7, 7.5d,"SURPRISE");*/

    ANGER(0, -5d, "giận dữ"),
    CONTEMPT(1, -4d, "kinh thường"),
    DISGUST(2, -2.5d, "căm phẫn"),
    FEAR(3, -1d, "sợ hãi"),
    NEUTRAL(4, 2.5, "bình thường"),
    SADNESS(5, -2.5d, "buồn"),
    SURPRISE(6, 3d,"ngạc nhiên"),
    HAPPINESS(7, 5d, "vui vẻ"),
    NONE(8, 0d, "");

    private int index;
    private double grade;
    private String name;

    EEmotion(int index, double grade, String name) {
        this.index = index;
        this.grade = grade;
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

    public double getGrade() {
        return grade;
    }

    @JsonValue
    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
