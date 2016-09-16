package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by lethanhtan on 9/14/16.
 */

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    @Autowired
    private DepartmentServiceImpl departmentService;


    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestParam("code") String code,
                               @RequestParam("name") String name,
                               @RequestParam("description") String description) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            DepartmentEntity departmentEntity = new DepartmentEntity(code, name, description, true);
            BaseResponse response = departmentService.create(departmentEntity);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {"/findAll"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse findAll(@RequestParam("page") int page,
                                @RequestParam("size") int size) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = departmentService.findAll(page, size);
        logger.info(IContanst.END_METHOD_CONTROLLER);
        return response;
    }
}
