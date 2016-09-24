package com.timelinekeeping.constant;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
public interface I_URI {

    /** COMMON*/
    String API_CREATE = "/create";
    String API_LIST = "/list";
    String API_SEARCH = "/search";
    String API_UPDATE = "/update";
    String API_ACTIVE = "/active";


    /*** CONTROLLER*/
    /** Account*/
    String API_ACCOUNT = "/api/account";
    String API_ACCOUNT_ADD_FACE = "/add_face";
    String API_ACCOUNT_CHECK_IN = "/check_in";
    String API_ACCOUNT_SEARCH_DEPARTMENT = "/search_department";


    /** Department*/
    String API_DEPARTMENT = "/api/department";
    String API_DEPARTMENT_TRAINING = "/training";
    String API_DEPARTMENT_EXIST = "/exist_code";

    /** Emotion**/
    String API_EMOTION = "/api/emotion";
    String API_EMOTION_RECOGNIZE = "/recognize_img";
    String API_EMOTION_ANALYZE = "/analyse_emotion";
    String API_EMOTION_GET_EMOTION = "/get_customer_emotion";


    /** Reminder*/
    String API_REMINDER = "/api/reminder";
    String API_REMINDER_LIST_MANAGER = "/list_by_manager";

    /** Timekeeping*/
    String API_TIMEKEEPING = "/api/time";
    String API_TIMEKEEPING_LIST_EMPLOYEE = "/list_employee";

}
