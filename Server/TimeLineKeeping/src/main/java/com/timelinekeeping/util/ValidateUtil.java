package com.timelinekeeping.util;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Created by TrungNN on 9/18/2016.
 */
public class ValidateUtil implements Serializable {

    public static Long parseNumber(String number) {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public static boolean isImageFile(InputStream data) {
        try {
            ImageIO.read(data).toString();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.size() <= 0;
    }

    public static boolean isEmpty(String text) {
        return text == null || text.length() <= 0;
    }

    public static boolean isNotEmpty(String text) {
        return text != null && text.length() > 0;
    }

    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

    public static boolean isResultAutocomplete(String text) {
        String pattern = "^(.*)-(.*)$";
        return text.matches(pattern);
    }

//    public static boolean isEnableEdit(Date currentTime, Date timeReminder, Long) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.MINUTE, 15);
//        currentTime.getTime();
//        return false;
//    }

    public static void main(String[] args) {
//        String text = "Đào Tạo - dt";
//        if (isResultAutocomplete(text)) {
//            System.out.println("Matching");
//            String[] groups = UtilApps.parseSearchValue(text);
//            System.out.println("Length: " + groups.length);
//            for (String group : groups) {
//                System.out.println("-" + group.trim() + ".");
//            }
//        } else {
//            System.out.println("Not matching");
//        }
        String currentT = "10-11-2016 10:00";
        String timeR = "10-11-2016 10:30";
        Date currentTime = TimeUtil.parseToDate(currentT, "dd-MM-yyyy hh:mm");
        Date timeReminder = TimeUtil.parseToDate(timeR, "dd-MM-yyyy hh:mm");
        String text = "17 September 2016 - 09:30 pm";
        DateFormat format = new SimpleDateFormat("dd MMMM yyyy - HH:mm a", Locale.ENGLISH);
        int minutes = 30;
        long mills = minutes * 60 * 1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentTime);
        System.out.println("date: " + calendar.DAY_OF_MONTH);
        System.out.println("month: " + calendar.MONTH);
        System.out.println("year: " + calendar.YEAR);
        System.out.println("hour: " + calendar.HOUR_OF_DAY);
        System.out.println("minute: " + calendar.MINUTE);
    }
}
