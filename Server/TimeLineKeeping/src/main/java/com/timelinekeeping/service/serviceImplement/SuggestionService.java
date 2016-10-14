package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.ESuggestionSubject;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.EmotionContentEntity;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.model.EmotionCompare;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.repository.EmotionContentRepo;
import com.timelinekeeping.repository.QuantityRepo;
import com.timelinekeeping.util.UtilApps;
import com.timelinekeeping.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HienTQSE60896 on 10/7/2016.
 */
@Service
public class SuggestionService {

    @Autowired
    private QuantityRepo quantityRepo;

    @Autowired
    private EmotionContentRepo emotionContentRepo;


    public ESuggestionSubject getSubject(Double age, Gender gender) {
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
//        List<String> quantities = new ArrayList<>();
        String quantity = null;
        if (ValidateUtil.isEmpty(quantities)) {
            quantity = IContanst.QUANLITY_EMOTION_DEFAULT;
        } else {
            quantity = quantities.get(UtilApps.random(0, quantities.size() - 1));
        }
        return String.format("%s %s", quantity, emotionCompare.getEmotion().getName());
    }


    public String getEmotionMessage(EmotionAnalysisModel analysisModel) {
        ESuggestionSubject subject = getSubject(analysisModel.getAge(), analysisModel.getGender());
        String result = "";
        EmotionRecognizeScores emotionScores = analysisModel.getEmotion();
        emotionScores.clearData(IContanst.EXCEPTION_VALUE);
        //get emotionCompar
        List<EmotionCompare> emotionCompares = emotionScores.getEmotionExist();
        if (emotionCompares.size() <= 0) {
            result = "";
        } else if (emotionCompares.size() == 1) {
            // single time
            result = String.format(IContanst.SUGGESTION_1_EMOTION, subject.getName(), getEmotion(emotionCompares.get(0)));
        } else {
            //many time

            //cut 1 list -> postive list, negative list
            List<EmotionCompare> positive = new ArrayList<>();
            List<EmotionCompare> negative = new ArrayList<>();
            for (EmotionCompare emotionCompare : emotionCompares) {
                if (emotionCompare.getEmotion().getGrade() > 0) {
                    positive.add(emotionCompare);
                } else {
                    negative.add(emotionCompare);
                }
            }
            if (positive.size() == 0 || negative.size() == 0) {
                if (positive.size() == 0) {
                    //only negative
                    negative.sort((EmotionCompare e1, EmotionCompare e2) -> Math.abs(e1.getValue()) > Math.abs(e2.getValue()) ? -1 : 1);
                    positive = negative;
                }
                //only positive
                if (positive.size() == 2) {
                    result = String.format(IContanst.SUGGESTION_2_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(positive.get(1)));
                } else {
                    result = String.format(IContanst.SUGGESTION_3_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(positive.get(1)), getEmotion(positive.get(2)));
                }

            } else {
                //Bolt
                negative.sort((EmotionCompare e1, EmotionCompare e2) -> Math.abs(e1.getValue()) > Math.abs(e2.getValue()) ? -1 : 1);
                if (positive.size() > negative.size()) {
                    result = String.format(IContanst.SUGGESTION_BOTH_2_1_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(positive.get(1)), getEmotion(negative.get(0)));
                } else {
                    result = String.format(IContanst.SUGGESTION_BOTH_2_1_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(negative.get(0)), getEmotion(negative.get(1)));
                }
            }

        }

        return result;
    }

    public String getSuggestion(EEmotion emotion, Double age, Gender gender) {
        ESuggestionSubject subject = getSubject(age, gender);
        /*String formatString ;
        switch (emotion){
            case ANGER: formatString = "Bạn nên rót cho %s ly nước."; break;
            case CONTEMPT: formatString = "Bạn nên bình tỉnh và tôn trọng %s khi nói chuyện";break;
            case DISGUST: formatString = "Bạn nên lăng nghe những ấm uất của %s.";break;
            case FEAR: formatString = "Bạn nên tạo không gian cỡ mở với %s.";break;
            case HAPPINESS: formatString = "Bạn nên đón nhận niềm vui chung với %s";break;
            case NEUTRAL: formatString = "Bạn nên nói chuyện với %s bình thường";break;
            case SADNESS: formatString = "Bạn nên nói chuyện với %s nhẹ nhàng.";break;
            case SURPRISE: formatString = "Bạn nên bình tỉnh nói chuyện với %s.";break;
            default: formatString = "Bạn nên nói chuyện với %s nhẹ nhàng.";break;
        }*/

        // get from database
        //TODO add many emotion
        Page<EmotionContentEntity> pageContent = emotionContentRepo.getEmotionContent(emotion, null, null, new PageRequest(IContanst.PAGE_PAGE_I, IContanst.PAGE_SIZE_CONTENT));
        if(pageContent != null && pageContent.getContent() != null && pageContent.getContent().size() > 0){

        }
//        String formatString = listContent.get(UtilApps.random(0, listContent.size() - 1)).getMessage();
//        return String.format(formatString, subject.getName());
        return null;
    }


    public static void main(String[] args) {
        EmotionAnalysisModel analysisModel = new EmotionAnalysisModel();
//        System.out.println(suggestionService.getSubject(24d, Gender.MALE).getName());
        analysisModel.setAge(24d);
        analysisModel.setGender(Gender.FEMALE);
        EmotionRecognizeScores emotionRecognizeScores = new EmotionRecognizeScores();
        emotionRecognizeScores.setHappiness(0.9d);
        emotionRecognizeScores.setSadness(0.1d);
        emotionRecognizeScores.setAnger(0.2d);
        analysisModel.setEmotion(emotionRecognizeScores);
        SuggestionService suggestionService = new SuggestionService();
        System.out.println(suggestionService.getEmotionMessage(analysisModel));
    }
}
