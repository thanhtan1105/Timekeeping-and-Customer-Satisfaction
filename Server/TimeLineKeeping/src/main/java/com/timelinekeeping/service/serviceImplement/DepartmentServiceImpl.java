package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.DepartmentModel;
import com.timelinekeeping.repository.DepartmentRepo;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lethanhtan on 9/14/16.
 */

@Service
public class DepartmentServiceImpl {

    @Autowired(required = true)
    private DepartmentRepo repo;

    private Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);

    public List<DepartmentEntity> findByName(String name) {
        return repo.findByName(name);
    }

    public DepartmentEntity findBy(long id) {
        return repo.findOne(id);
    }

    public BaseResponse create(DepartmentEntity model) throws IOException, URISyntaxException {
        BaseResponse baseResponse = new BaseResponse();
        if (isExist(model.getName())) {
            baseResponse.setSuccess(false);
            baseResponse.setMessage("Person group " + model.getName() + " already exists.");
        } else {
            PersonGroupServiceMCSImpl groupService = new PersonGroupServiceMCSImpl();
            baseResponse = groupService.create(model.getCode(), model.getName(), model.getDescription());
            if (baseResponse.isSuccess()) {
                repo.saveAndFlush(model);
            }
        }
        return baseResponse;
    }

    public boolean isExist(String name) {
        return repo.isExist(name).size() > 0;
    }

    public BaseResponse findAll(int page, int size) {
        BaseResponse baseResponse = new BaseResponse();
        Page<DepartmentEntity> departments = repo.findAll(new PageRequest(page, size));
        baseResponse.setSuccess(true);
        baseResponse.setData(departments);
        return baseResponse;
    }

    public Page<DepartmentEntity> searchDepartment(String code, String name, Integer page, Integer size){
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            Page<DepartmentEntity> departmentEntities = repo.search(code, name, new PageRequest(page, size));
            logger.info("-- Result: " + JsonUtil.toJson(departmentEntities));
            return departmentEntities;
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }


}
