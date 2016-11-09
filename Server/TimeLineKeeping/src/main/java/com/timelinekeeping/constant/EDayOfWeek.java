package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

import java.time.DayOfWeek;

/**
 * Created by lethanhtan on 9/15/16.
 */
public enum EDayOfWeek {

    SUNDAY(1, "SUNDAY"),
    MONDAY(2, "MONDAY"),
    TUESDAY(3, "TUESDAY"),
    WEDNESDAY(4, "WEDNESDAY"),
    THURSDAY(5, "THURSDAY"),
    FRIDAY(6, "FRIDAY"),
    SATURDAY(7, "SATURDAY");

    private int index;
    private String name;

    EDayOfWeek(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EDayOfWeek fromIndex(int index){
        for (EDayOfWeek ex : values()){
            if (ex.getIndex() == index){
                return ex;
            }
        }
        return null;
    }

    public static Boolean checkDayOff(int index){
        EDayOfWeek dayOfWeek = fromIndex(index);
        if (dayOfWeek == EDayOfWeek.SUNDAY || dayOfWeek == EDayOfWeek.SATURDAY){
            return true;
        }else{
            return false;
        }
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
