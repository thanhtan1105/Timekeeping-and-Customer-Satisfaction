package com.timelinekeeping.constant;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by HienTQSE60896 on 10/7/2016.
 */
public enum  ESuggestionSubject {

    ANH(0, "Anh ấy"),
    CHI(1, "Chị ấy"),
    EM(2, "Em ấy"),
    CHU(3, "Chú ấy"),
    CO(4, "Cô ấy"),
    BAC(4, "Bác ấy"),
    ONG(4, "Ông"),
    BA(4, "Bà");

    private int index;
    private String name;

    ESuggestionSubject(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static ESuggestionSubject fromIndex(int index){
        for (ESuggestionSubject ex : values()){
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
