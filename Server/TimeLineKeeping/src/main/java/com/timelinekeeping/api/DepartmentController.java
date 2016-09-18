package com.timelinekeeping.api;

import com.timelinekeeping.constant.EStatus;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.controller.PersonGroupControllerWeb;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.DepartmentModel;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            DepartmentEntity departmentEntity = new DepartmentEntity(code, name, description, EStatus.ACTIVE);
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
    public BaseResponse findAll(@RequestParam("start") int start,
                                @RequestParam("top") int top) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = departmentService.findAll(start, top);
        logger.info(IContanst.END_METHOD_CONTROLLER);
        return response;
    }

    @RequestMapping(value = {"/exist_code"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse checkExistCode(@RequestParam("code") String code) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        try {
            Boolean isExist = departmentService.isExist(code);
            Map<String, Boolean> map = new HashMap<>();
            map.put("exist", isExist);
            return new BaseResponse(true, map);
        } catch (Exception e){
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search(@RequestParam(name = "code", required = false) String code,
                               @RequestParam(name = "name", required = false) String name,
                               @RequestParam(name = "start", defaultValue = IContanst.PAGE_PAGE, required = false) Integer page,
                               @RequestParam(name = "top", defaultValue = IContanst.PAGE_SIZE, required = false) Integer size) {

        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Page<DepartmentEntity> departmentEntities = departmentService.searchDepartment(code, name, page, size);
            logger.info(JsonUtil.toJson(departmentEntities));
            return new BaseResponse(true, departmentEntities);
        } catch (Exception e){
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = "/completeCode", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search (@RequestParam(name = "code", required = false) String code){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            List<DepartmentModel> departmentModels = departmentService.completeCode(code);
            return new BaseResponse(true, departmentModels);
        } catch (Exception e){
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

}
