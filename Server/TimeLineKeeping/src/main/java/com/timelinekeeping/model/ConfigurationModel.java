package com.timelinekeeping.model;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.util.ValidateUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 11/14/2016.
 */
public class ConfigurationModel {

    private Boolean sendSMS;

    private Double emotionAccept;

    private String emailCompay;

    private Double checkinConfident;

    private String timeCheckinBegin;

    private String timeCheckinEnd;

    public ConfigurationModel() {
    }

    public ConfigurationModel(Map<String, String> entity) {
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.SEND_SMS_KEY))) {
            this.sendSMS = Integer.valueOf(entity.get(IContanst.SEND_SMS_KEY)) == 1;
        }
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_ACEPTION_VALUE_KEY))) {
            this.emotionAccept = Double.valueOf(entity.get(IContanst.EMOTION_ACEPTION_VALUE_KEY));
        }
        this.emailCompay = entity.get(IContanst.COMPANY_EMAIL_KEY);

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.CHECKIN_CONFIDINCE_CORRECT_KEY))) {
            this.checkinConfident = Double.valueOf(entity.get(IContanst.CHECKIN_CONFIDINCE_CORRECT_KEY));
        }
        this.timeCheckinBegin = entity.get(IContanst.TIME_CHECK_IN_SYSTEM_START_KEY);
        this.timeCheckinEnd = entity.get(IContanst.TIME_CHECK_IN_SYSTEM_END_KEY);
    }

    public Map<String, String> map(){
        Map<String, String> map = new HashMap<>();
        map.put(IContanst.SEND_SMS_KEY, String.valueOf(sendSMS));
        map.put(IContanst.EMOTION_ACEPTION_VALUE_KEY, String.valueOf(emotionAccept));
        map.put(IContanst.COMPANY_EMAIL_KEY, String.valueOf(emailCompay));
        map.put(IContanst.CHECKIN_CONFIDINCE_CORRECT_KEY, String.valueOf(checkinConfident));
        map.put(IContanst.CHECKIN_CONFIDINCE_CORRECT_KEY, String.valueOf(timeCheckinBegin));
        map.put(IContanst.TIME_CHECK_IN_SYSTEM_END_KEY, String.valueOf(timeCheckinEnd));
        return map;
    }

    public Boolean getSendSMS() {
        return sendSMS;
    }

    public void setSendSMS(Boolean sendSMS) {
        this.sendSMS = sendSMS;
    }

    public Double getEmotionAccept() {
        return emotionAccept;
    }

    public void setEmotionAccept(Double emotionAccept) {
        this.emotionAccept = emotionAccept;
    }

    public String getEmailCompay() {
        return emailCompay;
    }

    public void setEmailCompay(String emailCompay) {
        this.emailCompay = emailCompay;
    }

    public Double getCheckinConfident() {
        return checkinConfident;
    }

    public void setCheckinConfident(Double checkinConfident) {
        this.checkinConfident = checkinConfident;
    }

    public String getTimeCheckinBegin() {
        return timeCheckinBegin;
    }

    public void setTimeCheckinBegin(String timeCheckinBegin) {
        this.timeCheckinBegin = timeCheckinBegin;
    }

    public String getTimeCheckinEnd() {
        return timeCheckinEnd;
    }

    public void setTimeCheckinEnd(String timeCheckinEnd) {
        this.timeCheckinEnd = timeCheckinEnd;
    }
}
