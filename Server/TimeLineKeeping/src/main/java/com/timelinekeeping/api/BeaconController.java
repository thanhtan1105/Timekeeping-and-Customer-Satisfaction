package com.timelinekeeping.api;

import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BeaconModel;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.service.algorithm.BeaconAlgorithm;
import com.timelinekeeping.service.serviceImplement.BeaconServiceImpl;
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

    @Autowired
    private BeaconServiceImpl beaconService;

    @Autowired
    private BeaconAlgorithm algorithm;

    @RequestMapping(I_URI.API_BEACON_GET_BEACON_POINT)
    public List<BeaconModel> getBeaconPoint(){
        return beaconService.getBeaconPoint();
    }

    @RequestMapping(I_URI.API_BEACON_FIND_PATH)
    public List<CoordinateModel> findMinPath(@RequestParam("from") Long beginVertex,
                                             @RequestParam("to") Long endVertex){
        algorithm.findShortPath(beginVertex, endVertex);
        return null;
    }
}
