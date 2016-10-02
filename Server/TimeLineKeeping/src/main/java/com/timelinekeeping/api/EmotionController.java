package com.timelinekeeping.api;


import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.EmotionCustomerResponse;
import com.timelinekeeping.model.Pair;
import com.timelinekeeping.repository.CustomerServiceRepo;
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
//
//    @RequestMapping(value = {I_URI.API_EMOTION_RECOGNIZE}, method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse recognize(@RequestParam("img") MultipartFile imgFile,
//                                  @RequestParam("employeeId") long employeeId,
//                                  @RequestParam("isFirstTime") boolean isFirstTime) {
//        try {
//            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
//            BaseResponse response;
//            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
//                response = new BaseResponse(true);
//                response.setData(emotionService.save(imgFile.getInputStream(), employeeId, isFirstTime));
////                response = emotionServiceMCS.recognize(imgFile.getInputStream());
//            } else {
//                response = new BaseResponse();
//                response.setSuccess(false);
//                response.setMessage("File not image format.");
//            }
//            return response;
//        } catch (Exception e) {
//            logger.error(e);
//            return new BaseResponse(e);
//        } finally {
//            logger.info(IContanst.END_METHOD_CONTROLLER);
//        }
//    }
//
//    @RequestMapping(value = {I_URI.API_EMOTION_ANALYZE}, method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse analyseEmotion(@RequestParam("id") Long id) {
//        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
//        BaseResponse baseResponse = emotionService.analyseEmotion(id);
//        logger.info(IContanst.END_METHOD_CONTROLLER);
//        return baseResponse;
//    }
//
//    @RequestMapping(value = {I_URI.API_EMOTION_GET_EMOTION}, method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse getCustomerEmotion(@RequestParam("image") MultipartFile imgFile,
//                                           @RequestParam("employeeId") Long employeeId,
//                                           @RequestParam("isFirstTime") Boolean isFirstTime) {
//        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
//        BaseResponse response;
//        try {
//            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
//                response = new BaseResponse(true);
//                response.setData(emotionService.getCustomerEmotion(imgFile.getInputStream()));
//            } else {
//                response = new BaseResponse();
//                response.setSuccess(false);
//                response.setMessage("File not image format.");
//            }
//            return response;
//        } catch (Exception e) {
//            logger.error(e);
//            return new BaseResponse(false, e.getMessage());
//        } finally {
//            logger.info(IContanst.END_METHOD_CONTROLLER);
//        }
//    }


    @RequestMapping(value = {I_URI.API_EMOTION_BEGIN_TRANSACTION}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse beginTransaction(@RequestParam("image") MultipartFile imgFile,
                                         @RequestParam("employeeId") String employeeId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
                Pair<EmotionCustomerResponse, String> result = emotionService.beginTransaction(imgFile.getInputStream(), Long.parseLong(employeeId));
                if (result != null && result.getKey() != null) {
                    response = new BaseResponse(true, result.getKey());
                } else {
                    response = new BaseResponse(false, result.getValue());
                }
            } else {
                response = new BaseResponse(false, "File not image format.");
            }
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_PROCESS_TRANSACTION}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse processTransaction(@RequestParam("image") MultipartFile imgFile,
                                           @RequestParam("customerCode") String customerCode) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
                response = new BaseResponse(emotionService.processTransaction(imgFile.getInputStream(), customerCode));
            } else {
                response = new BaseResponse(false, "File not image format.");
            }
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_END_TRANSACTION}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse endTransaction(@RequestParam("customerCode") String customerCode) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            return new BaseResponse(emotionService.endTransaction(customerCode));
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }
}
