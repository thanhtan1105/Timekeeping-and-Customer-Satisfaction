package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.ESuggestionSubject;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.repository.QuantityRepo;
import com.timelinekeeping.util.UtilApps;
import com.timelinekeeping.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by HienTQSE60896 on 10/7/2016.
 */
public class SuggestionService {

    @Autowired
    private QuantityRepo quantityRepo;


    public ESuggestionSubject getSubject(Integer age, Gender gender) {
        ESuggestionSubject result = ESuggestionSubject.ANH;
        if (age < 20) {
            result = ESuggestionSubject.EM;
        } else if (age < 35) {
            if (gender == Gender.FEMALE) {
                result = ESuggestionSubject.CHI;
            } else {
                result = ESuggestionSubject.ANH;
            }
        } else if (age < 50) {
            if (gender == Gender.FEMALE) {
                result = ESuggestionSubject.CO;
            } else {
                result = ESuggestionSubject.CHU;
            }
        } else if (age < 65) {
            result = ESuggestionSubject.BAC;
        } else {
            if (gender == Gender.FEMALE) {
                result = ESuggestionSubject.BA;
            } else {
                result = ESuggestionSubject.ONG;
            }
        }
        return result;
    }


    public String getEmotion(EEmotion emotion, Double value) {
        List<String> quantities = quantityRepo.findQuantity(value);
        String quantity = null;
        if (ValidateUtil.isEmpty(quantity)) {
            quantity = IContanst.QUANLITY_EMOTION_DEFAULT;
        } else {
            quantity = quantities.get(UtilApps.random(0, quantities.size()));
        }
        return String.format("%s %s", quantity, emotion.getName());
    }


    public String listEmotion(EmotionAnalysisModel analysisModel) {
        String result = null;
        EmotionRecognizeScores emotionScores = analysisModel.getEmotion();
        emotionScores.clearData(IContanst.EXCEPTION_VALUE);


        return result;
    }


    public static void main(String[] args) {
        System.out.println(Math.random());
    }
}
