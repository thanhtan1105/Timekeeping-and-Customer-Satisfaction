package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.BaseResponseG;
import com.timelinekeeping.model.ReminderModel;
import com.timelinekeeping.model.ReminderModifyModel;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.service.serviceImplement.ReminderServiceImpl;
import com.timelinekeeping.util.TimeUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadManagementReminderView(Model model, HttpSession session) {
        logger.info("[Controller- Load Management Reminder View] BEGIN");

        int page = 0;
        int size = 1000;
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            url = IViewConst.LOGIN_VIEW;
            String role = accountModel.getRole().getName().toUpperCase();
            // check is manager
            if ("MANAGER".equals(role)) {
                Long mangerId = accountModel.getId();

                // get all reminders by managerId
                Page<ReminderModel> pageReminder = reminderService.listByEmployee(mangerId, page, size);
                List<ReminderModel> reminderModels = pageReminder.getContent();
                logger.info("[Controller- Load Management Reminder View] [Size] list of reminders: " + reminderModels.size());

                // set side-bar
                String sideBar = IContanst.SIDE_BAR_MANAGER_MANAGEMENT_REMINDER;

                model.addAttribute("ListReminders", reminderModels);
                // side-bar
                model.addAttribute("SideBar", sideBar);

                url = IViewConst.MANAGEMENT_REMINDER_VIEW;
            }
        }

        logger.info("[Controller- Load Management Reminder View] END");
        return url;
    }

    @RequestMapping(value = "/addReminder", method = RequestMethod.GET)
    public String loadAddReminderView(Model model, HttpSession session) {
        logger.info("[Controller- Load Add Reminder View] BEGIN");

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            url = IViewConst.LOGIN_VIEW;
            String role = accountModel.getRole().getName().toUpperCase();
            // check is manager
            if ("MANAGER".equals(role)) {
                Long managerId = accountModel.getId();

                // get all employees for assigning participants
                List<AccountModel> accountModels = accountService.getEmployeesOfDepart(managerId);
                logger.info("[Controller- Load Add Reminder View] size of list employees: " + accountModels.size());

                // set side-bar
                String sideBar = IContanst.SIDE_BAR_MANAGER_MANAGEMENT_REMINDER;

                model.addAttribute("ListAccounts", accountModels);
                // side-bar
                model.addAttribute("SideBar", sideBar);

                url = IViewConst.ADD_REMINDER_VIEW;
            }
        }

        logger.info("[Controller- Load Add Reminder View] END");
        return url;
    }

    @RequestMapping(value = "/addReminderProcessing", produces = "text/plain;charset=UTF-8", method = RequestMethod.POST)
    public String addReminder(@RequestParam("title") String title,
                              @RequestParam("time") String time,
                              @RequestParam("listEmployees") String[] listEmployees,
                              @RequestParam("message") String message,
                              HttpSession session) {
        logger.info("[Controller- Add Reminder] BEGIN");
        logger.info("[Controller- Add Reminder] title: " + title);
        logger.info("[Controller- Add Reminder] time: " + time);
        logger.info("[Controller- Add Reminder] size of list employees: " + listEmployees.length);
        logger.info("[Controller- Add Reminder] message: " + message);
        TimeUtil timeUtil = new TimeUtil();
        Date timeParser = timeUtil.parseStringToDate(time);
        logger.info("[Controller- Add Reminder] parse to Date: " + timeParser);
        List<Long> employeeSet = new ArrayList<Long>();
        for (int i = 0; i < listEmployees.length; i++) {
            logger.info("[Controller- Add Reminder] employeeId[" + i + "]: " + listEmployees[i]);
            employeeSet.add(ValidateUtil.isNumber(listEmployees[i]));
        }

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            url = IViewConst.LOGIN_VIEW;
            String role = accountModel.getRole().getName().toUpperCase();
            // check is manager
            if ("MANAGER".equals(role)) {
                Long managerId = accountModel.getId();
                ReminderModifyModel reminderModifyModel = new ReminderModifyModel();
                reminderModifyModel.setTitle(title);
                reminderModifyModel.setMessage(message);
                reminderModifyModel.setTime(timeParser.getTime());
                reminderModifyModel.setManagerId(managerId);
                reminderModifyModel.setEmployeeSet(employeeSet);
                // create reminder
                BaseResponseG<ReminderModel> response = reminderService.create(reminderModifyModel);

                url = IViewConst.ADD_REMINDER_VIEW;
                boolean success = response.isSuccess();
                logger.info("[Controller- Add Reminder] [result] create reminder: " + success);
                if (success) {
                    url = "redirect:/manager/reminders/";
                }
            }
        }

        logger.info("[Controller- Add Reminder] END");
        return url;
    }
}
