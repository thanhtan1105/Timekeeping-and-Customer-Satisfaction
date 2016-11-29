package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.ConfigurationModel;
import com.timelinekeeping.model.HistoryModel;
import com.timelinekeeping.service.serviceImplement.HandlerServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by HienTQSE60896 on 11/7/2016.
 */
@RestController
@RequestMapping(I_URI.API_HANDLER)
public class HandlerController {

    Logger logger = LogManager.getLogger(HandlerController.class);
    @Autowired
    HandlerServiceImpl handlerService;

    @RequestMapping(value = I_URI.API_HANDLER_SYNCHONRINZE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse synchronize() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

            Boolean result = handlerService.synchronize();
            if (result == null) {
                return new BaseResponse(false);
            } else {
                return new BaseResponse(result);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_HANDLER_LIST_HISTORY, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse listHistory() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

            List<HistoryModel> result = handlerService.listHistory();
            if (result == null) {
                return new BaseResponse(false);
            } else {
                return new BaseResponse(true, result);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }


    @RequestMapping(value = I_URI.API_HANDLER_LIST_CONFIGURATION, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse listConfiguration() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

            ConfigurationModel result = handlerService.getConfiguration();
            logger.info("Result : " + JsonUtil.toJson(result));
            if (result == null) {
                return new BaseResponse(false);
            } else {
                return new BaseResponse(true, result);
            }

        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_HANDLER_UPDATE_CONFIGURATION, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse updateConfiguration(@ModelAttribute ConfigurationModel model) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(handlerService.updateConfiguration(model));
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

}
