package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BeaconFindPathResponse;
import com.timelinekeeping.service.blackService.BeaconAlgorithm;
import com.timelinekeeping.service.serviceImplement.BeaconServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by HienTQSE60896 on 10/21/2016.
 */
@RestController
@RequestMapping(I_URI.API_BEACON)
public class BeaconController {


    private Logger logger = LogManager.getLogger(BeaconController.class);

    @Autowired
    private BeaconServiceImpl beaconService;

    @Autowired
    private BeaconAlgorithm algorithm;

    @RequestMapping(value = I_URI.API_BEACON_GET_BEACON_POINT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse getBeaconPoint() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(beaconService.getBeaconPoint());
            baseResponse.setSuccess(true);
            return baseResponse;
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }

    @RequestMapping(value = I_URI.API_BEACON_FIND_PATH, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public BaseResponse findMinPath(@RequestParam("from") String beginVertex,
                                    @RequestParam("to") String endVertex) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("from = '%s', to= '%s' ", beginVertex, endVertex));
            BeaconFindPathResponse response = algorithm.findShortPath(Long.parseLong(beginVertex), Long.parseLong(endVertex));
            if (response != null) {
                return new BaseResponse(true, response);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        }
    }


}
