package com.timelinekeeping.service.blackService;

import com.timelinekeeping.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.Arrays;

/**
 * Created by HienTQSE60896 on 11/13/2016.
 */
@RestController
@RequestMapping("/api/store")
public class WebStorage {

    private final Logger logger = LogManager.getLogger(WebStorage.class);


    @RequestMapping("/")
    public String upload() throws IOException {
        String fileName = "D:\\CP\\FILE\\147849309073-1.jpg";
        File file = new File(fileName);
        String uri = "http://202.78.227.93:6783/Api/FileUpload/Post";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//        headers.add(HttpHeaders.ACCEPT_ENCODING, "UTF-8");
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(fileName));
        MultiValueMap<String, Object> mapEntity = new LinkedMultiValueMap<>();
        mapEntity.set("File", );

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<MultiValueMap<String, Object>>(mapEntity, headers), String.class);
        logger.info(JsonUtil.toJson(response));
        return null;
    }

    public static void main(String[] args) {
        try {
            File file = new File("D:\\CP\\FILE\\customerCode3BEFORE1478852014859.jpg");
            new WebStorage().upload();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
