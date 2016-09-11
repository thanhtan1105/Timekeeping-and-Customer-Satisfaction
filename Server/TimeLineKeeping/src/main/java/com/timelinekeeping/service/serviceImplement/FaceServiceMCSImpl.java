package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.FaceDetectRespone;
import com.timelinekeeping.model.ResponseErrorWrap;
import com.timelinekeeping.service.FaceServiceMCS;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ServiceUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
@Service
public class FaceServiceMCSImpl implements FaceServiceMCS {

    private Logger logger = LogManager.getLogger(PersonGroupServiceImpl.class);

    private String key = AppConfigKeys.getInstance().getApiPropertyValue("ocp.apim.subscription.key");
    private String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url");

    /** detect commit*/

    @Override
    public BaseResponse detech(InputStream imgStream) throws URISyntaxException, IOException {
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.face.detech");
        String url = rootPath + urlAddition;

        /*** url*/
        URIBuilder builder = new URIBuilder(url)
                .addParameter("returnFaceId","true")
                .addParameter("returnFaceLandmarks","false")
                .addParameter("returnFaceAttributes", "age,gender");

        /** entity*/
        byte[] bytes =IOUtils.toByteArray(imgStream);

        /** type Response JSON List*/

        /** Class return **/


        return new HTTPClientUtil().toPostOct(builder.build(),new ByteArrayEntity(bytes), JsonUtil.LIST_PARSER, FaceDetectRespone.class);

        /*
        // Request
        HttpClient httpclient = HttpClients.createDefault();

        HttpPost request = new HttpPost(uri);
//        request.setHeader("Content-Type", "application/json");
        request.setHeader("Content-Type", "application/octet-stream");
        request.setHeader("Ocp-Apim-Subscription-Key", key);

//

        // Response
        HttpResponse response = httpclient.execute(request);

        HttpEntity entity = response.getEntity();
        String dataResponse = ServiceUtils.getDataResponse(entity);

        // JSON
        BaseResponse reponseResult = new BaseResponse();
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            List<FaceDetectRespone> groupStatus = JsonUtil.convertListObject(dataResponse,
                     FaceDetectRespone.class);

            reponseResult.setSuccess(true);
            reponseResult.setData(groupStatus);
        }else {
            ResponseErrorWrap responseErrorWrap = JsonUtil.convertObject(dataResponse, ResponseErrorWrap.class);
            reponseResult.setSuccess(false);
            if (responseErrorWrap.getError() != null) {
                reponseResult.setErrorCode(responseErrorWrap.getError().getCode());
                reponseResult.setMessage(responseErrorWrap.getError().getMessage());
            }
        }
        return reponseResult;*/
    }
}
