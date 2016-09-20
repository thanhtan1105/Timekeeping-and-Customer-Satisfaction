package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.constant.EEmotion;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.entity.Emotion;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.EmotionAnalysisModel;
import com.timelinekeeping.modelAPI.EmotionRecognizeResponse;
import com.timelinekeeping.modelAPI.FaceDetectRespone;
import com.timelinekeeping.repository.EmotionRepo;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lethanhtan on 9/15/16.
 */
@Service
public class EmotionServiceImpl {

    private Logger logger = Logger.getLogger(EmotionServiceImpl.class);

    @Autowired
    private EmotionRepo repo;

    public BaseResponse save(InputStream inputStreamImg, Long employeeId, boolean isFirstTime) throws IOException, URISyntaxException {
        BaseResponse baseResponse = new BaseResponse();
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
        // TO-DO: implement multi thread
        // parser emotion response
        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();
        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

        // parser face response
        List<FaceDetectRespone> faceRecognizeList = (List<FaceDetectRespone>) faceResponse.getData();
        FaceDetectRespone faceDetectResponse = faceRecognizeList.get(0);

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
        Emotion emotion = new Emotion(timestamp, employeeId, anger, contempt, disgust, fear, happiness, neutral, sadness, surprise, age, gender, smile);
        baseResponse.setData(repo.saveAndFlush(emotion));
        return baseResponse;
    }

    public BaseResponse analyseEmotion(Long id) {
        logger.info("[Analyse Emotion] BEGIN");
        BaseResponse baseResponse = new BaseResponse();
        Emotion emotion = repo.findOne(id);
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

    private Emotion parseEmotionFaceResponse(BaseResponse emotionResponse, BaseResponse faceResponse) {
        // parser emotion response
        List<EmotionRecognizeResponse> emotionRecognizeList = (List<EmotionRecognizeResponse>) emotionResponse.getData();
        EmotionRecognizeResponse emotionRecognize = emotionRecognizeList.get(0);

        // parser face response
        List<FaceDetectRespone> faceRecognizeList = (List<FaceDetectRespone>) faceResponse.getData();
        FaceDetectRespone faceDetectResponse = faceRecognizeList.get(0);

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
        return new Emotion(anger, contempt, disgust, fear, happiness, neutral, sadness, surprise, age, gender, smile);
    }

    private EmotionAnalysisModel analyseEmotion(Emotion emotion) {
        logger.info("[Analyse Emotion Service] BEGIN SERVICE");
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
        EEmotion emotionMost = EEmotion.fromName(maxEntry.getKey());
        logger.info("[Analyse Emotion Service] emotionMost: " + maxEntry.getKey());
        logger.info("[Analyse Emotion Service] END SERVICE");

        return new EmotionAnalysisModel(emotionMost, emotion);
    }

    public BaseResponse getCustomerEmotion(InputStream inputStreamImg, Long employeeId, boolean isFirstTime)
            throws IOException, URISyntaxException {
        logger.info("[Get Customer Emotion] BEGIN SERVICE");
        BaseResponse baseResponse = new BaseResponse();
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

        // parse emotion, face response
        Emotion emotion = parseEmotionFaceResponse(emotionResponse, faceResponse);

        // create time
        java.util.Date date = new java.util.Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        // save to database
        emotion.setCreateTime(timestamp);
        emotion.setCreateBy(employeeId);
        repo.saveAndFlush(emotion);

        // get customer emotion
        EmotionAnalysisModel emotionAnalysisModel = analyseEmotion(emotion);
        baseResponse.setData(emotionAnalysisModel);
        logger.info("[Get Customer Emotion] END SERVICE");
        return baseResponse;
    }
}
