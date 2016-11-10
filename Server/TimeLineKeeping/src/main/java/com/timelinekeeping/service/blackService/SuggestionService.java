package com.timelinekeeping.service.blackService;

import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.ESuggestionSubject;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.EmotionContentEntity;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.model.EmotionCompare;
import com.timelinekeeping.model.EmotionContentModel;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.repository.EmotionContentRepo;
import com.timelinekeeping.repository.QuantityRepo;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.UtilApps;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HienTQSE60896 on 10/7/2016.
 */
@Service
@Component
public class SuggestionService {

    @Autowired
    private QuantityRepo quantityRepo;

    @Autowired
    private EmotionContentRepo emotionContentRepo;

    private Logger logger = LogManager.getLogger(SuggestionService.class);

    public ESuggestionSubject getSubject(Double age, Gender gender) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            ESuggestionSubject result = ESuggestionSubject.ANH;
            if (age < 10) {
                result = ESuggestionSubject.EM;
//            } else if (age < 55) {
//                if (gender == Gender.FEMALE) {
//                    result = ESuggestionSubject.CHI;
//                } else {
//                    result = ESuggestionSubject.ANH;
//                }
            } else {
                if (gender == Gender.FEMALE) {
                    result = ESuggestionSubject.BA;
                } else {
                    result = ESuggestionSubject.ONG;
                }
            }
            return result;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public String getEmotion(EmotionCompare emotionCompare) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<String> quantities = quantityRepo.findQuantity(emotionCompare.getValue());
//        List<String> quantities = new ArrayList<>();
            String quantity = null;
            if (ValidateUtil.isEmpty(quantities)) {
                quantity = IContanst.QUANLITY_EMOTION_DEFAULT;
            } else {
                quantity = quantities.get(UtilApps.random(0, quantities.size() - 1));
            }
            return String.format("%s %s", quantity, emotionCompare.getEmotion().getName());
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    public String getEmotionMessage(EmotionAnalysisModel analysisModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("EmotionAnalysisModel: " + JsonUtil.toJson(analysisModel));

            ESuggestionSubject subject = getSubject(analysisModel.getAge(), analysisModel.getGender());
            String result = "";
            EmotionRecognizeScores emotionScores = analysisModel.getEmotion();
            emotionScores.clearData(IContanst.EXCEPTION_VALUE);

            //get emotionCompar
            List<EmotionCompare> emotionCompares = emotionScores.getEmotionExist();
            logger.info("emotionCompares: " + JsonUtil.toJson(emotionCompares));

            // create message
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

                    //size bold =1, positive = 1
                    if (positive.size() == 1 && negative.size() == 1) {
                        result = String.format(IContanst.SUGGESTION_BOTH_1_1_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(negative.get(0)));
                    } else if (positive.size() > 1 && positive.size() > negative.size()) {
                        result = String.format(IContanst.SUGGESTION_BOTH_2_1_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(positive.get(1)), getEmotion(negative.get(0)));
                    } else if (negative.size() > 1) {
                        result = String.format(IContanst.SUGGESTION_BOTH_2_1_EMOTION, subject.getName(), getEmotion(positive.get(0)), getEmotion(negative.get(0)), getEmotion(negative.get(1)));
                    } else {
                        logger.error("--%%%%% -- It out our parttern.");
                        logger.info("Positive: " + JsonUtil.toJson(positive));
                        logger.info("Negative: " + JsonUtil.toJson(negative));
                    }
                }
                logger.info("Result message: " + result);
            }

            return result;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public List<EmotionContentModel> getSuggestion(EEmotion emotion, Double age, Gender gender) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            ESuggestionSubject subject = getSubject(age, gender);

            // get from database
            //TODO add many emotion
            Page<EmotionContentEntity> pageContent = emotionContentRepo.getEmotionContent(emotion, null, null, new PageRequest(IContanst.PAGE_PAGE_I, IContanst.PAGE_SIZE_CONTENT));
            List<EmotionContentModel> modelContents = new ArrayList<>();
            if (pageContent != null && pageContent.getContent() != null && pageContent.getContent().size() > 0) {
                for (EmotionContentEntity entity : pageContent.getContent()) {
                    EmotionContentModel model = new EmotionContentModel(entity);
                    String message = model.getMessage();
                    model.setMessage(UtilApps.formatSentence(String.format(message, subject.getName())));
                    modelContents.add(model);
                }
            }
            return modelContents;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


//    public static void main(String[] args) {
//        EmotionAnalysisModel analysisModel = new EmotionAnalysisModel();
////        System.out.println(suggestionService.getSubject(24d, Gender.MALE).getName());
//        analysisModel.setAge(24d);
//        analysisModel.setGender(Gender.FEMALE);
//        EmotionRecognizeScores emotionRecognizeScores = new EmotionRecognizeScores();
//        emotionRecognizeScores.setHappiness(0.9d);
//        emotionRecognizeScores.setSadness(0.1d);
//        emotionRecognizeScores.setAnger(0.2d);
//        analysisModel.setEmotion(emotionRecognizeScores);
//        SuggestionService suggestionService = new SuggestionService();
//        System.out.println(suggestionService.getEmotionMessage(analysisModel));
//    }
}
