package com.timelinekeeping.accessAPI;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.modelMCS.PersonGroup;
import com.timelinekeeping.modelMCS.PersonGroupTrainingStatus;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lethanhtan on 9/7/16.
 */
@Service
public class PersonGroupServiceMCSImpl {

    private Logger logger = LogManager.getLogger(PersonGroupServiceMCSImpl.class);

    private String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url")
            + AppConfigKeys.getInstance().getApiPropertyValue("api.person.group");


    public BaseResponse create(String groupId, String groupName, String groupData) throws URISyntaxException, IOException {

        try {
            logger.info(IContanst.BEGIN_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlString = String.format("%s/%s", rootPath, groupId);

            logger.info("url: " + urlString);

            /** entity*/
            Map<String, String> entity = new HashMap<String, String>();
            entity.put("name", groupName);
            entity.put("userData", groupData);
            String jsonEntity = JsonUtil.toJson(entity);


            return HTTPClientUtil.getInstanceFace().toPut(urlString, new StringEntity(jsonEntity, StandardCharsets.UTF_8));
        } finally {
            logger.info(IContanst.END_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public BaseResponse listAll(int start, int top) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlString = rootPath;
            URIBuilder builder = new URIBuilder(urlString)
                    .addParameter("start", String.valueOf(start))
                    .addParameter("top", String.valueOf(top));

            logger.info("url: " + builder.build());
            return HTTPClientUtil.getInstanceFace().toGet(builder.build(), JsonUtil.LIST_PARSER, PersonGroup.class);
        } finally {
            logger.info(IContanst.END_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public BaseResponse trainGroup(String personGroupId) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlString = rootPath;


            String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.person.addition");
            String url = String.format("%s/%s/%s", urlString, personGroupId, urlAddition);

            logger.info("url: " + url);
            return HTTPClientUtil.getInstanceFace().toPost(url);
        } finally {
            logger.info(IContanst.END_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    public BaseResponse trainPersonGroupStatus(String personGroupId) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlString = rootPath;
            String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.status.addition");
            String url = String.format("%s/%s/%s", urlString, personGroupId, urlAddition);

            logger.info("url: " + url);
            return HTTPClientUtil.getInstanceFace().toGet(url, JsonUtil.TIME_PARSER, PersonGroupTrainingStatus.class);
        } finally {
            logger.info(IContanst.END_METHOD_MCS + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}
