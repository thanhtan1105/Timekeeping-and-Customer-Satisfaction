package com.timelinekeeping.api;

import com.timelinekeeping.accessAPI.EmotionServiceMCSImpl;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.UtilApps;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by HienTQSE60896 on 9/12/2016.
 */
@RestController
@RequestMapping("/api/emotion")
public class EmotionController {

    private Logger logger = LogManager.getLogger(EmotionController.class);

//    @Autowired
//    private EmotionServiceImpl emotionServiceMCS;
/*
    @RequestMapping(value = {"/recognize"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse recognize(@RequestParam("url") String urlImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response = emotionServiceMCS.recognize(urlImg);
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

<<<<<<< HEAD
//    @RequestMapping(value = {"/recognize"}, method = RequestMethod.POST)
//    @ResponseBody
//    public BaseResponse recognize(@RequestParam("url") String urlImg) {
//        try {
//            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
//            BaseResponse response = emotionServiceMCS.recognize(urlImg);
//            return response;
//        } catch (Exception e) {
//            logger.error(e);
//            return new BaseResponse(e);
//        } finally {
//            logger.info(IContanst.END_METHOD_CONTROLLER);
//        }
//    }

    @RequestMapping(value = {"/recognize_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse recognize(@RequestParam("img") MultipartFile imgFile,
                                  @RequestParam("employeeId") long employeeId,
                                  @RequestParam("isFirstTime") boolean isFirstTime) {
=======
    @RequestMapping(value = {"/recognize_img"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse recognize(@RequestParam("img") MultipartFile imgFile) {
>>>>>>> w2-hien-store_file
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse response;
            if (UtilApps.isImageFile(imgFile.getInputStream())) {
<<<<<<< HEAD
                response = emotionServiceMCS.save(imgFile.getInputStream(), employeeId, isFirstTime);
=======
                response = emotionServiceMCS.recognize(imgFile.getInputStream());
>>>>>>> w2-hien-store_file
            }else {
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
*/
}
