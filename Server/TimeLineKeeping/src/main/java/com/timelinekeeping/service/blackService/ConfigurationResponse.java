package com.timelinekeeping.service.blackService;

import com.timelinekeeping.constant.EConfiguration;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.ConfigurationEntity;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.repository.ConfigurationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by HienTQSE60896 on 11/28/2016.
 */
@Service
@Component
public class ConfigurationResponse {

    @Autowired
    private ConfigurationRepo configurationRepo;

    public Boolean getSendSMS() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.SEND_SMS_KEY);
        return entity != null ? Integer.valueOf(entity.getValue()) == EConfiguration.ALLOW.getIndex() : false;
    }

    public Double getEmotionAccept() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_ACCEPTANCE_VALUE_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public String getEmailCompany() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.COMPANY_EMAIL_KEY);
        return entity != null ? String.valueOf(entity.getValue()) : null;
    }

    public Double getCheckinConfident() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.CHECKIN_CONFIDENT_CORRECT_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public Double getTrainConfident() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.CHECKIN_CONFIDENT_TRAIN_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public String getTimeCheckinBegin() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.TIME_CHECK_IN_SYSTEM_START_KEY);
        return entity != null ? String.valueOf(entity.getValue()) : null;
    }

    public String getTimeCheckinEnd() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.TIME_CHECK_IN_SYSTEM_END_KEY);
        return entity != null ? String.valueOf(entity.getValue()) : null;
    }

    public Boolean getEmotionAdvance() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_ADVANCE_KEY);
        return entity != null ? Integer.valueOf(entity.getValue()) == EConfiguration.ALLOW.getIndex() : false;
    }

    public Double getEmotionAdvanceConfidence() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_ADVANCE_CONFIDENCE_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public Double getEmotionAgeA() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_MINING_AGE_A_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public Double getEmotionAgeB() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_MINING_AGE_B_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public EmotionRecognizeScores getScores() {
        Double anger = 0d, contempt = 0d, disgust = 0d, fear = 0d,
                happiness = 0d, neutral = 0d, sadness = 0d, surprise = 0d;

        /* get emotion*/
        ConfigurationEntity entityAger = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_ANGER_KEY);
        ConfigurationEntity entityContempt = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_CONTEMPT_KEY);
        ConfigurationEntity entityDisgust = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_DISGUST_KEY);
        ConfigurationEntity entityFear = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_FEAR_KEY);
        ConfigurationEntity entityHappiness = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_HAPPINESS_KEY);
        ConfigurationEntity entityNeutral = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_NEUTRAL_KEY);
        ConfigurationEntity entitySadness = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_SADNESS_KEY);
        ConfigurationEntity entitySurprise = configurationRepo.findByKey(IContanst.EMOTION_MINING_BOUND_SURPRISE_KEY);


        anger = entityAger != null ? Double.valueOf(entityAger.getValue()) : null;
        contempt = entityContempt != null ? Double.valueOf(entityContempt.getValue()) : null;
        disgust = entityDisgust != null ? Double.valueOf(entityDisgust.getValue()) : null;
        fear = entityFear != null ? Double.valueOf(entityFear.getValue()) : null;
        happiness = entityHappiness != null ? Double.valueOf(entityHappiness.getValue()) : null;
        neutral = entityNeutral != null ? Double.valueOf(entityNeutral.getValue()) : null;
        sadness = entitySadness != null ? Double.valueOf(entitySadness.getValue()) : null;
        surprise = entitySurprise != null ? Double.valueOf(entitySurprise.getValue()) : null;
        
        return new EmotionRecognizeScores(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise);
    }
}
