package com.timelinekeeping.constant;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.common.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/8/2016.
 */
public interface IContanst {
    String BEGIN_METHOD = "---begin---";
    String END_METHOD = "----end----";

    String BEGIN_METHOD_SERVICE = "--- Begin Service : ";
    String END_METHOD_SERVICE = "---- End Service ----";

    String BEGIN_METHOD_MCS = "--- Begin MCS API : ";
    String END_METHOD_MCS = "---- End MCS API ----";


    String BEGIN_METHOD_CONTROLLER = "--- Begin Controller : ";
    String END_METHOD_CONTROLLER = "---- End Controller ----";
    String LOGGER_ERROR = "--- ERROR";

    String ERROR_LOGGER = "-- ERROR: ";



    int HTTP_CLIENT_KEY_FACE = 0;
    int HTTP_CLIENT_KEY_EMOTION = 1;

    String EXTENSION_FILE_IMAGE = "jpg";
    int PAGE_PAGE_I = 0;
    int PAGE_SIZE_I = 10;
    int PAGE_SIZE_CONTENT = 10;
    int PAGE_SIZE_HISTORY = 15;
    int PAGE_SIZE_AUTO_COMPLETE = 1000;
    String PAGE_PAGE = 0 + "";
    String PAGE_SIZE = 10 + "";
    String DEFAULT_INT = -1 + "";
    double MCS_PERSON_DETECT_CONFIDINCE_CORRECT = AppConfigKeys.getInstance().getApiPropertyDouble("detect.person.indetify.confidence");

    int AGE_AMOUNT = 2;
    int SEND_SMS = 0;


    /**
     * Side-bar
     */
    String SIDE_BAR_ADMIN_MANAGEMENT_DEPART = "ManagementDepart";
    String SIDE_BAR_ADMIN_MANAGEMENT_ACC = "ManagementAcc";
    String SIDE_BAR_ADMIN_SYNC_DATA = "SyncData";
    String SIDE_BAR_MANAGER_CHECK_IN = "Checkin";
    String SIDE_BAR_MANAGER_TIMEKEEPING = "Timekeeping";
    String SIDE_BAR_MANAGER_MANAGEMENT_REMINDER = "ManagementReminder";
    String SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION = "CustomerSatisfaction";
    String SIDE_BAR_EMPLOYEE_ATTENDANCE = "Attendance";
    String SIDE_BAR_EMPLOYEE_CUSTOMER_EMOTION = "CustomerEmotion";


    /** CHECKIN*/
    String TIME_CHECK_IN_SYSTEM_START = AppConfigKeys.getInstance().getApiPropertyValue("checkin.time.system.start");
    String TIME_CHECK_IN_SYSTEM_END = AppConfigKeys.getInstance().getApiPropertyValue("checkin.time.system.end");

    // emotion minimun
    double EXCEPTION_VALUE = 0.15;

    /** suggestion model**/
    String QUANLITY_EMOTION_DEFAULT = "một ít";

    String SUGGESTION_1_EMOTION = "%s có vẻ %s.";
    String SUGGESTION_2_EMOTION = "%s có vẻ %s và %s.";
    String SUGGESTION_3_EMOTION = "%s có vẻ %s, %s và %s.";
    String SUGGESTION_BOTH_1_1_EMOTION = "%s có vẻ %s nhưng có %s.";
    String SUGGESTION_BOTH_2_1_EMOTION = "%s có vẻ %s và %s nhưng có %s.";
    String SUGGESTION_BOTH_1_2_EMOTION = "%s có vẻ %s nhưng có %s và %s.";

    /** Message Competition*/
    List<Pair<EEmotion, EEmotion>> COMPETITION_EMOTION =
            Arrays.asList(new Pair<>(EEmotion.ANGER, EEmotion.HAPPINESS),
                          new Pair<>(EEmotion.NEUTRAL, EEmotion.HAPPINESS),
                    new Pair<>(EEmotion.NEUTRAL, EEmotion.CONTEMPT),
                    new Pair<>(EEmotion.NEUTRAL, EEmotion.CONTEMPT));

    String COMPANY_EMAIL = "tkcs.vn";
    String PASSWORD_DEFAULT = "abcd@123";


    String DEPARTMENT_MICROSOFT = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.department");
}
