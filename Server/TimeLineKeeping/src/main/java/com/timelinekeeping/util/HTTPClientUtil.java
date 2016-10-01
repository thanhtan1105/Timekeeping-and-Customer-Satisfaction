package com.timelinekeeping.util;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.modelMCS.ResponseErrorWrap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class HTTPClientUtil {

    private Logger logger = LogManager.getLogger(HTTPClientUtil.class);
    /** key */
    private String key;
    private String keyFace = AppConfigKeys.getInstance().getApiPropertyValue("ocp.apim.subscription.key");
    private String keyEmotion = AppConfigKeys.getInstance().getApiPropertyValue("ocp.apim.subscription.key.emotion");

    private static HTTPClientUtil clientUtil;


    /** HTTP Standstand */
    private HttpRequestBase request;

    /** type parser**/
    private int typeJson = JsonUtil.NORMAl_PARSER;

    /*** class return*/
    private Class<?> classReturn;

    private HTTPClientUtil(){

    }

    public static HTTPClientUtil getInstance(int keyType) {
        if (clientUtil == null) {
            clientUtil = new HTTPClientUtil();
        }
        switch (keyType) {
            case IContanst.HTTP_CLIENT_KEY_FACE:
                clientUtil.setKeyFace();
                break;
            case IContanst.HTTP_CLIENT_KEY_EMOTION:
                clientUtil.setKeyEmotion();
                break;
        }
        return clientUtil;
    }

    public static HTTPClientUtil getInstanceFace() {
        if (clientUtil == null) {
            clientUtil = new HTTPClientUtil();
        }
        clientUtil.setKeyFace();
        return clientUtil;
    }
    public static HTTPClientUtil getInstanceEmotion() {
        if (clientUtil == null) {
            clientUtil = new HTTPClientUtil();
        }
        clientUtil.setKeyEmotion();
        return clientUtil;
    }

    public void setKeyFace(){
        key = keyFace;
    }

    public void setKeyEmotion(){
        key = keyEmotion;
    }

    public BaseResponse toGet(URI uri, Class<?> classReturn) throws IOException {
        return toGet(uri, JsonUtil.NORMAl_PARSER, classReturn);
    }

    public BaseResponse toGet(String uri, int typeJson, Class<?> classReturn) throws IOException, URISyntaxException {
        return toGet(new URIBuilder(uri).build(), typeJson, classReturn);
    }
    public BaseResponse toGet(URI uri, int typeJson, Class<?> classReturn) throws IOException {
        this.typeJson = typeJson;
        this.classReturn = classReturn;
        request = new HttpGet(uri);
        setHeaderJson();
        return toProcess();
    }

    public BaseResponse toPost(String url) throws URISyntaxException, IOException {
        return toPost(new URIBuilder(url).build(), null, JsonUtil.NORMAl_PARSER,null);
    }

    public BaseResponse toPost(String url, int typeParser, Class<?> classReturn) throws URISyntaxException, IOException{
        return toPost(new URIBuilder(url).build(), null, typeParser, classReturn);
    }
    public BaseResponse toPost(String url, HttpEntity entity, int typeParser, Class<?> classReturn) throws URISyntaxException, IOException{
        return toPost(new URIBuilder(url).build(), entity, typeParser, classReturn);
    }

    public BaseResponse toPost(URI uri, HttpEntity entity, int typeJson, Class<?> classReturn) throws IOException {
        this.typeJson = typeJson;
        this.classReturn = classReturn;
        request = new HttpPost(uri);
        setHeaderJson();
        if (entity != null) {
            ((HttpPost) request).setEntity(entity);
        }
        return toProcess();
    }

    public BaseResponse toPostOct(String url, HttpEntity entity, int typeJson, Class<?> classReturn) throws IOException, URISyntaxException {
        return toPostOct(new URIBuilder(url).build(), entity, typeJson, classReturn);
    }

    public BaseResponse toPostOct(URI uri, HttpEntity entity, int typeJson, Class<?> classReturn) throws IOException {
        this.typeJson = typeJson;
        this.classReturn = classReturn;
        request = new HttpPost(uri);
        setHeaderOct();
        if (entity != null) {
            ((HttpPost) request).setEntity(entity);
        }
        return toProcess();
    }



    public BaseResponse toPut(String url, HttpEntity entity) throws IOException, URISyntaxException {

        return toPut(new URIBuilder(url).build(), entity, JsonUtil.NORMAl_PARSER, null);
    }

    public BaseResponse toPut(URI uri, HttpEntity entity, int typeJson, Class<?> classReturn) throws IOException {
        this.typeJson = typeJson;
        this.classReturn = classReturn;
        request = new HttpPut(uri);
        setHeaderJson();
        if (entity!= null) {
            ((HttpPut) request).setEntity(entity);
        }
        return toProcess();
    }


    private void  setHeaderJson(){
        request.setHeader("Content-Type", "application/json");
        request.setHeader("Ocp-Apim-Subscription-Key", key);
    }

    private void  setHeaderOct(){
        request.setHeader("Content-Type", "application/octet-stream");
        request.setHeader("Ocp-Apim-Subscription-Key", key);
    }

    /***/
    private BaseResponse toProcess() throws IOException {
        // Request
        HttpClient httpclient = HttpClients.createDefault();


        // Response
        HttpResponse response = httpclient.execute(request);
        HttpEntity entity = response.getEntity();
        String dataResponse = ServiceUtils.getDataResponse(entity);
        logger.info("--Response Server:" + dataResponse);
        // JSON
        BaseResponse responseResult = new BaseResponse();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ||
                response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
            responseResult.setSuccess(true);
            if (classReturn != null) {
                responseResult.setData(convertObject(dataResponse, classReturn));
            }
        }else {
            ResponseErrorWrap responseErrorWrap = JsonUtil.convertObject(dataResponse, ResponseErrorWrap.class);
            responseResult.setSuccess(false);
            if (responseErrorWrap!= null && responseErrorWrap.getError() != null) {
                responseResult.setErrorCode(responseErrorWrap.getError().getCode());
                responseResult.setMessage(responseErrorWrap.getError().getMessage());
            }else{
                responseResult.setMessage(dataResponse);
            }
        }

        logger.info("RESPONSE: " + JsonUtil.toJson(responseResult));

        return responseResult;
    }

    private<T> Object convertObject(String dataResponse, Class<T> classReturn){
        switch (typeJson) {
            case JsonUtil.NORMAl_PARSER:
                return JsonUtil.convertObject(dataResponse, classReturn);
            case JsonUtil.TIME_PARSER:
                return JsonUtil.convertObject(dataResponse, classReturn, IContanst.API_COGN_MICROSOFT_PER_GROUP_FORMAT_TIME);
            case JsonUtil.LIST_PARSER:
                return JsonUtil.convertListObject(dataResponse, classReturn);
            case JsonUtil.MAP_PARSER:
                return JsonUtil.convertMapObject(dataResponse, classReturn);
        }
        return null;
    }



}
