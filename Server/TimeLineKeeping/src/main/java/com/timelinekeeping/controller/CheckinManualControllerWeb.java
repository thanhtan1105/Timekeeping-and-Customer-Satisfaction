package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TrungNN on 9/24/2016.
 */
@Controller
@RequestMapping("/manager/check_in")
public class CheckinManualControllerWeb {

    private Logger logger = Logger.getLogger(CheckinManualControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadCheckinManaulView(Model model) {
        logger.info("[Controller- Load Check-in Manual View] BEGIN");
        return ViewConst.CHECK_IN_MANUAL_VIEW;
    }
}
