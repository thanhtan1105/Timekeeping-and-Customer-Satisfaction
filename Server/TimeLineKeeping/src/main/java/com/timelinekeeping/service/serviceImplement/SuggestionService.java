package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.ESuggestionSubject;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.model.EmotionCompare;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.repository.QuantityRepo;
import com.timelinekeeping.util.UtilApps;
import com.timelinekeeping.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
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


    public String getEmotion(EmotionCompare emotionCompare) {
        List<String> quantities = quantityRepo.findQuantity(emotionCompare.getValue());
        String quantity = null;
        if (ValidateUtil.isEmpty(quantity)) {
            quantity = IContanst.QUANLITY_EMOTION_DEFAULT;
        } else {
            quantity = quantities.get(UtilApps.random(0, quantities.size()));
        }
        return String.format("%s %s", quantity, emotionCompare.getEmotion().getName());
    }


    public String listEmotion(EmotionAnalysisModel analysisModel) {
        String result = "";
        EmotionRecognizeScores emotionScores = analysisModel.getEmotion();
        emotionScores.clearData(IContanst.EXCEPTION_VALUE);
        //get emotionCompar
        List<EmotionCompare> emotionCompares = emotionScores.getEmotionExist();
        if (emotionCompares.size() <= 0) {
            result= "";
        } else if (emotionCompares.size() == 1) {
            // single time
            result= getEmotion(emotionCompares.get(0));
        } else {
            //many time

            //cut 1 list -> postive list, negative list
            List<EmotionCompare> postive = new ArrayList<>();
            List<EmotionCompare> negative = new ArrayList<>();
            for (EmotionCompare emotionCompare : emotionCompares) {
                if (emotionCompare.getEmotion().getGrade() > 0) {
                    postive.add(emotionCompare);
                } else {
                    negative.add(emotionCompare);
                }
            }
            if (negative.size() == 0) {
                //only positive

                if (postive.size() == 2) {
                    result = String.format("%s và %s", getEmotion(postive.get(0)), getEmotion(postive.get(1)));
                }else{
                    result = String.format("%s , %s và %s", getEmotion(postive.get(0)), getEmotion(postive.get(1)), getEmotion(postive.get(2)));
                }


            } else if (postive.size() == 0) {
                //only negative

            } else {
                //Bolt
            }

        }

        return result;
    }


    public static void main(String[] args) {
        System.out.println(Math.random());
    }
}
