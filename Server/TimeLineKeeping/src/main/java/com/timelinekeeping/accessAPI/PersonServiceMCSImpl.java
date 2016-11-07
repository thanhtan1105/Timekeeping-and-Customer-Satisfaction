package com.timelinekeeping.accessAPI;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.modelMCS.PersonInformation;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/11/2016.
 */
@Service
@Component
public class PersonServiceMCSImpl {

    private Logger logger = LogManager.getLogger(PersonServiceMCSImpl.class);


    /**
     * root path
     **/
    private String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url")
            + AppConfigKeys.getInstance().getApiPropertyValue("api.person.group");

    /**
     * create person in MCS
     *
     * @param name             person
     * @param description
     * @return @{@link BaseResponse}
     * @apiNote https://dev.projectoxford.ai/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f3039523c
     * <p>
     * url: https://api.projectoxford.ai/face/v1.0/persongroups/{personGroupId}/persons
     * entity: (name, description)
     * typeJsonReturn @{@link com.timelinekeeping.util.JsonUtil}
     * return Class: @{@link com.fasterxml.jackson.core.type.TypeReference}
     * <p>
     * 9-11-2016
     * @author hientq
     */
    public BaseResponse createPerson(String departmentCode, String name, String description) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlChild = AppConfigKeys.getInstance().getApiPropertyValue("api.person.addition");
            String url = rootPath + String.format("/%s", departmentCode) + urlChild;

            /*** url*/
            /** entity*/
            Map<String, String> entity = new HashMap<String, String>();
            entity.put("name", name);
            entity.put("userData", description);
            String jsonEntity = JsonUtil.toJson(entity);

            return HTTPClientUtil.getInstanceFace().toPost(url, new StringEntity(jsonEntity, StandardCharsets.UTF_8), JsonUtil.MAP_PARSER, String.class);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


    /**
     * add face into person by pe in MCS
     *
     * @param persongroupId group of  person
     * @param personId      person of face
     * @param urlImg        url to img
     * @return @{@link BaseResponse}
     * @apiNote https://dev.projectoxford.ai/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f3039523b
     * <p>
     * url: https://api.projectoxford.ai/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces[?userData][&targetFace]s
     * entity: (url)
     * typeJsonReturn @{@link com.timelinekeeping.util.JsonUtil}
     * return Class: @{@link Map<String, String>}
     * <p>
     * 9-11-2016
     * @author hientq
     */
    public BaseResponse addFaceUrl(String persongroupId, String personId, String urlImg) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlPerson = AppConfigKeys.getInstance().getApiPropertyValue("api.person.addition");
            String urlPersistence = AppConfigKeys.getInstance().getApiPropertyValue("api.person.add.face.addition");
            String url = rootPath + String.format("/%s", persongroupId) + urlPerson + String.format("/%s", personId) + urlPersistence;

            /*** url -> {url}*/

            /** entity*/
            Map<String, String> mapEntity = new HashMap<>();
            mapEntity.put("url", urlImg);
            String jsonEntity = JsonUtil.toJson(mapEntity);

            /** type Response JSON Map <String, String>*/

            /** Class return  Map<String,String>**/


            return HTTPClientUtil.getInstanceFace().toPost(url, new StringEntity(jsonEntity, StandardCharsets.UTF_8), JsonUtil.MAP_PARSER, String.class);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public BaseResponse addFaceImg(String persongroupId, String personId, InputStream imgStream) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_MCS+ Thread.currentThread().getStackTrace()[1].getMethodName());
            //STORE FILE
            String urlPerson = AppConfigKeys.getInstance().getApiPropertyValue("api.person.addition");
            String urlPersistence = AppConfigKeys.getInstance().getApiPropertyValue("api.person.add.face.addition");
            String url = rootPath + String.format("/%s", persongroupId) + urlPerson + String.format("/%s", personId) + urlPersistence;

            /*** url -> {url}*/

            /** entity*/
            byte[] byteImg = IOUtils.toByteArray(imgStream);

            /** type Response JSON List*/

            /** Class return **/


            return HTTPClientUtil.getInstanceFace().toPostOct(url, new ByteArrayEntity(byteImg), JsonUtil.MAP_PARSER, String.class);
        } finally {
            logger.info(IContanst.END_METHOD_MCS+ Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    /**
     * list Person in Person Groups
     *
     * @param groupId group to add person
     * @return @{@link BaseResponse}
     * @apiNote https://dev.projectoxford.ai/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395241
     * <p>
     * url: https://api.projectoxford.ai/face/v1.0/persongroups/{personGroupId}/persons
     * entity: (name, description)
     * typeJsonReturn @{@link com.timelinekeeping.util.JsonUtil}
     * return Class: @{@link com.fasterxml.jackson.core.type.TypeReference}
     * <p>
     * 9-11-2016
     * @author hientq
     */
    public BaseResponse listPersonInGroup(String groupId) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_MCS+ Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlChild = AppConfigKeys.getInstance().getApiPropertyValue("api.person.addition");
            String url = rootPath + String.format("/%s", groupId) + urlChild;

            /*** url*/
            logger.info("-- url: " + url);
            /** entity*/

            return HTTPClientUtil.getInstanceFace().toGet(url, JsonUtil.LIST_PARSER, PersonInformation.class);
        } finally {
            logger.info(IContanst.END_METHOD_MCS+ Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    /**
     * list Person in Person Groups
     *
     * @param personId person to delete
     * @return @{@link BaseResponse}
     * @apiNote https://dev.projectoxford.ai/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395241
     * <p>
     * url: https://api.projectoxford.ai/face/v1.0/persongroups/{personGroupId}/persons
     * entity: (name, description)
     * typeJsonReturn @{@link com.timelinekeeping.util.JsonUtil}
     * return Class: @{@link com.fasterxml.jackson.core.type.TypeReference}
     * <p>
     * 9-11-2016
     * @author hientq
     */
    public BaseResponse deletePerson(String groupId, String personId) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_MCS+ Thread.currentThread().getStackTrace()[1].getMethodName());
            String urlChild = AppConfigKeys.getInstance().getApiPropertyValue("api.person.addition");
            String url = rootPath + String.format("/%s", groupId) + urlChild;

            /*** url*/
            logger.info("-- url: " + url);
            /** entity*/

            return HTTPClientUtil.getInstanceFace().toGet(url, JsonUtil.LIST_PARSER, PersonInformation.class);
        } finally {
            logger.info(IContanst.END_METHOD_MCS+ Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }



//    public static void main(String[] args) {
//        try {
//            PersonServiceMCSImpl personServiceMCS = new PersonServiceMCSImpl();
//            System.out.println(JsonUtil.toJson(personServiceMCS.listPersonInGroup("humanresource")));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


}
