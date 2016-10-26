package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.common.BaseResponseG;
import com.timelinekeeping.model.DepartmentModel;
import com.timelinekeeping.model.DepartmentSelectModel;
import com.timelinekeeping.repository.DepartmentRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by lethanhtan on 9/14/16.
 */

@Service
public class DepartmentServiceImpl {

    @Autowired(required = true)
    private DepartmentRepo repo;

    @Autowired
    PersonGroupServiceMCSImpl groupServiceMCS;

    private Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);

    public BaseResponseG<DepartmentModel> create(DepartmentModel model) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            Integer record = repo.isExist(model.getCode());
            if (record > 0) {
                return new BaseResponseG<>(false, String.format(ERROR.DEPARTMENT_API_CREATE_DEPARTMENT_DOES_EXIST, model.getCode()));
            }
            BaseResponse responseGroup = groupServiceMCS.create(model.getCode(), model.getName(), model.getDescription());
            if (responseGroup.isSuccess()) {
                DepartmentEntity entityReturn = repo.saveAndFlush(new DepartmentEntity(model));
                return new BaseResponseG<>(true, new DepartmentModel(entityReturn));
            }
            return new BaseResponseG<>(false, ERROR.OTHER);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Page<DepartmentModel> findAll(int page, int size) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());

            //request
            PageRequest pageRequest = new PageRequest(page, size);

            //save DB
            Page<DepartmentEntity> pageEntity = repo.findAll(pageRequest);

            //Convert data
            List<DepartmentModel> departmentModels = pageEntity.getContent().stream().map(DepartmentModel::new).collect(Collectors.toList());
            Page<DepartmentModel> pageDepartment = new PageImpl<DepartmentModel>(departmentModels, pageRequest, pageEntity.getTotalElements());
            logger.info("[Find All] " + JsonUtil.toJson(pageDepartment));

            return pageDepartment;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public List<DepartmentSelectModel> findAll() {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<DepartmentEntity> departmentEntities = repo.findAll();
            if (departmentEntities != null && departmentEntities.size() > 0) {
                List<DepartmentSelectModel> departmentSelectModels
                        = departmentEntities.stream().map(DepartmentSelectModel::new).collect(Collectors.toList());
                logger.info("Response: " + JsonUtil.toJson(departmentSelectModels));
                return departmentSelectModels;
            }
            return null;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Page<DepartmentModel> searchDepartment(String code, String name, Integer page, Integer size) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            PageRequest pageRequest = new PageRequest(page, size);
            //call DB
            Page<DepartmentEntity> pageEntity = repo.search(code, name, pageRequest);

            //Convert ListD
            List<DepartmentModel> departmentModels = pageEntity.getContent().stream().map(DepartmentModel::new).collect(Collectors.toList());
            Page<DepartmentModel> pageResopne = new PageImpl<DepartmentModel>(departmentModels, pageRequest, pageEntity.getTotalElements());
            logger.info("-- Result: " + JsonUtil.toJson(pageResopne));

            return pageResopne;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public BaseResponse training(String departmentId) throws IOException, URISyntaxException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            DepartmentEntity departmentEntity = repo.findOne(Long.parseLong(departmentId));
            return groupServiceMCS.trainGroup(departmentEntity.getCode());
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public Boolean isExist(String code) {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            Integer count = repo.isExist(code);
            return count > 0;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }
}
