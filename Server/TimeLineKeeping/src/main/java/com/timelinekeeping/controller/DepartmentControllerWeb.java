package com.timelinekeeping.controller;

import com.timelinekeeping.api.DepartmentController;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by TrungNN on 9/17/2016.
 */
@Controller
@RequestMapping("/admin/departments")
public class DepartmentControllerWeb {

    private Logger logger = Logger.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentServiceImpl departmentService;

    @RequestMapping("/")
    public String loadManagementDepartmentView(Model model) {
        int page = 0;
        int size = 1000;

        BaseResponse response = departmentService.findAll(page, size);
        Page<DepartmentEntity> pageDepartment = (Page<DepartmentEntity>) response.getData();

        // List of departments
        model.addAttribute("ListDepartments", pageDepartment.getContent());

        return "/views/admin/management_depart/management_depart";
    }

    @RequestMapping(value = "/addDepartment", method = RequestMethod.GET)
    public String loadAddDepartmentView() {
        return "/views/admin/management_depart/add_depart";
    }

    @RequestMapping(value = "/addDepartmentProcessing", method = RequestMethod.POST)
    public String addDepartment(@RequestParam("departmentCode") String departmentCode,
                                @RequestParam("departmentName") String departmentName,
                                @RequestParam("description") String description,
                                Model model) {
        logger.info("[Add Department] BEGIN");
        logger.info("[Add Department] department code: " + departmentCode);
        logger.info("[Add Department] department name: " + departmentName);
        logger.info("[Add Department] description: " + description);
        boolean active = true;
        DepartmentEntity departmentEntity
                = new DepartmentEntity(departmentCode, departmentName, description, active);

        boolean success = false;
        String url = "/views/admin/management_depart/add_depart";
        try {
            BaseResponse response = departmentService.create(departmentEntity);
            success = response.isSuccess();
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
}
