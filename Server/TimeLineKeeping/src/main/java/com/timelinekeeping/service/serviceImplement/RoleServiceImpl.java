package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.RoleEntity;
import com.timelinekeeping.model.RoleModel;
import com.timelinekeeping.repository.RoleRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by TrungNN on 9/18/2016.
 */
@Component
@Service
public class RoleServiceImpl {

    private Logger logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepo roleRepo;

    public List<RoleModel> listAll() {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<RoleEntity> roleEntities = roleRepo.findAll();
            List<RoleModel> roleModels = roleEntities.stream().map(RoleModel::new).collect(Collectors.toList());
            logger.info("List All:" + JsonUtil.toJson(roleModels));
            return roleModels;
        }finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}
