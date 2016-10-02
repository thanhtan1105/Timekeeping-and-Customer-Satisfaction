package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_PAGE;
import com.timelinekeeping.constant.I_SESSION;
import com.timelinekeeping.model.AccountAuthen;
import com.timelinekeeping.service.serviceImplement.AuthenServiceIml;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Controller
@RequestMapping("/")
public class LoginControllerWeb {


    @Autowired
    private AuthenServiceIml authenService;

    private Logger logger = Logger.getLogger(AccountControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginView(HttpSession session) {
        Object objAccount = session.getAttribute(I_SESSION.ACCOUNT);
        if (objAccount != null && (objAccount instanceof AccountAuthen)) {
            return ((AccountAuthen) objAccount).getRole().getAuthenPage();
        } else {
            return I_PAGE.LOGIN;
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(value = "r", required = false) String redirectPath,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model, HttpSession session) {

        try {

            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
                model.addAttribute("r", redirectPath);
                model.addAttribute(I_SESSION.A_MESSAGE, ERROR.AUTHEN_USERNAME_PASSWORD_EMPTY);
                return I_PAGE.RE + I_PAGE.LOGIN;
            }

            AccountAuthen account = authenService.author(username, password);
            if (account == null) {
                model.addAttribute("r", redirectPath);
                model.addAttribute(I_SESSION.A_MESSAGE, ERROR.AUTHEN_USERNAME_PASSWORD_NO_CORRECT);
                return I_PAGE.RE + I_PAGE.LOGIN;
            }

            //set session
            session.setAttribute(I_SESSION.ACCOUNT, account);

            //get redirect
            String redirect;
            if (StringUtils.isNotEmpty(redirectPath) && !"/".equals(redirectPath)) {
                redirect = I_PAGE.RE + redirectPath;
            } else {
                redirect = I_PAGE.RE + account.getRole().getAuthenPage();
            }
            return redirect;

        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }
}
