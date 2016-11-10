
package com.timelinekeeping.util;

import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
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


    public static YearMonth parseYearMonth(Long time) {
        return parseYearMonth(new Date(time));
    }

    public static YearMonth parseYearMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return YearMonth.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
    }

    public static Long correctMilisecord(Long time) {
        if (String.valueOf(new Date().getTime()).length() > String.valueOf(time).length()) {
            return time * 1000;
        } else {
            return time;
        }
    }

}
