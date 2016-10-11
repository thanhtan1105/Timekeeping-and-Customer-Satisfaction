package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HienTQSE60896 on 10/10/2016.
 */
@RestController
@RequestMapping(I_URI.API_EMOTION)
public class EmotionController {

    private Logger logger = LogManager.getLogger(EmotionController.class);

    @Autowired
    private EmotionServiceImpl emotionService;


    @RequestMapping(value = I_URI.API_EMOTION_GET_EMOTION)
    @ResponseBody
    public BaseResponse getEmotion(@RequestParam(I_URI.PARAMETER_EMOTION_ACCOUNT_ID) Long accountId,
                                   HttpSession session) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("accountId = '%s' ", accountId));
            String customerCode = (String) session.getAttribute(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (ValidateUtil.isEmpty(customerCode)) {
                return new BaseResponse(false);
            }

            EmotionCustomerResponse emotionCustomer = emotionService.getEmotionCustomer(customerCode);
            if (emotionCustomer != null) {
                return new BaseResponse(true, emotionCustomer);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }


    @RequestMapping(value = I_URI.API_EMOTION_UPLOAD_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse uploadImage(@RequestParam(I_URI.PARAMETER_EMOTION_ACCOUNT_ID) Long accountId,
                                    @RequestParam("image") MultipartFile imageFile,
                                    HttpSession session) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("accountId = '%s' ", accountId));
            String customerCode = (String) session.getAttribute(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (ValidateUtil.isEmpty(customerCode)) {
                return new BaseResponse(false);
            }

            Boolean result = emotionService.uploadImage(imageFile.getInputStream(), customerCode);
            if (result != null && result) {
                return new BaseResponse(true, new Pair<>("uploadSuccess", result));
            } else {
                return new BaseResponse(false);
            }

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
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_EMOTION_REPORT_EMPLOYEE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse reportEmotion(@RequestParam("year") Integer year,
                                      @RequestParam("month") Integer month,
                                      @RequestParam("employeeId") Long employeeId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = null;
        try {
            EmployeeReportCustomerService customerServiceReport = emotionService.reportCustomerServiceEmployee(year, month, employeeId);
            if (customerServiceReport != null) {
                return new BaseResponse(true, customerServiceReport);
            } else {
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
