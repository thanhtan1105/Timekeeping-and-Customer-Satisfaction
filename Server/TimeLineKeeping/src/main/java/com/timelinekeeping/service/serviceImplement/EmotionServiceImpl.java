package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.EmotionCustomerEntity;
import com.timelinekeeping.entity.MessageEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.BaseResponseG;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.modelMCS.EmotionRecognizeResponse;
import com.timelinekeeping.modelMCS.EmotionRecognizeScores;
import com.timelinekeeping.modelMCS.FaceDetectResponse;
import com.timelinekeeping.modelMCS.RectangleImage;
import com.timelinekeeping.repository.AccountRepo;
import com.timelinekeeping.repository.EmotionRepo;
import com.timelinekeeping.repository.MessageRepo;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by lethanhtan on 9/15/16.
 */
@Service
public class EmotionServiceImpl {

    private Logger logger = Logger.getLogger(EmotionServiceImpl.class);

    @Autowired
    private EmotionRepo emotionRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private MessageRepo messageRepo;

    public BaseResponse save(InputStream inputStreamImg, Long employeeId, boolean isFirstTime) throws IOException, URISyntaxException {

        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse responseResult = new BaseResponse();
        byte[] bytes = IOUtils.toByteArray(inputStreamImg);

        // emotion
        EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
        BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(bytes));

        // face detect
        FaceServiceMCSImpl faceServiceMCS = new FaceServiceMCSImpl();
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(bytes));

        if (isFirstTime) {
            // add suggess
        }

        // TODO: implement multi thread
        // parser emotion response
        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();
        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

        // parser face response
        List<FaceDetectResponse> faceRecognizeList = (List<FaceDetectResponse>) faceResponse.getData();
        FaceDetectResponse faceDetectResponse = faceRecognizeList.get(0);

        // create time
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        Double anger = emotionRecognize.getScores().getAnger(); // get anger
        Double contempt = emotionRecognize.getScores().getContempt(); // get contempt
        Double disgust = emotionRecognize.getScores().getDisgust(); // get Disgust
        Double fear = emotionRecognize.getScores().getFear(); // get Fear
        Double happiness = emotionRecognize.getScores().getHappiness(); // get happiness
        Double neutral = emotionRecognize.getScores().getNeutral(); // get neutral
        Double sadness = emotionRecognize.getScores().getSadness(); // get sadness
        Double surprise = emotionRecognize.getScores().getSurprise(); // get surprise
        Double age = faceDetectResponse.getFaceAttributes().getAge();
        Gender gender = faceDetectResponse.getFaceAttributes().getGender().toUpperCase().equals("MALE") ? Gender.MALE : Gender.FEMALE;
        Double smile = faceDetectResponse.getFaceAttributes().getSmile();

        // save to database
        EmotionCustomerEntity emotionCustomerEntity = new EmotionCustomerEntity(timestamp, anger, contempt, disgust, fear, happiness, neutral, sadness, surprise, age, gender, smile);
