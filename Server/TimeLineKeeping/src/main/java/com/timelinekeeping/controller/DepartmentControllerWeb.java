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

import java.util.List;

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
}
