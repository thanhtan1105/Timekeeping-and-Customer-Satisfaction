package com.timelinekeeping.service.blackService;

import com.timelinekeeping.constant.EConfiguration;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.ConfigurationEntity;
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
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_AGE_A_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }

    public Double getEmotionAgeB() {
        ConfigurationEntity entity = configurationRepo.findByKey(IContanst.EMOTION_AGE_B_KEY);
        return entity != null ? Double.valueOf(entity.getValue()) : null;
    }
}
