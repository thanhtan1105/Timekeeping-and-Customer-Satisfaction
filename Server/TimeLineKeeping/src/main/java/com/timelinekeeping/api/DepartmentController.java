package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.entity.Department;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;

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
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
            java.util.Date date= new java.util.Date();
            Department department = new Department(code, name, description, new Timestamp(date.getTime()), true);
            BaseResponse response = departmentService.create(department);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    @RequestMapping(value = {"/findAll"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse findAll(@RequestParam("start") int start,
                                @RequestParam("top") int top) {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = departmentService.findAll(start, top);
        logger.info(IContanst.END_METHOD_SERVICE);
        return response;
    }
}
