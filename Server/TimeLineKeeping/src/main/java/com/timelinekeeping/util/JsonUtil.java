package com.timelinekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.FaceDetectRespone;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/8/2016.
 */
public class JsonUtil {

    public static final int NORMAl_PARSER = 0;
    public static final int TIME_PARSER = 1;
    public static final int LIST_PARSER = 2;


    private static ObjectMapper mapper = new ObjectMapper();
    private static Logger logger = LogManager.getLogger(JsonUtil.class);

    public static String toJson(Object data) {
        try {
            return mapper.writeValueAsString(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T convertObject(String data, Class<T> classObj) {
        try {
            return (T) mapper.readValue(data, classObj);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T convertObject(String data, Class<T> classObj, String formatTime) {
        try {
            DateFormat format = new SimpleDateFormat(formatTime);
            mapper.setDateFormat(format);
            return mapper.readValue(data, classObj);
        } catch (Exception e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> convertListObject(String data, Class<T> classObj) {
        try {
            return mapper.readValue(data, mapper.getTypeFactory().constructCollectionType(List.class, classObj));
        } catch (Exception e) {
            logger.error(e);
            return null;
        }
    }

    public static void main(String[] args) {
        BaseResponse reponse = JsonUtil.convertObject("{\"success\":true,\"message\":\"\",\"data\":null}", BaseResponse.class);
        System.out.println(reponse.isSuccess());
    }
}
