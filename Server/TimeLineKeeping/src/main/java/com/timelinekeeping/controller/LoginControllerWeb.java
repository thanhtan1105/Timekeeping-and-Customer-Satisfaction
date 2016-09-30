package com.timelinekeeping.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lethanhtan on 9/29/16.
 */

@Controller
@RequestMapping("/")
public class LoginControllerWeb {

    private Logger logger = Logger.getLogger(AccountControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loginView() {
        return "/views/login/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        return "";
    }
}
