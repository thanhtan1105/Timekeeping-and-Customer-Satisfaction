package com.timelinekeeping.util;

import com.timelinekeeping.constant.EDayOfWeek;
import com.timelinekeeping.constant.EDayStatus;
import com.timelinekeeping.entity.AccountEntity;
import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by lethanhtan on 9/8/16.
 */
public class ServiceUtils {

    public static String getDataResponse(HttpEntity entity) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(entity.getContent()));
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }

    public static EDayStatus convertDateType(AccountEntity accountEntity, Calendar calendar) {
        //set DayStatus
        if (accountEntity.getTimeCreate() != null && calendar.getTime().compareTo(accountEntity.getTimeCreate()) < 0) {
            return EDayStatus.DAY_BEFORE_CREATE;
        } else if (accountEntity.getTimeDeactive() != null && calendar.getTime().compareTo(accountEntity.getTimeDeactive()) > 0) {
            return EDayStatus.DAY_AFTER_DEACTIVE;
        } else if (calendar.getTime().compareTo(new Date()) > 0) {
            if (EDayOfWeek.checkDayOff(calendar.get(Calendar.DAY_OF_WEEK))) {
                return EDayStatus.DAY_FUTURE_OFF;
            } else {
                return EDayStatus.DAY_FUTURE_NORMAL;
            }
        } else {
            if (EDayOfWeek.checkDayOff(calendar.get(Calendar.DAY_OF_WEEK))) {
                return EDayStatus.DAY_OFF;
            } else {
                return EDayStatus.NORMAL;
            }
        }
    }

    // detemeter fromday, today
    public static int countWorkDay(int year, int month, Date timeCreate, Date timeDeactive) {

        if (timeCreate == null || (timeDeactive != null && timeCreate.compareTo(timeDeactive) > 0)) {
            return 0;
        }

        //init
        int dayOfMonth = YearMonth.of(year, month).lengthOfMonth();
        int fromday = 1, today = dayOfMonth;


        //start day
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, fromday);

        //if timeDeactive < fromday, return 0
        if (timeDeactive != null && calendar.getTime().compareTo(timeDeactive) > 0) {
            return 0;
        }

        //if formday < createtime,  fromday = createTime
        if (calendar.getTime().compareTo(timeCreate) < 0) {
            calendar.setTime(timeCreate);
            fromday = calendar.get(Calendar.DAY_OF_MONTH);
        }

        //today
        calendar.set(year, month - 1, dayOfMonth);

        //if today < createTime, return 0;
        if (calendar.getTime().compareTo(timeCreate) < 0) {
            return 0;
        }
        //if timeDeactive < today,  today = timeDeactive
        if (timeDeactive != null && calendar.getTime().compareTo(timeDeactive) > 0) {
            calendar.setTime(timeDeactive);
            today = calendar.get(Calendar.DAY_OF_MONTH);

            //if now() < today,  today = now()
        } else if (calendar.getTime().compareTo(new Date()) > 0) {
            calendar.setTime(new Date());
            today = calendar.get(Calendar.DAY_OF_MONTH);
        }


        return countWorkDay(year, month, fromday, today);

    }

    private static int countWorkDay(int year, int month, int fromday, int today) {
        if (fromday > today) {
            return 0;
        }
        int dayWork = 0;
        Calendar calendar = Calendar.getInstance();
        for (int i = fromday; i <= today; i++) {
            calendar.set(year, month - 1, i);
            EDayOfWeek dayOfWeek = EDayOfWeek.fromIndex(calendar.get(Calendar.DAY_OF_WEEK));
            if (dayOfWeek != EDayOfWeek.SATURDAY && dayOfWeek != EDayOfWeek.SUNDAY) {
                dayWork++;
            }
        }
        return dayWork;
    }

    public static String convertAgePredict(Double ageOfFace) {
        if (ageOfFace < 5) {
            return String.format("%s - %s", ((int) (ageOfFace - 0.5)) > 0 ? (int) (ageOfFace - 0.5) : 0, (int) Math.round(ageOfFace + 0.5));
        } else if (ageOfFace < 20) {
            return String.format("%s - %s", (int) (ageOfFace - 0.75), (int) Math.round(ageOfFace + 0.75));
        } else if (ageOfFace < 40) {
            return String.format("%s - %s", (int) (ageOfFace - 1), (int) Math.round(ageOfFace + 1));
        } else {
            return String.format("%s - %s", (int) (ageOfFace - 1.5), (int) Math.round(ageOfFace + 1.5));
        }
    }

    public static void main(String[] args) {
        System.out.println(convertAgePredict(0d));
    }
}
