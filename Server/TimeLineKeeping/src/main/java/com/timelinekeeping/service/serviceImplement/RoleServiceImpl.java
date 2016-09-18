package com.timelinekeeping.service.serviceImplement;

import com.timelinekeeping.entity.RoleEntity;
import com.timelinekeeping.repository.RoleRepo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by TrungNN on 9/18/2016.
 */
@Component
@Service
public class RoleServiceImpl {

    private Logger logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepo roleRepo;

    public List<RoleEntity> listAll() {
        return roleRepo.findAll();
    }
}
