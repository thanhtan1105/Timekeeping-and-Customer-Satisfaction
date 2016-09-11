package com.timelinekeeping.api;

import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.FaceServiceMCS;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by lethanhtan on 9/9/16.
 */

@RestController
@RequestMapping("/api/face")
public class FaceController {

    private Logger logger = Logger.getLogger(FaceController.class);

    @Autowired
    private FaceServiceMCS faceService;

    @RequestMapping(value = {"/detect"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detect(@RequestParam("url") String urlImg) {
        try {
            BaseResponse response = faceService.detech(urlImg);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/detect_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse detectimg(@RequestParam("img") MultipartFile img) {
        try {
            BaseResponse response = null;
            if (UtilApps.isImageFile(img.getInputStream())) {
                    response = faceService.detech(img.getInputStream());
                    logger.info("RESPONSE: " + JsonUtil.toJson(response));
            }else {
                response = new BaseResponse();
                response.setSuccess(false);
                response.setMessage("File not image format.");
            }
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/identify"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse identify(@RequestParam("groupId") String groupId,
                                 @RequestParam("faceId")List<String> faceIds) {
        try {
            BaseResponse response = faceService.identify(groupId,faceIds);
                logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

}

