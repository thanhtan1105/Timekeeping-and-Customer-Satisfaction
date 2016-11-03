package com.timelinekeeping.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HienTQSE60896 on 9/9/2016.
 */
public class TimeUtil {

    private static Logger logger = Logger.getLogger(TimeUtil.class);
    private static final String pattern = "yyyy-MM-dd  HH:mm";

    public static Date parseStringToDate(String text) {
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            logger.error(e);
        }
        return date;
    }

    public static Date parseToDate(String text, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(text);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    public static String timeToString(Date time) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(time);
    }

    public static void main(String[] args) {
        String text = "2016-11-08 09:30 AM";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date date = null;
        try {
            date = format.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("Date: " + date);
    }
}
