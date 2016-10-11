package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.RoleAuthen;
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
public class LoginControllerWeb {

    private Logger logger = Logger.getLogger(AccountControllerWeb.class);

    @Autowired
    AccountServiceImpl accountService;

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String loginView(HttpSession session) {
        AccountModel accountModel = (AccountModel) session.getAttribute(I_URI.SESSION_AUTHEN);
        if (accountModel != null) {
            return ((RoleAuthen) accountModel.getRole()).getRedirect();
        } else {
            return IViewConst.LOGIN_VIEW;
        }
    }

    @RequestMapping(value = "/login/processing", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session) {
        logger.info("[Controller- Login] BEGIN");
        logger.info("[Controller- Login] username: " + username);

        String url;
        AccountModel accountModel = accountService.login(username, password);
        if (accountModel != null) {
            session.setAttribute("UserSession", accountModel);
            url = ((RoleAuthen) accountModel.getRole()).getRedirect();
        } else {
            url = IViewConst.INVALID_VIEW;
        }
        logger.info("[Controller- Login] END");
        return url;
    }

    @RequestMapping(value = {"/logout", "/login/logout"}, method = RequestMethod.GET)
    public String logout(HttpSession session) {
        AccountModel accountModel = (AccountModel) session.getAttribute(I_URI.SESSION_AUTHEN);
        if (accountModel != null) {
            session.removeAttribute(I_URI.SESSION_AUTHEN);
        }
        return "redirect:/";
    }
    @RequestMapping(value = {"/login/permissionDenied"}, method = RequestMethod.GET)
    public String permissionDenied() {
        return "redirect:/login";
    }
}
