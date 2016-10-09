package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by lethanhtan on 10/9/16.
 */
public enum EStatusToDoTask {

    INCOMPLETE(0, "INCOMPLETE"),
    COMPLETE(1, "COMPLETE");

    private int index;
    private String name;

    EStatusToDoTask(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EStatusToDoTask fromIndex(int index){
        for (EStatusToDoTask ex : values()){
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
