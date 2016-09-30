package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TrungNN on 9/30/2016.
 */
@Controller
@RequestMapping("/manager/timekeeping")
public class TimekeepingControllerWeb {

    private Logger logger = Logger.getLogger(TimekeepingControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadTimekeepingView(Model model) {
        logger.info("[Controller- Load Timekeeping View] BEGIN");

        return ViewConst.TIME_KEEPING_VIEW;
    }
}
