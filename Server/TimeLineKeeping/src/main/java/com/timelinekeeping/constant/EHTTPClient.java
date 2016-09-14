package com.timelinekeeping.constant;

import com.timelinekeeping.util.HTTPClientUtil;

/**
 * Created by HienTQSE60896 on 9/13/2016.
 */
public enum  EHTTPClient {
    KEY_FACE(0, "KEY_FACE"),
    KEY_EMOTION(1, "KEY_EMOTION");

    private int index;
    private String name;

    EHTTPClient(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public static EHTTPClient fromIndex(int index){
        for (EHTTPClient ex : values()){
            if (ex.getIndex() == index){
                return ex;
            }
        }
        return null;
    }

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
