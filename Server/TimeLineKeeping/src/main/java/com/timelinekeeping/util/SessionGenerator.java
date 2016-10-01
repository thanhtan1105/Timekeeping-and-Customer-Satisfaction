package com.timelinekeeping.util;

import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/30/2016.
 */
public class SessionGenerator {

    //TODO generator
    public static String nextSession() {
        return new Date().getTime()+ "" + new Date().getTime();
    }
}
