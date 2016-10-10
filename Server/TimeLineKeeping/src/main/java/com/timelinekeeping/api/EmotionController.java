package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.EmotionCustomerResponse;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
                                   HttpSession session){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("accountId = '%s' ", accountId));
            String customerCode = (String) session.getAttribute(I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + accountId);
            if (ValidateUtil.isEmpty(customerCode)){
                return new BaseResponse(false);
            }

            EmotionCustomerResponse emotionCustomer =  emotionService.getEmotionCustomer(customerCode);
            if (emotionCustomer != null){
                return new BaseResponse(true, emotionCustomer);
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
