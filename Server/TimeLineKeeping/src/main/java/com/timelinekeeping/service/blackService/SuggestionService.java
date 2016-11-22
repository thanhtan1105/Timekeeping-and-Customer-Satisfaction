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
import com.timelinekeeping.util.ServiceUtils;
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
import java.util.Map;

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
            if (age < 15) {
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
//            emotionScores.clearData(IContanst.EXCEPTION_VALUE);

            Map<EEmotion, Double> map = emotionScores.map();

            //get emotionCompar
            ServiceUtils.competitiveEmotion(map);
            List<EmotionCompare> emotionCompares = ServiceUtils.getEmotionExist(map);

            logger.info("emotionCompares: " + JsonUtil.toJson(emotionCompares));

            // create message
            if (emotionCompares.size() <= 0) {
                result = "";
            } else if (emotionCompares.size() == 1) {
                // single time
                result = String.format(IContanst.SUGGESTION_1_EMOTION, subject.getName(), emotionCompares.get(0).getEmotionName());
            } else {
                //many time

                //cut 1 list -> postive list, negative list
                List<EmotionCompare> array1 = new ArrayList<>();
                List<EmotionCompare> array2 = new ArrayList<>();
                for (EmotionCompare emotionCompare : emotionCompares) {
                    if (emotionCompare.getEmotion().getGrade() > 0) {
                        array1.add(emotionCompare);
                    } else {
                        array2.add(emotionCompare);
                    }
                }

                if (array1.size() == 0 || array2.size() == 0) {
                    if (array1.size() == 0) {
                        //only negative
                        array2.sort((EmotionCompare e1, EmotionCompare e2) -> Math.abs(e1.getValue()) > Math.abs(e2.getValue()) ? -1 : 1);
                        array1 = array2;
                    }
                    //only positive
                    if (array1.size() == 2) {
                        result = String.format(IContanst.SUGGESTION_2_EMOTION, subject.getName(), array1.get(0).getEmotionName(), getEmotion(array1.get(1)));
                    } else {
                        result = String.format(IContanst.SUGGESTION_3_EMOTION, subject.getName(), array1.get(0).getEmotionName(), getEmotion(array1.get(1)), getEmotion(array1.get(2)));
                    }

                } else {
                    //Bolt
                    array2.sort((EmotionCompare e1, EmotionCompare e2) -> Math.abs(e1.getValue()) > Math.abs(e2.getValue()) ? -1 : 1);

                    //switch array
                    if (Math.abs(array2.get(0).getValue()) > Math.abs(array1.get(0).getValue())){
                        List<EmotionCompare> tmp = array1;
                        array1 = array2;
                        array2 = tmp;
                    }

                    //size bold =1, positive = 1
//                    if (array1.size() == 1 && array2.size() == 1) {
                        result = String.format(IContanst.SUGGESTION_BOTH_1_1_EMOTION, subject.getName(), array1.get(0).getEmotionName(), getEmotion(array2.get(0)));
//                    } else if (array1.size() > 1 && array1.size() > array2.size()) {
//                        result = String.format(IContanst.SUGGESTION_BOTH_2_1_EMOTION, subject.getName(), array1.get(0).getEmotionName(), getEmotion(array1.get(1)), getEmotion(array2.get(0)));
//                    } else if (array2.size() > 1) {
//                        result = String.format(IContanst.SUGGESTION_BOTH_2_1_EMOTION, subject.getName(), array1.get(0).getEmotionName(), getEmotion(array2.get(0)), getEmotion(array2.get(1)));
//                    } else {
                        logger.error("--%%%%% -- It out our parttern.");
                        logger.info("Positive: " + JsonUtil.toJson(array1));
                        logger.info("Negative: " + JsonUtil.toJson(array2));
//                    }
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
            Page<EmotionContentEntity> pageContent = emotionContentRepo.getEmotionContent(emotion, null, null, null, null, new PageRequest(IContanst.PAGE_PAGE_I, IContanst.PAGE_SIZE_CONTENT));
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
//        emotionRecognizeScores.setNeutral(0.5d);
//        emotionRecognizeScores.setSurprise(0.4d);
//        emotionRecognizeScores.setAnger(0.2d);
//        analysisModel.setEmotion(emotionRecognizeScores);
//        SuggestionService suggestionService = new SuggestionService();
//        System.out.println(UtilApps.formatSentence(suggestionService.getEmotionMessage(analysisModel)));
//    }
}