//        emotionCustomerEntity.setCreateBy(accountRepo.findOne(employeeId));
        EmotionCustomerEntity emotionRespone = emotionRepo.saveAndFlush(emotionCustomerEntity);

        logger.info(IContanst.END_METHOD_SERVICE);
        return emotionRespone;
    }

    public BaseResponse analyseEmotion(Long id) {
        logger.info("[Analyse Emotion] BEGIN");
        BaseResponse baseResponse = new BaseResponse();
        EmotionCustomerEntity emotion = emotionRepo.findOne(id);
        if (emotion != null) {
            logger.info("[Analyse Emotion] anger: " + emotion.getAnger());
            logger.info("[Analyse Emotion] contempt: " + emotion.getContempt());
            logger.info("[Analyse Emotion] disgust: " + emotion.getDisgust());
            logger.info("[Analyse Emotion] fear: " + emotion.getFear());
            logger.info("[Analyse Emotion] happiness: " + emotion.getHappiness());
            logger.info("[Analyse Emotion] neutral: " + emotion.getNeutral());
            logger.info("[Analyse Emotion] sadness: " + emotion.getSadness());
            logger.info("[Analyse Emotion] surprise: " + emotion.getSurprise());
            Map<String, Double> listEmotions = new HashMap<String, Double>();
            listEmotions.put("anger", emotion.getAnger());
            listEmotions.put("contempt", emotion.getContempt());
            listEmotions.put("disgust", emotion.getDisgust());
            listEmotions.put("fear", emotion.getFear());
            listEmotions.put("happiness", emotion.getHappiness());
            listEmotions.put("neutral", emotion.getNeutral());
            listEmotions.put("sadness", emotion.getSadness());
            listEmotions.put("surprise", emotion.getSurprise());
            Map.Entry<String, Double> maxEntry = null;
            for (Map.Entry<String, Double> entry : listEmotions.entrySet()) {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                    maxEntry = entry;
                }
            }
            logger.info("[Analyse Emotion] current emotion: " + maxEntry.getKey());
            logger.info("[Analyse Emotion] ratio: " + maxEntry.getValue());
            baseResponse.setData(maxEntry);
        }
        logger.info("[Analyse Emotion] END");
        return baseResponse;
    }

    private EmotionCustomerEntity parseEmotionFaceResponse(BaseResponse emotionResponse, BaseResponse faceResponse) {
        // parser emotion response
        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();
        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

        // parser face response
        List<FaceDetectResponse> faceRecognizeList = (List<FaceDetectResponse>) faceResponse.getData();
        FaceDetectResponse faceDetectResponse = faceRecognizeList.get(0);

        // get emotion_scores
        Double anger = emotionRecognize.getScores().getAnger(); // get anger
        Double contempt = emotionRecognize.getScores().getContempt(); // get contempt
        Double disgust = emotionRecognize.getScores().getDisgust(); // get Disgust
        Double fear = emotionRecognize.getScores().getFear(); // get Fear
        Double happiness = emotionRecognize.getScores().getHappiness(); // get happiness
        Double neutral = emotionRecognize.getScores().getNeutral(); // get neutral
        Double sadness = emotionRecognize.getScores().getSadness(); // get sadness
        Double surprise = emotionRecognize.getScores().getSurprise(); // get surprise

        // get face_attributes
        Double age = faceDetectResponse.getFaceAttributes().getAge(); // get age
        Gender gender = faceDetectResponse.getFaceAttributes().getGender().toUpperCase()
                .equals("MALE") ? Gender.MALE : Gender.FEMALE; // get gender
        Double smile = faceDetectResponse.getFaceAttributes().getSmile(); // get smile
        return new EmotionCustomerEntity(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise, age, gender, smile);
    }

    private EmotionAnalysisModel analyseEmotion(EmotionRecognizeScores emotionScores) {
        logger.info("[Analyse Emotion Service] BEGIN SERVICE");
        Map<EEmotion, Double> listEmotions = new HashMap<EEmotion, Double>();
        listEmotions.put(EEmotion.ANGER, emotionScores.getAnger());
        listEmotions.put(EEmotion.CONTEMPT, emotionScores.getContempt());
        listEmotions.put(EEmotion.DISGUST, emotionScores.getDisgust());
        listEmotions.put(EEmotion.FEAR, emotionScores.getFear());
        listEmotions.put(EEmotion.HAPPINESS, emotionScores.getHappiness());
        listEmotions.put(EEmotion.NEUTRAL, emotionScores.getNeutral());
        listEmotions.put(EEmotion.SADNESS, emotionScores.getSadness());
        listEmotions.put(EEmotion.SURPRISE, emotionScores.getSurprise());
        Map.Entry<EEmotion, Double> maxEntry = null;
        for (Map.Entry<EEmotion, Double> entry : listEmotions.entrySet()) {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
                maxEntry = entry;
            }
        }
        logger.info("[Analyse Emotion Service] emotionMost: " + maxEntry.getKey());
        logger.info("[Analyse Emotion Service] END SERVICE");

        return new EmotionAnalysisModel(maxEntry.getKey(), emotionScores);
    }

    private List<MessageEntity> getListMessage(Gender gender, Double fromAge, Double toAge, EEmotion emotion) {

        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        List<MessageEntity> messageEntities = messageRepo.getListMessage(fromAge, toAge, gender.getIndex(), emotion.getName());
        logger.info(IContanst.END_METHOD_SERVICE);
        return messageEntities;
    }

    public BaseResponse getCustomerEmotion(InputStream inputStreamImg, Long employeeId, boolean isFirstTime)
            throws IOException, URISyntaxException {
        logger.info("[Get Customer Emotion] BEGIN SERVICE");
        BaseResponse baseResponse = new BaseResponse();
        HashMap<String, Object> responseData = new HashMap<>();

        byte[] bytes = IOUtils.toByteArray(inputStreamImg);
        // face detect
        FaceServiceMCSImpl faceServiceMCS = new FaceServiceMCSImpl();
        BaseResponse faceResponse = faceServiceMCS.detect(new ByteArrayInputStream(bytes));
        logger.info("[Get Customer Emotion] face response success: " + faceResponse.isSuccess());
        if (faceResponse.isSuccess()) {
            if (faceResponse.getData() != null) {

                // parser face response
                List<FaceDetectResponse> faceRecognizeList = (List<FaceDetectResponse>) faceResponse.getData();
                if (faceRecognizeList != null || faceRecognizeList.size() > 0) {

                    List<EmotionAnalysisModel> emotionAnalysisModels = new ArrayList<>();
                    for (FaceDetectResponse faceDetectResponse : faceRecognizeList) {
                        // get face_attributes
                        Double age = faceDetectResponse.getFaceAttributes().getAge(); // get age
                        Gender gender = faceDetectResponse.getFaceAttributes().getGender().toUpperCase()
                                .equals("MALE") ? Gender.MALE : Gender.FEMALE; // get gender
                        // get rectangle image
                        RectangleImage rectangleImage = faceDetectResponse.getFaceRectangle();

                        // emotion recognize
                        EmotionServiceMCSImpl emotionServiceMCS = new EmotionServiceMCSImpl();
                        BaseResponse emotionResponse = emotionServiceMCS.recognize(new ByteArrayInputStream(bytes), rectangleImage);

                        // parser emotion response
                        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();

                        //TODO check many face
                        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

                        // get emotion_scores
                        Double anger = emotionRecognize.getScores().getAnger(); // get anger
                        Double contempt = emotionRecognize.getScores().getContempt(); // get contempt
                        Double disgust = emotionRecognize.getScores().getDisgust(); // get Disgust
                        Double fear = emotionRecognize.getScores().getFear(); // get Fear
                        Double happiness = emotionRecognize.getScores().getHappiness(); // get happiness
                        Double neutral = emotionRecognize.getScores().getNeutral(); // get neutral
                        Double sadness = emotionRecognize.getScores().getSadness(); // get sadness
                        Double surprise = emotionRecognize.getScores().getSurprise(); // get surprise

                        EmotionRecognizeScores emotionRecognizeScores
                                = new EmotionRecognizeScores(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise);
                        // get customer emotion
                        EmotionAnalysisModel emotionAnalysisModel = analyseEmotion(emotionRecognizeScores);
                        emotionAnalysisModel.setAge(age);
                        emotionAnalysisModel.setGender(gender);
                        emotionAnalysisModel.setRectangleImage(faceDetectResponse.getFaceRectangle());

                        emotionAnalysisModels.add(emotionAnalysisModel);

                    }
                    baseResponse.setSuccess(true);
                    responseData.put("emotion", emotionAnalysisModels);
//                    baseResponse.setData(emotionAnalysisModels);
                }

                if (isFirstTime) {
                    // TODO: get suggestion
                    List<EmotionAnalysisModel> emotionAnalysisModels = (List<EmotionAnalysisModel>) responseData.get("emotion");
                    if (emotionAnalysisModels.size() > 0) {
                        EmotionAnalysisModel emotionAnalysisModel = emotionAnalysisModels.get(0);
                        List<MessageEntity> messageEntities = getListMessage(emotionAnalysisModel.getGender(),
                                emotionAnalysisModel.getAge() - 10,
                                emotionAnalysisModel.getAge() + 10,
                                emotionAnalysisModel.getEmotionMost());
                        responseData.put("suggestMessage", messageEntities);
                    }
                }

                baseResponse.setData(responseData);

//            // create time
//            java.util.Date date = new java.util.Date();
//            Timestamp timestamp = new Timestamp(date.getTime());

//            // save to database

//            emotion.setCreateTime(timestamp);
//            emotion.setCreateBy(accountRepo.findOne(employeeId));
//            emotionRepo.saveAndFlush(emotion);
            }
        } else {
            baseResponse.setSuccess(false);
            baseResponse.setMessage(ERROR.EMOTION_API_GET_CUSTOMER_EMOTION_EMPTY_DETECT);
        }

        logger.info("[Get Customer Emotion] END SERVICE");
        return baseResponse;
    }

}
