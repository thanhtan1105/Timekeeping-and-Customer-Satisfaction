package com.timelinekeeping.constant;

import com.timelinekeeping._config.AppConfigKeys;

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

    String API_COGN_MICROSOFT_PER_GROUP_FORMAT_TIME = "MM/DD/YYYY h:mm:ss a";

    int HTTP_CLIENT_KEY_FACE = 0;
    int HTTP_CLIENT_KEY_EMOTION = 1;

    String EXTENSION_FILE_IMAGE = "jpg";
    int PAGE_PAGE_I = 0;
    int PAGE_SIZE_I = 10;
    String PAGE_PAGE = 0 + "";
    String PAGE_SIZE = 10 + "";
    double MCS_PERSON_DETECT_CONFIDINCE_CORRECT = AppConfigKeys.getInstance().getApiPropertyDouble("detect.person.indetify.confidence");
}
