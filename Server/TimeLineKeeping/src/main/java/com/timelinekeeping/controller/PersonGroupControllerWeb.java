package com.timelinekeeping.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lethanhtan on 9/7/16.
 */

@Controller
@RequestMapping("/persongroups")
public class PersonGroupControllerWeb {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    // list all
    @RequestMapping("/select-group")
    public String loadSelectGroupPage() {
        return "views/staff/select_group";
    }
}
