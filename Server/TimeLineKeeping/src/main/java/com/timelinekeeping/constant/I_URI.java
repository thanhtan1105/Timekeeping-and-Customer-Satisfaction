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
    String API_ACCOUNT_UPDATE_TOKEN_ID_MOBILE = "/update_token_id_mobile";
    String API_ACCOUNT_LOGIN = "/login";
    String API_ACCOUNT_GET_REMINDER = "/get_reminder";

    /** Department*/
    String API_DEPARTMENT = "/api/department";
    String API_DEPARTMENT_TRAINING = "/training";
    String API_DEPARTMENT_EXIST = "/exist_code";

    /** Emotion**/
    String API_EMOTION = "/api/emotion";
    String API_EMOTION_RECOGNIZE = "/recognize_img";
    String API_EMOTION_ANALYZE = "/analyse_emotion";
    String API_EMOTION_GET_EMOTION = "/get_emotion";
    String API_EMOTION_UPLOAD_IMAGE = "/upload";
    String API_EMOTION_NEXT_TRANSACTION = "/next";
    String API_EMOTION_BEGIN_TRANSACTION = "/begin_transaction";
    String API_EMOTION_START_TRANSACTION = "/start_transaction";
    String API_EMOTION_PROCESS_TRANSACTION = "/process_transaction";
    String API_EMOTION_END_TRANSACTION = "/end_transaction";
    String API_EMOTION_REPORT = "/report";
    String API_EMOTION_REPORT_EMPLOYEE = "/report_employee";

    /** Reminder*/
    String API_REMINDER = "/api/reminder";
    String API_REMINDER_LIST_MANAGER = "/list_by_manager";

    /** Timekeeping*/
    String API_TIMEKEEPING = "/api/time";
    String API_TIMEKEEPING_LIST_EMPLOYEE = "/list_employee";
    String API_TIMEKEEPING_CHECK_IN_MANUAL = "/check_in_manual";
    String API_TIMEKEEPING_VIEW_TIMEKEEPING = "/get_time_keeping";
    String API_TIMEKEEPING_ATTENDANCE = "/get_attendance";

    /** To do list */
    String API_TODOLIST = "/api/todo_list";
    String API_TODOLIST_GET = "/get";
    String API_TODOLIST_SELECT_TASK = "/select_task";


    /** SESSION*/
    String SESSION_API_EMOTION_CUSTOMER_CODE = "customerCode";

    /** parameter*/
    String PARAMETER_EMOTION_ACCOUNT_ID = "accountId";
}
