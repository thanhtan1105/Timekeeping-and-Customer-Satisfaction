package com.timelinekeeping.api;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.BaseResponseG;
import com.timelinekeeping.model.DepartmentModel;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lethanhtan on 9/14/16.
 */

@RestController
@RequestMapping(I_URI.API_DEPARTMENT)
public class DepartmentController {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    @Autowired
    private DepartmentServiceImpl departmentService;

    @RequestMapping(value = {I_URI.API_CREATE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@ModelAttribute DepartmentModel department) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            BaseResponseG<DepartmentModel> response = departmentService.create(department);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response.toBaseResponse();

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_DEPARTMENT_TRAINING}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse training(@RequestParam("departmentId") String departmentId) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        try {
            BaseResponse response = departmentService.training(departmentId);
            return response;
        } catch (IOException e) {
            logger.error(e);
            return new BaseResponse(e);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_LIST}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse findAll(@RequestParam("start") int start,
                                @RequestParam("top") int top) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, departmentService.findAll(start, top));
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_DEPARTMENT_EXIST}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse checkExistCode(@RequestParam("code") String code) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        try {
            Boolean isExist = departmentService.isExist(code);
            Map<String, Boolean> map = new HashMap<>();
            map.put("exist", isExist);
            return new BaseResponse(true, map);
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = {I_URI.API_SEARCH}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search(@RequestParam(name = "code") String code,
                               @RequestParam(name = "name", required = false) String name,
                               @RequestParam(name = "start", defaultValue = IContanst.PAGE_PAGE, required = false) Integer page,
                               @RequestParam(name = "top", defaultValue = IContanst.PAGE_SIZE, required = false) Integer size) {

        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + " Code: " + code);
            Page<DepartmentModel> departmentEntities = departmentService.searchDepartment(code, name, page, size);
            logger.info(JsonUtil.toJson(departmentEntities));
            return new BaseResponse(true, departmentEntities);
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }


}
