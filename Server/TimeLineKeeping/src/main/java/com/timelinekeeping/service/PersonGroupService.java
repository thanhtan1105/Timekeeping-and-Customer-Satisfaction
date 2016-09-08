package com.timelinekeeping.service;

import com.google.gson.Gson;
import com.timelinekeeping.config.AppConfigKeys;
import com.timelinekeeping.entity.PersonGroup;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.json.GsonJsonParser;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lethanhtan on 9/7/16.
 */
public class PersonGroupService {

    private String key = AppConfigKeys.getInstance().getPropertyValue("ocp.apim.subscription.key");

    public JSONObject create(String groupName, String groupData) throws URISyntaxException, IOException {
        String urlString = AppConfigKeys.getInstance().getPropertyValue("api.person.group.create");
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
        JSONObject returnObject = new JSONObject();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            System.out.println(EntityUtils.toString(entity));
            returnObject.put("isSuccess", true);
            returnObject.put("error", null);

        } else {
            returnObject.put("isSuccess", false);
            returnObject.put("error", dataResponse);
        }

        return returnObject;
    }

    public List<PersonGroup> listAll(int start, int top) throws URISyntaxException, IOException {
        String urlString = AppConfigKeys.getInstance().getPropertyValue("api.person.group.findall");

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
        List<PersonGroup> personGroupList = new ArrayList<PersonGroup>();
        Gson gson = new Gson();
        personGroupList = gson.fromJson(dataResponse, personGroupList.getClass());

        return personGroupList;

    }
}
