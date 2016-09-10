package com.timelinekeeping.api;

import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.FaceServiceMCS;
import com.timelinekeeping.service.serviceImplement.PersonGroupServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

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
    public BaseResponse detect(@RequestParam("url") String url) {
        try {
            File img = new File(url);
            BaseResponse response = null;
            if (img.exists()) {
                response = faceService.detech(new FileInputStream(img));
                logger.info("RESPONSE: " + JsonUtil.toJson(response));
            }else {
                response = new BaseResponse();
                response.setSuccess(false);
                response.setMessage("URL not exist.");
            }
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/detectimg"}, method = RequestMethod.POST)
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

}

