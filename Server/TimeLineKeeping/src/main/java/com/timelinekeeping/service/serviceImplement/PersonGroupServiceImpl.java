package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.PersonGroup;
import com.timelinekeeping.model.PersonGroupTrainingStatus;
import com.timelinekeeping.service.PersonGroupsService;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

/**
 * Created by lethanhtan on 9/7/16.
 */
@Service
public class PersonGroupServiceImpl implements PersonGroupsService {

    private Logger logger = LogManager.getLogger(PersonGroupServiceImpl.class);

    private String key = AppConfigKeys.getInstance().getApiPropertyValue("ocp.apim.subscription.key");
    private String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url")
            + AppConfigKeys.getInstance().getApiPropertyValue("api.person.group");


    public BaseResponse create(String groupName, String groupData) throws URISyntaxException, IOException {
        String urlString = String.format("%s/%s", rootPath, groupName);

        JSONObject object = new JSONObject();
        object.put("name", groupName);
        object.put("userData", groupData);
        byte[] postData = object.toString().getBytes(StandardCharsets.UTF_8);
        return new HTTPClientUtil().toPut(urlString, new ByteArrayEntity(postData));
    }

    public BaseResponse listAll(int start, int top) throws URISyntaxException, IOException {
        String urlString = rootPath;
        URIBuilder builder = new URIBuilder(urlString)
                .addParameter("start", String.valueOf(start))
                .addParameter("top", String.valueOf(top));
        return new HTTPClientUtil().toGet(builder.build(), JsonUtil.LIST_PARSER, PersonGroup.class);
    }

    public BaseResponse trainGroup(String personGroupId) throws URISyntaxException, IOException {
        String urlString = rootPath;
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.person.addition");
        String url = String.format("%s/%s/%s", urlString, personGroupId, urlAddition);
        return new HTTPClientUtil().toPost(url);
    }

    public BaseResponse trainPersonGroupStatus(String personGroupId) throws URISyntaxException, IOException {
        String urlString = rootPath;
        String urlAddition = AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.status.addition");
        String url = String.format("%s/%s/%s", urlString, personGroupId, urlAddition);

        return new HTTPClientUtil().toPost(url, JsonUtil.TIME_PARSER, PersonGroupTrainingStatus.class);
    }
}
