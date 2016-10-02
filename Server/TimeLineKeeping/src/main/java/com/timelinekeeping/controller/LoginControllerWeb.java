package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Controller
@RequestMapping("/login")
public class LoginControllerWeb {

    private Logger logger = Logger.getLogger(AccountControllerWeb.class);

    @Autowired
    AccountServiceImpl accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginView() {
        return IViewConst.LOGIN_VIEW;
    }

    @RequestMapping(value = "/processing", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        logger.info("[Controller- Login] BEGIN");
        logger.info("[Controller- Login] username: " + username);
        logger.info("[Controller- Login] password: " + password);

        String url = IViewConst.INVALID_VIEW;
        AccountModel accountModel = accountService.login(username, password);
        if (accountModel != null) {
            url = "redirect:/employee/attendance/";
            session.setAttribute("UserSession", accountModel);
            String role = accountModel.getRole().getName().toUpperCase();
            if ("ADMIN".equals(role)) {
                url = "redirect:/admin/departments/";
            } else if ("MANAGER".equals(role)) {
                url = "redirect:/manager/check_in/";
            }
        }
        logger.info("[Controller- Login] END");
        return url;
    }
}
