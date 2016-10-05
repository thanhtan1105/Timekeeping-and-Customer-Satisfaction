package com.timelinekeeping.constant;

/**
 * Created by TrungNN on 9/22/2016.
 */
public interface IViewConst {

    /**
     * Views for manager
     */
    String MANAGEMENT_REMINDER_VIEW = "/views/manager/management_reminder/management_reminder";
    String ADD_REMINDER_VIEW = "/views/manager/management_reminder/add_reminder";
    String CHECK_IN_MANUAL_VIEW = "/views/manager/checkin_manual/checkin_manual";
    String TIME_KEEPING_VIEW = "/views/manager/timekeeping/timekeeping";
    String TIME_KEEPING_DETAILS_VIEW = "/views/manager/timekeeping/timekeeping_details";
    String CUSTOMER_SATISFACTION_MONTH_VIEW = "/views/manager/customer_satisfaction/cus_satisfaction_month";
    String CUSTOMER_SATISFACTION_DATE_VIEW = "/views/manager/customer_satisfaction/cus_satisfaction_date";
    String CUSTOMER_SATISFACTION_MONTH_DETAILS_VIEW = "/views/manager/customer_satisfaction/cus_satisfaction_month_details";

    /**
     * Views for employee
     */
    String ATTENDANCE_VIEW = "/views/employee/attendance/attendance";
    String CUSTOMER_EMOTION_VIEW = "/views/employee/customer_emotion/customer_emotion";

    /**
     * Views for admin
     */
    String MANAGEMENT_DEPARTMENT_VIEW = "/views/admin/management_depart/management_depart";

    /**
     * Views for login
     */
    String LOGIN_VIEW = "/views/login/login";
    String INVALID_VIEW = "views/login/invalid";
}

