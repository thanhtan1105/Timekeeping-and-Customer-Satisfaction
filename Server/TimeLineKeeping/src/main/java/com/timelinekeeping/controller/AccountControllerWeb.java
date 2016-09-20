package com.timelinekeeping.controller;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.RoleEntity;
import com.timelinekeeping.model.AccountCreate;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.DepartmentSelectModel;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.service.serviceImplement.DepartmentServiceImpl;
import com.timelinekeeping.service.serviceImplement.RoleServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/admin/accounts")
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

        List<AccountModel> listAccounts= accountService.listAll(page, size);

        // List of accounts
        model.addAttribute("ListAccounts", listAccounts);
        logger.info("[Controller- Load Management Account View] END");

        return "/views/admin/management_acc/management_acc";
    }

    @RequestMapping(value = "/addAccount", method = RequestMethod.GET)
    public String loadAddAccountView(Model model) {
        logger.info("[Controller- Load Add Account View] BEGIN");
        // Get all roles for selection
        List<RoleEntity> roleEntities = roleService.listAll();
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

        model.addAttribute("ListRoles", roleEntities);
        model.addAttribute("ListDepartments", departmentSelectModels);
        logger.info("[Controller- Load Add Account View] END");

        return "/views/admin/management_acc/add_acc";
    }

    @RequestMapping(value = "/addAccountProcessing", method = RequestMethod.POST)
    public String addAccount(@RequestParam("username") String username,
                             @RequestParam("fullName") String fullName,
                             @RequestParam("roleId") String roleId,
                             @RequestParam("departmentId") String departmentId,
                             Model model) {
        logger.info("[Controller- Add Account] BEGIN");
        logger.info("[Controller- Add Account] username: " + username);
        logger.info("[Controller- Add Account] fullName: " + fullName);
        logger.info("[Controller- Add Account] roleId: " + roleId);
        logger.info("[Controller- Add Account] departmentId: " + departmentId);
        AccountCreate account = new AccountCreate();
        account.setUsername(username);
        account.setFullname(fullName);
        account.setRoleId(ValidateUtil.validateNumber(roleId));
        account.setDepartmentId(ValidateUtil.validateNumber(departmentId));

        BaseResponse response = accountService.create(account);
        boolean success = response.isSuccess();

        logger.info("[Controller- Add Account] success: " + success);
        String url = "/views/admin/management_acc/add_acc";
        if (success) {
            url = "redirect:/admin/accounts/";
        } else {
            model.addAttribute("username", username);
            model.addAttribute("fullName", fullName);
            model.addAttribute("roleId", roleId);
            model.addAttribute("departmentId", departmentId);

            // Get all roles for selection
            List<RoleEntity> roleEntities = roleService.listAll();
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
        }
        logger.info("[Controller- Add Account] END");
        return url;
    }
}
