package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Created by HienTQSE60896 on 11/7/2016.
 */
public class HandlerController {

    Logger logger = LogManager.getLogger(HandlerController.class);

    public BaseResponse synchronize(){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());


            return new BaseResponse(true);
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }
}
