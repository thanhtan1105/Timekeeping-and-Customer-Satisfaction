package com.timelinekeeping.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lethanhtan on 9/7/16.
 */

@Controller
@RequestMapping("/persongroups")
public class PersonGroupControllerWeb {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    // list all
    @RequestMapping(value = "/select-group", method = RequestMethod.GET)
    public String loadSelectGroupPage() {
        return "/views/staff/training_image/select_group";
//        return "/views/staff/training_image/create_group";
    }
}
