package com.timelinekeeping._config;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 10/11/2016.
 */
public class EmotionSession {
    private static Map<String, String> map = new HashMap<>();


    private EmotionSession() {
    }

    public static String getValue(String key){
        return map.get(key);
    }

    public static void setValue(String key, String value){
        map.put(key, value);
    }

}
