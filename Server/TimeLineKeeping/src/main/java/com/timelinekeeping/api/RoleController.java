package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.RoleModel;
import com.timelinekeeping.service.serviceImplement.RoleServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by TrungNN on 11/8/2016.
 */
@RestController
@RequestMapping(I_URI.API_ROLE)
public class RoleController {

    private Logger logger = Logger.getLogger(RoleController.class);

    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping(value = {I_URI.API_LIST_ROLE}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse listDepartment() {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<RoleModel> roles = roleService.listAll();
            if (roles != null) {
                return new BaseResponse(true, roles);
            } else {
                return new BaseResponse(false);
            }
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        }
    }
}
