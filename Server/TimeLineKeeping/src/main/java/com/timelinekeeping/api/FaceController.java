package com.timelinekeeping.api;

import com.timelinekeeping.accessAPI.FaceServiceMCSImpl;
import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.modelAPI.FaceDetectRespone;
import com.timelinekeeping.modelAPI.PersonGroup;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.StoreFileUtils;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lethanhtan on 9/9/16.
 */

@RestController
@RequestMapping("/api/face")
public class FaceController {

    private Logger logger = Logger.getLogger(FaceController.class);

    @Autowired
    private FaceServiceMCSImpl faceService;

    @Autowired
    private PersonGroupServiceMCSImpl personGroupsServiceMCS;

    @RequestMapping(value = {"/detect"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detect(@RequestParam("url") String urlImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response = faceService.detect(urlImg);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/detect_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detectimg(@RequestParam("img") MultipartFile img) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            StoreFileUtils.storeFile("hhjfjf2dd", img.getInputStream());
            BaseResponse response = faceService.detect(img.getInputStream());
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {"/identify"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse identify(@RequestParam("groupId") String groupId,
                                 @RequestParam("faceId") List<String> faceIds) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response = faceService.identify(groupId, faceIds);
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/identifyImage"}, method = RequestMethod.POST)
    public BaseResponse identifyImage(@RequestParam("image") MultipartFile image) {
        try {
            // call function detect image
            BaseResponse detectFaceResponse = detectimg(image);
            List<FaceDetectRespone> faceDetectRespone = (ArrayList<FaceDetectRespone>) detectFaceResponse.getData();

            String faceId = faceDetectRespone.get(0).getFaceId();
            logger.info("FaceId: " + faceId);

            // call personGroup list
            BaseResponse personGroupListResponse = personGroupsServiceMCS.listAll(0, 1000);
            List<PersonGroup> personGroupList = (ArrayList<PersonGroup>) personGroupListResponse.getData();

            for (PersonGroup personGroup: personGroupList) {
                List<String> face = new ArrayList();
                face.add(faceId);
                BaseResponse identifyResponse = identify(personGroup.getPersonGroupId(), face);
                logger.info("identifyResponse " + identifyResponse);
            }


        } catch (Exception e) {
            logger.error(e);
        }

        return null;
    }

}

