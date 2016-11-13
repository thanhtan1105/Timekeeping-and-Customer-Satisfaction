package com.timelinekeeping.service.blackService;

import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by HienTQSE60896 on 11/13/2016.
 */
public class WebStorage {

    private final Logger logger = LogManager.getLogger(WebStorage.class);


    public void upload() {
        File file = new File("D:\\CP\\FILE\\customerCode3BEFORE1478852014859.jpg");
        String uri = "http://202.78.227.93:6783/Api/FileUpload/Post";

        MultiValueMap<String, Object> mapEntity = new LinkedMultiValueMap<>();
//        mapEntity.set("File", new );

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<MultiValueMap<String, Object>>(mapEntity), String.class);
        logger.info(JsonUtil.toJson(response));
    }

    public static void main(String[] args) {
        try {
            File file = new File("D:\\CP\\FILE\\customerCode3BEFORE1478852014859.jpg");
//            new WebStorage().upload(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
