package com.timelinekeeping.controller;

import com.timelinekeeping.api.DepartmentController;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.*;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.model.DepartmentModel;
import com.timelinekeeping.model.DepartmentModifyModel;
import com.timelinekeeping.model.DepartmentSelectModel;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by TrungNN on 9/17/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_ADMIN_DEPARTMENT)
public class DepartmentControllerWeb {

    private Logger logger = Logger.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentServiceImpl departmentService;

    @RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadManagementDepartmentView(Model model) {
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_ADMIN_MANAGEMENT_DEPART;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        return "/views/admin/management_depart/management_depart";
    }

    @RequestMapping(value = "/addDepartment", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadAddDepartmentView(Model model) {
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_ADMIN_MANAGEMENT_DEPART;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        return "/views/admin/management_depart/add_depart";
    }

    @RequestMapping(value = "/addDepartmentProcessing", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addDepartment(@RequestParam("departmentCode") String departmentCode,
                                @RequestParam("departmentName") String departmentName,
                                @RequestParam("description") String description,
                                Model model) {
        logger.info("[Add Department] BEGIN");
        logger.info("[Add Department] department code: " + departmentCode);
        logger.info("[Add Department] department name: " + departmentName);
        logger.info("[Add Department] description: " + description);
        DepartmentModifyModel departmentEntity
                = new DepartmentModifyModel(departmentCode, departmentName, description);
//        departmentEntity.setActive(EStatus.ACTIVE);
//        departmentEntity.setStatus(ETrainStatus.NOT_STARTED);

        boolean success = false;
        String message = "";
        String url = "/views/admin/management_depart/add_depart";
        try {
                    Pair<Boolean, String> pair = departmentService.create(departmentEntity);
            success = pair.getKey();
            message = pair.getValue();
        } catch (IOException e) {
            logger.info("[Add Department] IOException: " + e.getMessage());
        } catch (URISyntaxException e) {
            logger.info("[Add Department] URISyntaxException: " + e.getMessage());
        }

        logger.info("[Add Department] success: " + success);
        if (success) {
            url = "redirect:/admin/departments/";
        } else {
            model.addAttribute("departmentCode", departmentCode);
            model.addAttribute("departmentName", departmentName);
            model.addAttribute("description", description);
        }
        logger.info("[Add Department] END");
        return url;
    }

    @RequestMapping(value = "/search2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String search(@RequestParam(value = "searchValue") String searchValue) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "[searchValue] " + searchValue);

        logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        return IViewConst.MANAGEMENT_DEPARTMENT_VIEW;
//        return "/views/admin/management_depart/add_depart";
    }
}
