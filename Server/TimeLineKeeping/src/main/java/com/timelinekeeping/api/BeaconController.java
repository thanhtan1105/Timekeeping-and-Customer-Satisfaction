package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BeaconFindPathResponse;
import com.timelinekeeping.model.BeaconModel;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.service.algorithm.BeaconAlgorithm;
import com.timelinekeeping.service.serviceImplement.BeaconServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @RequestMapping(I_URI.API_BEACON_GET_ROOM_POINT)
    public List<CoordinateModel> getRoomPoint() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return beaconService.getRoomPoint();
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(I_URI.API_BEACON_GET_BEACON_POINT)
    public List<BeaconModel> getBeaconPoint() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return beaconService.getBeaconPoint();
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(I_URI.API_BEACON_GET_POINT)
    public List<CoordinateModel> getPoint() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return beaconService.getPoint();
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(I_URI.API_BEACON_FIND_PATH)
    public BaseResponse findMinPath(@RequestParam("from") Long beginVertex,
                                    @RequestParam("to") Long endVertex) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.debug(String.format("from = '%s', to= '%s' ", beginVertex, endVertex));
            BeaconFindPathResponse response = algorithm.findShortPath(beginVertex, endVertex);
            if (response != null) {
                return new BaseResponse(true, response);
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
