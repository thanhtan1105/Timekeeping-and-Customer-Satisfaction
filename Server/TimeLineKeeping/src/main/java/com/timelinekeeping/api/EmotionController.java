package com.timelinekeeping.api;


import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.entity.CustomerServiceEntity;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 9/12/2016.
 */
@RestController
@RequestMapping(I_URI.API_EMOTION)
public class EmotionController {

    private Logger logger = LogManager.getLogger(EmotionController.class);

    @Autowired
    private EmotionServiceImpl emotionService;

    @RequestMapping(value = {I_URI.API_EMOTION_BEGIN_TRANSACTION}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse beginTransaction(@RequestParam("image") MultipartFile imgFile,
                                         @RequestParam("employeeId") Long employeeId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            if (ValidateUtil.isImageFile(imgFile.getInputStream())) {
                Pair<EmotionCustomerResponse, String> result = emotionService.beginTransaction(imgFile.getInputStream(), employeeId);
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
                boolean result = emotionService.processTransaction(imgFile.getInputStream(), customerCode);
                response = new BaseResponse(true);
                Map<String, Boolean> mapResult = new HashMap<>();
                mapResult.put("transaction", result);
                response.setData(mapResult);
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
        BaseResponse response;
        try {
            boolean result = emotionService.endTransaction(customerCode);
            response = new BaseResponse(true);
            Map<String, Boolean> mapResult = new HashMap<String, Boolean>();
            mapResult.put("transaction", result);
            response.setData(mapResult);
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    /**
     * @author TrungNN
     * Web employee: start transaction
     */
    @RequestMapping(value = {"/employee/customer_emotion/begin_transaction"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse beginTransactionWeb(@RequestParam("employeeId") Long employeeId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " employeeId: " + employeeId);
        BaseResponse response;
        try {
            CustomerServiceEntity customerServiceEntity = emotionService.beginTransactionWeb(employeeId);
            if (customerServiceEntity != null) {
                String customerCode = customerServiceEntity.getCustomerCode();
                response = new BaseResponse(true, customerCode);
            } else {
                response = new BaseResponse(false, "AccountId does not exist");
            }
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    /**
     * @author TrungNN
     * Web employee: get 1st emotion
     */
    @RequestMapping(value = {"/employee/customer_emotion/get_emotion"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse getFirstEmotionWeb(@RequestParam("customerCode") String customerCode) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " customer code: " + customerCode);
        BaseResponse response;
        try {
            EmotionCustomerWebResponse emotionCustomerWebResponse = emotionService.getFirstEmotionWeb(customerCode);
            if (emotionCustomerWebResponse != null) {
                logger.info("[API- Get First Customer Emotion Web] customer emotion: not null");
                logger.info("[API- Get First Customer Emotion Web] age: " + emotionCustomerWebResponse.getAge());
                response = new BaseResponse(true, emotionCustomerWebResponse);
            } else {
                logger.info("[API- Get First Customer Emotion Web] customer emotion: null");
                response = new BaseResponse(false);
            }
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    /**
     * @author TrungNN
     * Web employee: end transaction
     */
    @RequestMapping(value = {"/employee/customer_emotion/end_transaction"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse endTransactionWeb(@RequestParam("customerCode") String customerCode) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " customerCode: " + customerCode);
        BaseResponse response;
        try {
            boolean result = emotionService.endTransactionWeb(customerCode);
            logger.info("[API- End Transaction Web] result: " + result);
            if (result) {
                response = new BaseResponse(true);
            } else {
                response = new BaseResponse(false);
            }
            return response;
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_REPORT}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse reportEmotion(@RequestParam("year") Integer year,
                                      @RequestParam("month") Integer month,
                                      @RequestParam(value = "day", defaultValue = IContanst.DEFAULT_INT) Integer day,
                                      @RequestParam("managerId") Long managerId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            CustomerServiceReport customerServiceReport = emotionService.reportCustomerService(year, month, day, managerId);
            if (customerServiceReport != null) {
                return new BaseResponse(true, customerServiceReport);
            }else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }
}
