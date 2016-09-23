package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import com.timelinekeeping.model.ReminderModel;
import com.timelinekeeping.service.serviceImplement.ReminderServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by TrungNN on 9/22/2016.
 */
@Controller
@RequestMapping("/manager/reminders")
public class ReminderControllerWeb {

    private Logger logger = Logger.getLogger(ReminderControllerWeb.class);

    @Autowired
    private ReminderServiceImpl reminderService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadManagementReminderView(Model model) {
        logger.info("[Controller- Load Management Reminder View] BEGIN");

        Long mangerId = Long.parseLong("1");
        int page = 0;
        int size = 1000;
        // Get all reminders by managerId
        Page<ReminderModel> pageReminder = reminderService.listByEmployee(mangerId, page, size);
        List<ReminderModel> reminderModels = pageReminder.getContent();
        logger.info("[Controller- Load Management Reminder View] [Size] list of reminders: " + reminderModels.size());

        model.addAttribute("ListReminders", reminderModels);

        logger.info("[Controller- Load Management Reminder View] END");

        return ViewConst.MANAGEMENT_REMINDER_VIEW;
    }

    @RequestMapping(value = "/addReminder", method = RequestMethod.GET)
    public String loadAddReminderView(Model model) {
        logger.info("[Controller- Load Add Reminder View] BEGIN");
        return ViewConst.ADD_REMINDER_VIEW;
    }
}
