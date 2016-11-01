package com.timelinekeeping.controller;

import com.timelinekeeping.common.BaseResponseG;
import com.timelinekeeping.common.Pair;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import com.timelinekeeping.service.serviceImplement.RoleServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by TrungNN on 9/18/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_ADMIN_ACCOUNT)
public class AccountControllerWeb {

    private Logger logger = Logger.getLogger(AccountControllerWeb.class);

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private DepartmentServiceImpl departmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadManagementAccountView(Model model) {
        logger.info("[Controller- Load Management Account View] BEGIN");
        int page = 0;
        int size = 1000;

        Page<AccountModel> listAccounts = accountService.listAll(page, size);

        // set side-bar
        String sideBar = IContanst.SIDE_BAR_ADMIN_MANAGEMENT_ACC;

        // List of accounts
        model.addAttribute("ListAccounts", listAccounts);
        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info("[Controller- Load Management Account View] END");
        return "/views/admin/management_acc/management_acc";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    public String loadAddAccountView(Model model) {
        logger.info("[Controller- Load Add Account View] BEGIN");
        // Get all roles for selection
        List<RoleModel> roleEntities = roleService.listAll();
        if (roleEntities != null) {
            logger.info("[Controller- Load Add Account View] Size of list roles: "
                    + roleEntities.size());
        }

        // Get all departments for selection
        List<DepartmentSelectModel> departmentSelectModels = departmentService.findAll();
        if (roleEntities != null) {
            logger.info("[Controller- Load Add Account View] Size of list departments: "
                    + departmentSelectModels.size());
        }

        // set side-bar
        String sideBar = IContanst.SIDE_BAR_ADMIN_MANAGEMENT_ACC;

        model.addAttribute("ListRoles", roleEntities);
        model.addAttribute("ListDepartments", departmentSelectModels);
        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info("[Controller- Load Add Account View] END");
        return "/views/admin/management_acc/add_acc";
    }

    @RequestMapping(value = "/addAccountProcessing", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    public String addAccount(@RequestParam("username") String username,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("roleId") String roleId,
                             @RequestParam("departmentId") String departmentId,
                             Model model) {
        String url = "/views/admin/management_acc/add_acc";
        try {
            logger.info("[Controller- Add Account] BEGIN");
            logger.info("[Controller- Add Account] username: " + username);
            logger.info("[Controller- Add Account] fullName: " + fullName);
            logger.info("[Controller- Add Account] roleId: " + roleId);
            logger.info("[Controller- Add Account] departmentId: " + departmentId);
            AccountModifyModel account = new AccountModifyModel();
            account.setUsername(username);
            account.setFullname(fullName);
            account.setRoleId(ValidateUtil.parseNumber(roleId));
            account.setDepartmentId(ValidateUtil.parseNumber(departmentId));

            Pair<Boolean, String> response = accountService.create(account);
            boolean success = response.getKey();

            logger.info("[Controller- Add Account] success: " + success);
            if (success) {
                url = "redirect:/admin/accounts/";
            }
        } catch (Exception e) {
            //TODO redirect error
            logger.error(e);
        }

        model.addAttribute("username", username);
        model.addAttribute("fullName", fullName);
        model.addAttribute("roleId", roleId);
        model.addAttribute("departmentId", departmentId);

        // Get all roles for selection
        List<RoleModel> roleEntities = roleService.listAll();
        if (roleEntities != null) {
            logger.info("[Controller- Add Account] Size of list roles: "
                    + roleEntities.size());
        }
        // Get all departments for selection
        List<DepartmentSelectModel> departmentSelectModels = departmentService.findAll();
        if (roleEntities != null) {
            logger.info("[Controller- Add Account] Size of list departments: "
                    + departmentSelectModels.size());
        }

        model.addAttribute("ListRoles", roleEntities);
        model.addAttribute("ListDepartments", departmentSelectModels);
        logger.info("[Controller- Add Account] END");
        return url;
    }
}
