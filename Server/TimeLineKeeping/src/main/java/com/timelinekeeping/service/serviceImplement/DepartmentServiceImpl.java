package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.accessAPI.PersonGroupServiceMCSImpl;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.repository.DepartmentRepo;
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
        return repo.isExist(name).size() > 0 ? true : false;
    }

    public BaseResponse findAll(int page, int size) {
        BaseResponse baseResponse = new BaseResponse();
        Page<DepartmentEntity> departments = repo.findAll(new PageRequest(page, size));
        baseResponse.setSuccess(true);
        baseResponse.setData(departments);
        return baseResponse;
    }


}
