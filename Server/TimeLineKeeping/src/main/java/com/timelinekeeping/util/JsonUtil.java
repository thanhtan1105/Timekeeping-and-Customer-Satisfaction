package com.timelinekeeping.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timelinekeeping.model.BaseResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by HienTQSE60896 on 9/8/2016.
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

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
            return (T) mapper.readValue(data, classObj);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        BaseResponse reponse = JsonUtil.convertObject("{\"success\":true,\"message\":\"\",\"data\":null}", BaseResponse.class);
        System.out.println(reponse.isSuccess());
    }
}
