package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TrungNN on 9/22/2016.
 */
@Controller
@RequestMapping("/manager/reminders")
public class ReminderControllerWeb {

    private Logger logger = Logger.getLogger(ReminderControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadManagementReminderView() {
        logger.info("[Controller- Load Management Reminder View] BEGIN");
        return ViewConst.MANAGEMENT_REMINDER_VIEW;
    }

    @RequestMapping(value = "/addReminder", method = RequestMethod.GET)
    public String loadAddReminderView() {
        logger.info("[Controller- Load Add Reminder View] BEGIN");
        return ViewConst.ADD_REMINDER_VIEW;
    }
}
