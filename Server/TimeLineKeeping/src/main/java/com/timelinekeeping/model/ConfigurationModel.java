package com.timelinekeeping.model;

import com.timelinekeeping.constant.EConfiguration;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.util.ValidateUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 11/14/2016.
 */
public class ConfigurationModel {

    private Boolean sendSMS;

    private Double emotionAccept;

    private String emailCompany;

    private Double checkinConfident;

    private Double trainConfident;

    private String timeCheckinBegin;

    private String timeCheckinEnd;

    private Boolean emotionAdvance;

    private Double emotionAdvanceConfidence;

    private Double emotionAgeA;

    private Double emotionAgeB;

    private EmotionRecognizeScores boundScorce;

    public ConfigurationModel() {
    }

    public ConfigurationModel(Map<String, String> entity) {
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.SEND_SMS_KEY))) {
            this.sendSMS = Integer.valueOf(entity.get(IContanst.SEND_SMS_KEY)) == EConfiguration.ALLOW.getIndex();
        }
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_ACCEPTANCE_VALUE_KEY))) {
            this.emotionAccept = Double.valueOf(entity.get(IContanst.EMOTION_ACCEPTANCE_VALUE_KEY));
        }
        this.emailCompany = entity.get(IContanst.COMPANY_EMAIL_KEY);

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.CHECKIN_CONFIDENT_CORRECT_KEY))) {
            this.checkinConfident = Double.valueOf(entity.get(IContanst.CHECKIN_CONFIDENT_CORRECT_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.CHECKIN_CONFIDENT_TRAIN_KEY))) {
            this.trainConfident = Double.valueOf(entity.get(IContanst.CHECKIN_CONFIDENT_TRAIN_KEY));
        }
        this.timeCheckinBegin = entity.get(IContanst.TIME_CHECK_IN_SYSTEM_START_KEY);
        this.timeCheckinEnd = entity.get(IContanst.TIME_CHECK_IN_SYSTEM_END_KEY);

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_ADVANCE_KEY))) {
            this.emotionAdvance = Integer.valueOf(entity.get(IContanst.EMOTION_ADVANCE_KEY)) == EConfiguration.ALLOW.getIndex();
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_ADVANCE_CONFIDENCE_KEY))) {
            this.emotionAdvanceConfidence = Double.valueOf(entity.get(IContanst.EMOTION_ADVANCE_CONFIDENCE_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_AGE_A_KEY))) {
            this.emotionAgeA = Double.valueOf(entity.get(IContanst.EMOTION_MINING_AGE_A_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_AGE_B_KEY))) {
            this.emotionAgeB = Double.valueOf(entity.get(IContanst.EMOTION_MINING_AGE_B_KEY));
        }

        // emotion Scrope
        emotionEntries(entity);
    }

    private void emotionEntries(Map<String, String> entity) {
        Double anger = 0d, contempt = 0d, disgust = 0d, fear = 0d, happiness = 0d, neutral = 0d, sadness = 0d, surprise = 0d;
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_ANGER_KEY))) {
            anger = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_ANGER_KEY));
        }
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_CONTEMPT_KEY))) {
            contempt = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_CONTEMPT_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_DISGUST_KEY))) {
            disgust = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_DISGUST_KEY));
        }
        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_FEAR_KEY))) {
            fear = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_FEAR_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_HAPPINESS_KEY))) {
            happiness = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_HAPPINESS_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_NEUTRAL_KEY))) {
            neutral = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_NEUTRAL_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_SADNESS_KEY))) {
            sadness = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_SADNESS_KEY));
        }

        if (ValidateUtil.isNotEmpty(entity.get(IContanst.EMOTION_MINING_BOUND_SURPRISE_KEY))) {
            surprise = Double.valueOf(entity.get(IContanst.EMOTION_MINING_BOUND_SURPRISE_KEY));
        }

        boundScorce = new EmotionRecognizeScores(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise);
    }

    public Map<String, String> map() {
        Map<String, String> map = new HashMap<>();
        if (sendSMS != null)
            map.put(IContanst.SEND_SMS_KEY, String.valueOf(sendSMS ? EConfiguration.ALLOW.getIndex() : EConfiguration.DO_NOT.getIndex()));
        if (emotionAccept != null && emotionAccept > 0)
            map.put(IContanst.EMOTION_ACCEPTANCE_VALUE_KEY, String.valueOf(emotionAccept));
        if (emailCompany != null)
            map.put(IContanst.COMPANY_EMAIL_KEY, String.valueOf(emailCompany));
        if (checkinConfident != null && checkinConfident > 0)
            map.put(IContanst.CHECKIN_CONFIDENT_CORRECT_KEY, String.valueOf(checkinConfident));
        if (trainConfident != null && trainConfident > 0)
            map.put(IContanst.CHECKIN_CONFIDENT_TRAIN_KEY, String.valueOf(trainConfident));
        if (timeCheckinBegin != null)
            map.put(IContanst.TIME_CHECK_IN_SYSTEM_START_KEY, String.valueOf(timeCheckinBegin));
        if (timeCheckinEnd != null)
            map.put(IContanst.TIME_CHECK_IN_SYSTEM_END_KEY, String.valueOf(timeCheckinEnd));
        if (emotionAdvance != null)
            map.put(IContanst.EMOTION_ADVANCE_KEY, String.valueOf(emotionAdvance ? EConfiguration.ALLOW.getIndex() : EConfiguration.DO_NOT.getIndex()));
        if (emotionAdvanceConfidence != null)
            map.put(IContanst.EMOTION_ADVANCE_CONFIDENCE_KEY, String.valueOf(emotionAdvanceConfidence));

        // age
        if (emotionAgeA != null)
            map.put(IContanst.EMOTION_MINING_AGE_A_KEY, String.valueOf(emotionAgeA));
        if (emotionAgeB != null)
            map.put(IContanst.EMOTION_MINING_AGE_B_KEY, String.valueOf(emotionAgeB));

        // Emotion mining

        if (boundScorce != null && boundScorce.getAnger() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_ANGER_KEY, String.valueOf(boundScorce.getAnger()));
        }

        if (boundScorce != null && boundScorce.getContempt() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_CONTEMPT_KEY, String.valueOf(boundScorce.getContempt()));
        }

        if (boundScorce != null && boundScorce.getDisgust() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_DISGUST_KEY, String.valueOf(boundScorce.getDisgust()));
        }

        if (boundScorce != null && boundScorce.getFear() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_FEAR_KEY, String.valueOf(boundScorce.getFear()));
        }

        if (boundScorce != null && boundScorce.getHappiness() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_HAPPINESS_KEY, String.valueOf(boundScorce.getHappiness()));
        }
        if (boundScorce != null && boundScorce.getNeutral() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_NEUTRAL_KEY, String.valueOf(boundScorce.getNeutral()));
        }

        if (boundScorce != null && boundScorce.getSadness() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_SADNESS_KEY, String.valueOf(boundScorce.getSadness()));
        }

        if (boundScorce != null && boundScorce.getSurprise() != null){
            map.put(IContanst.EMOTION_MINING_BOUND_SURPRISE_KEY, String.valueOf(boundScorce.getSurprise()));
        }


        //return
        return map;
    }

    public Boolean getSendSMS() {
        return sendSMS;
    }

    public Double getEmotionAccept() {
        return emotionAccept;
    }

    public String getEmailCompany() {
        return emailCompany;
    }

    public Double getCheckinConfident() {
        return checkinConfident;
    }

    public Double getTrainConfident() {
        return trainConfident;
    }

    public String getTimeCheckinBegin() {
        return timeCheckinBegin;
    }

    public String getTimeCheckinEnd() {
        return timeCheckinEnd;
    }

    public Boolean getEmotionAdvance() {
        return emotionAdvance;
    }

    public Double getEmotionAdvanceConfidence() {
        return emotionAdvanceConfidence;
    }

    public Double getEmotionAgeA() {
        return emotionAgeA;
    }

    public Double getEmotionAgeB() {
        return emotionAgeB;
    }

    public void setSendSMS(Boolean sendSMS) {
        this.sendSMS = sendSMS;
    }

    public void setEmotionAccept(Double emotionAccept) {
        this.emotionAccept = emotionAccept;
    }

    public void setEmailCompany(String emailCompany) {
        this.emailCompany = emailCompany;
    }

    public void setCheckinConfident(Double checkinConfident) {
        this.checkinConfident = checkinConfident;
    }

    public void setTrainConfident(Double trainConfident) {
        this.trainConfident = trainConfident;
    }

    public void setTimeCheckinBegin(String timeCheckinBegin) {
        this.timeCheckinBegin = timeCheckinBegin;
    }

    public void setTimeCheckinEnd(String timeCheckinEnd) {
        this.timeCheckinEnd = timeCheckinEnd;
    }

    public void setEmotionAdvance(Boolean emotionAdvance) {
        this.emotionAdvance = emotionAdvance;
    }

    public void setEmotionAdvanceConfidence(Double emotionAdvanceConfidence) {
        this.emotionAdvanceConfidence = emotionAdvanceConfidence;
    }

    public void setEmotionAgeA(Double emotionAgeA) {
        this.emotionAgeA = emotionAgeA;
    }

    public void setEmotionAgeB(Double emotionAgeB) {
        this.emotionAgeB = emotionAgeB;
    }

    public EmotionRecognizeScores getBoundScorce() {
        return boundScorce;
    }

    public void setBoundScorce(EmotionRecognizeScores boundScorce) {
        this.boundScorce = boundScorce;
    }


}
