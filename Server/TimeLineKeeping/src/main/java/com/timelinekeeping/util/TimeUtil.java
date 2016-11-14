
package com.timelinekeeping.util;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_TIME;
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

    public static Date parseToDate(String text, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(text);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }


    public static String timeToString(Date time, String pattern) {
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

    public static String correctTimeCheckin(String time) {
        Integer timeExcept = Integer.valueOf(IContanst.TIME_SYSTEM_EXCEPT);
        try {
            DateFormat format = new SimpleDateFormat(I_TIME.TIME_MINUTE);
            Calendar calendar = Calendar.getInstance();
            Date date = format.parse(time);
            calendar.setTime(date);
            calendar.add(Calendar.HOUR_OF_DAY, timeExcept * -1);
            System.out.println(calendar.getTime());


            //return
            DateFormat formatReturn = new SimpleDateFormat(I_TIME.TIME_FULL);
            return formatReturn.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date noiseDate(Date date, String pattern){
       return parseToDate(timeToString(date, pattern), pattern);
    }

//    public static boolean isPresentTimeCheckin(Date date) {
//        Date timeFromDate = parseToDate(IContanst.TIME_CHECK_IN_SYSTEM_START, I_TIME.TIME_MINUTE);
//        Date timeToDate = parseToDate(IContanst.TIME_CHECK_IN_SYSTEM_END, I_TIME.TIME_MINUTE);
//        if (timeFromDate == null || timeToDate == null) {
//            return true;
//        }
//
//        Date timeCheck = noiseDate(date, I_TIME.TIME_MINUTE);
//
//
//        if (timeFromDate.getTime() <= timeCheck.getTime() && timeCheck.getTime() <= timeToDate.getTime()) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static boolean isPresentTimeCheckin(Date date, String timeBegin, String timeEnd) {
        Date timeFromDate = parseToDate(timeBegin, I_TIME.TIME_MINUTE);
        Date timeToDate = parseToDate(timeEnd, I_TIME.TIME_MINUTE);
        if (timeFromDate == null || timeToDate == null) {
            return true;
        }

        Date timeCheck = noiseDate(date, I_TIME.TIME_MINUTE);


        if (timeFromDate.getTime() <= timeCheck.getTime() && timeCheck.getTime() <= timeToDate.getTime()) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            DateFormat format = new SimpleDateFormat(I_TIME.TIME_MINUTE);
            Date date = format.parse("16:31");
//            System.out.println(isPresentTimeCheckin(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
