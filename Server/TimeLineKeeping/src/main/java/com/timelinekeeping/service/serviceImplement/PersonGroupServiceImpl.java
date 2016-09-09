package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.PersonGroup;
import com.timelinekeeping.model.PersonGroupTrainingStatus;
import com.timelinekeeping.model.ReponseErrorWaper;
import com.timelinekeeping.service.PersonGroupsService;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ServiceUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lethanhtan on 9/7/16.
 */
@Service
public class PersonGroupServiceImpl implements PersonGroupsService{

    private Logger logger = LogManager.getLogger(PersonGroupServiceImpl.class);

    private String key = AppConfigKeys.getInstance().getApiPropertyValue("ocp.apim.subscription.key");


    public BaseResponse create(String groupName, String groupData) throws URISyntaxException, IOException {
        String urlString = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.create");
        String newURLString = urlString + groupName;

        // Request
        HttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(newURLString);
        URI uri = builder.build();
        HttpPut request = new HttpPut(uri);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", key);

        JSONObject object = new JSONObject();
        object.put("name", groupName);
        object.put("userData", groupData);
        byte[] postData = object.toString().getBytes(StandardCharsets.UTF_8);

        // Request body
        request.setEntity(new ByteArrayEntity(postData));

        // Response
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        String dataResponse = ServiceUtils.getDataResponse(entity);

        // Return value
        BaseResponse responseResult = new BaseResponse();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println(EntityUtils.toString(entity));
            responseResult.setSuccess(true);
        } else {
            ReponseErrorWaper reponseErrorWaper = JsonUtil.convertObject(dataResponse, ReponseErrorWaper.class);
            responseResult.setSuccess(false);
            if (reponseErrorWaper.getError() != null) {
                responseResult.setErrorCode(reponseErrorWaper.getError().getCode());
                responseResult.setMessage(reponseErrorWaper.getError().getMessage());
            }
        }

        return responseResult;
    }

    public BaseResponse listAll(int start, int top) throws URISyntaxException, IOException {
        String urlString = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.findall");

        // Request
        HttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(urlString)
                .addParameter("start", String.valueOf(start))
                .addParameter("top", String.valueOf(top));
        URI uri = builder.build();
        HttpGet request = new HttpGet(uri);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", key);

        // Response
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        String dataResponse = ServiceUtils.getDataResponse(entity);

        // JSON
        BaseResponse reponseResult = new BaseResponse();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            List<PersonGroup> personGroupList = new ArrayList<PersonGroup>();
            personGroupList = JsonUtil.convertObject(dataResponse, personGroupList.getClass());
            reponseResult.setSuccess(true);
            reponseResult.setData(personGroupList);
        }else {
            ReponseErrorWaper reponseErrorWaper = JsonUtil.convertObject(dataResponse, ReponseErrorWaper.class);
            reponseResult.setSuccess(false);
            if (reponseErrorWaper.getError() != null) {
                reponseResult.setErrorCode(reponseErrorWaper.getError().getCode());
                reponseResult.setMessage(reponseErrorWaper.getError().getMessage());
            }
        }

        return reponseResult;

    }

    public BaseResponse trainGroup(String personGroupId) throws URISyntaxException, IOException {
        String urlString = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.person");
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.person.addition");

        // Request
        HttpClient httpclient = HttpClients.createDefault();
        String url = String.format("%s/%s/%s", urlString, personGroupId, urlAddition);
        URIBuilder builder = new URIBuilder(url);
        URI uri = builder.build();
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", key);

        // Response
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        String dataResponse = ServiceUtils.getDataResponse(entity);

        // JSON
        BaseResponse reponseResult = new BaseResponse();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
            reponseResult.setSuccess(true);
        }else {
            ReponseErrorWaper reponseErrorWaper = JsonUtil.convertObject(dataResponse, ReponseErrorWaper.class);
            reponseResult.setSuccess(false);
            if (reponseErrorWaper.getError() != null) {
                reponseResult.setErrorCode(reponseErrorWaper.getError().getCode());
                reponseResult.setMessage(reponseErrorWaper.getError().getMessage());
            }
        }

        return reponseResult;
    }

    public BaseResponse trainPersonGroupStatus(String personGroupId) throws URISyntaxException, IOException {
        String urlString = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.status");
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.status.addition");
        String url = String.format("%s/%s/%s", urlString, personGroupId, urlAddition);

        // Request
        HttpClient httpclient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder(url);
        URI uri = builder.build();
        HttpPost request = new HttpPost(uri);
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", key);

        // Response
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        String dataResponse = ServiceUtils.getDataResponse(entity);

        // JSON
        BaseResponse reponseResult = new BaseResponse();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            PersonGroupTrainingStatus groupStatus = JsonUtil.convertObject(dataResponse,
                    PersonGroupTrainingStatus.class, IContanst.API_COGN_MICROSOFT_PER_GROUP_FORMAT_TIME);

            reponseResult.setSuccess(true);
            reponseResult.setData(groupStatus);
        }else {
            ReponseErrorWaper reponseErrorWaper = JsonUtil.convertObject(dataResponse, ReponseErrorWaper.class);
            reponseResult.setSuccess(false);
            if (reponseErrorWaper.getError() != null) {
                reponseResult.setErrorCode(reponseErrorWaper.getError().getCode());
                reponseResult.setMessage(reponseErrorWaper.getError().getMessage());
            }
        }
        return reponseResult;
    }
}
