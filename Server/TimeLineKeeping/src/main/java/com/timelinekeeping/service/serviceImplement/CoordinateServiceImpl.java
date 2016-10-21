package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.CoordinateEntity;
import com.timelinekeeping.model.CoordinateModel;
import com.timelinekeeping.repository.CoordinateRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lethanhtan on 10/20/16.
 */

@Component
@Service
public class CoordinateServiceImpl {

    @Autowired
    private CoordinateRepo coordinateRepo;

    private Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    public Page<CoordinateModel> listAll(int page, int size) {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        // paging
        Pageable pageable = new PageRequest(page, size);

        //repo db
        Page<CoordinateEntity> entityPage = coordinateRepo.findAll(pageable);

        //covert list
        List<CoordinateModel> coordinateModels = entityPage.getContent().stream().map(CoordinateModel::new).collect(Collectors.toList());
        Page<CoordinateModel> returnPage = new PageImpl<>(coordinateModels, pageable, entityPage.getTotalElements());
        logger.info("Entity result:" + JsonUtil.toJson(returnPage));
        return returnPage;
    }
}
