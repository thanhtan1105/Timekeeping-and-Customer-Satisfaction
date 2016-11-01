package com.timelinekeeping._config;


import com.timelinekeeping.common.Pair;
import com.timelinekeeping.model.EmotionSessionStoreCustomer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 10/11/2016.
 */
public class EmotionSession implements Serializable{
    private static Map<String, EmotionSessionStoreCustomer> map = new HashMap<>();


    private EmotionSession() {
    }

    public static EmotionSessionStoreCustomer getValue(String key){
        System.out.println("Key: -->" + key);
        System.out.println("Value: -->" + map.get(key));
        return map.get(key);

    }

    public static void remove(String key){
        map.remove(key);
    }
    public static void setValue(String key, EmotionSessionStoreCustomer value){
        map.put(key, value);
    }

    public static void clean(){
        map = new HashMap<>();
    }
}
