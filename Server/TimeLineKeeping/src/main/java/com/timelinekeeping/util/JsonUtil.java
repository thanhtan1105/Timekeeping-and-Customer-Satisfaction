package com.timelinekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.modelMCS.EmotionRecognizeResponse;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/8/2016.
 */
public class JsonUtil {

    public static final int NORMAl_PARSER = 0;
    public static final int TIME_PARSER = 1;
    public static final int LIST_PARSER = 2;
    public static final int MAP_PARSER = 3;


    private static ObjectMapper mapper = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(JsonUtil.class);

    public static String toJson(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static <T> T convertObject(String data, Class<T> classObj) {
        try {
            logger.info("json: " + data);
            logger.info("class: " + classObj.getName());
            return (T) mapper.readValue(data, classObj);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static <T> T convertObject(String data, Class<T> classObj, String formatTime) {
        try {
            DateFormat format = new SimpleDateFormat(formatTime);
            mapper.setDateFormat(format);
            return mapper.readValue(data, classObj);
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convertListObject(String data, Class<T> classObj) {
        try {
            logger.info("json: " + data);
            logger.info("class: " + classObj.getName());
            return mapper.readValue(data, mapper.getTypeFactory().constructCollectionType(List.class, classObj));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Map<String, T> convertMapObject(String data, Class<T> classObj) {
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructMapType(Map.class, String.class, classObj));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static void main(String[] args) {
        String json = "[{\"faceRectangle\":{\"height\":952,\"left\":806,\"top\":1275,\"width\":952},\"scores\":{\"anger\":0.80901897,\"contempt\":1.84519173E-08,\"disgust\":0.000492386345,\"fear\":0.150978327,\"happiness\":0.000305363646,\"neutral\":5.94567655E-06,\"sadness\":0.009365463,\"surprise\":0.0298335142}}]";
        List<EmotionRecognizeResponse> obj = JsonUtil.convertListObject(json, EmotionRecognizeResponse.class);
        System.out.println("json:" + JsonUtil.toJson(obj));
    }
}
