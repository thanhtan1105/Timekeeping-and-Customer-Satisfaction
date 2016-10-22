package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.entity.BeaconEntity;
import com.timelinekeeping.entity.CoordinateEntity;
import com.timelinekeeping.model.BeaconModel;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.repository.BeaconRepo;
import com.timelinekeeping.repository.CoordinateRepo;
import com.timelinekeeping.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by HienTQSE60896 on 10/21/2016.
 */
@Service
public class BeaconServiceImpl {


    @Autowired
    private CoordinateRepo coordinateRepo;

    @Autowired
    private BeaconRepo beaconRepo;


    public void findShortPath(CoordinateEntity beginPoint, CoordinateEntity entityPoint) {

    }

    public List<CoordinateModel> getRoomPoint() {
        List<CoordinateEntity> list = coordinateRepo.findAllPointRoom();
        if (ValidateUtil.isEmpty(list)) {
            return null;
        } else {
            return list.stream().map(CoordinateModel::new).collect(Collectors.toList());
        }
    }

    public List<BeaconModel> getBeaconPoint() {
        List<BeaconEntity> list = beaconRepo.findAll();
        if (ValidateUtil.isEmpty(list)) {
            return null;
        } else {
            return list.stream().map(BeaconModel::new).collect(Collectors.toList());
        }
    }

    public List<CoordinateModel> getPoint() {
        List<CoordinateEntity> list = coordinateRepo.findAll();
        if (ValidateUtil.isEmpty(list)) {
            return null;
        } else {
            return list.stream().map(CoordinateModel::new).collect(Collectors.toList());
        }
    }
}
