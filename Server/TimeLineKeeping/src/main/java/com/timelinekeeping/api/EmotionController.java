package com.timelinekeeping.api;


import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.modelMCS.RectangleImage;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 9/12/2016.
 */
@RestController
@RequestMapping(I_URI.API_EMOTION)
public class EmotionController {

    private Logger logger = LogManager.getLogger(EmotionController.class);

    @Autowired
    private EmotionServiceImpl emotionService;

    @RequestMapping(value = {I_URI.API_EMOTION_RECOGNIZE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse recognize(@RequestParam("img") MultipartFile imgFile,
                                  @RequestParam("employeeId") long employeeId,
                                  @RequestParam("isFirstTime") boolean isFirstTime) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response;
            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
                response = emotionService.save(imgFile.getInputStream(), employeeId, isFirstTime);
//                response = emotionServiceMCS.recognize(imgFile.getInputStream());
            } else {
                response = new BaseResponse();
                response.setSuccess(false);
                response.setMessage("File not image format.");
            }
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_ANALYZE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse analyseEmotion(@RequestParam("id") Long id) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse baseResponse = emotionService.analyseEmotion(id);
        logger.info(IContanst.END_METHOD_CONTROLLER);
        return baseResponse;
    }

    @RequestMapping(value = {I_URI.API_EMOTION_GET_EMOTION}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getCustomerEmotion(@RequestParam("image") MultipartFile imgFile,
                                           @RequestParam("employeeId") String employeeId,
                                           @RequestParam("isFirstTime") String isFirstTime) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response;
        try {
            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
                response = emotionService.getCustomerEmotion(imgFile.getInputStream(), Long.parseLong(employeeId), Boolean.parseBoolean(isFirstTime));
            } else {
                response = new BaseResponse();
                response.setSuccess(false);
                response.setMessage("File not image format.");
            }
            return response;
        } catch (IOException e) {
            logger.error(e);
            return new BaseResponse(e);
        } catch (URISyntaxException e) {
            logger.error(e);
            return new BaseResponse(e);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }
}
