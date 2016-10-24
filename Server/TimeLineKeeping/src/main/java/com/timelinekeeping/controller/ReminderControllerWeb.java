package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.common.BaseResponseG;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.service.serviceImplement.CoordinateServiceImpl;
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
import java.util.stream.Collectors;

/**
 * Created by TrungNN on 9/22/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_MANAGER_REMINDER)
public class ReminderControllerWeb {

    private Logger logger = Logger.getLogger(ReminderControllerWeb.class);

    @Autowired
    private ReminderServiceImpl reminderService;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private CoordinateServiceImpl coordinateService;

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
                //get all rooms
                List<CoordinateModel> coordinateModels = coordinateService.getRoomPoint();
                logger.info("[Controller- Load Add Reminder View] size of list rooms: " + coordinateModels.size());

                // set side-bar
                String sideBar = IContanst.SIDE_BAR_MANAGER_MANAGEMENT_REMINDER;

                model.addAttribute("ListAccounts", accountModels);
                model.addAttribute("ListRooms", coordinateModels);
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
                              @RequestParam("roomId") String roomId,
                              @RequestParam("listEmployees") String[] listEmployees,
                              @RequestParam("message") String message,
                              HttpSession session) {
        logger.info("[Controller- Add Reminder] BEGIN");
        logger.info("[Controller- Add Reminder] title: " + title);
        logger.info("[Controller- Add Reminder] time: " + time);
        logger.info("[Controller- Add Reminder] roomId: " + roomId);
        logger.info("[Controller- Add Reminder] size of list employees: " + listEmployees.length);
        logger.info("[Controller- Add Reminder] message: " + message);
        TimeUtil timeUtil = new TimeUtil();
        Date timeParser = timeUtil.parseStringToDate(time);
        logger.info("[Controller- Add Reminder] parse to Date: " + timeParser);
        List<Long> employeeSet = new ArrayList<Long>();
        for (int i = 0; i < listEmployees.length; i++) {
            logger.info("[Controller- Add Reminder] employeeId[" + i + "]: " + listEmployees[i]);
            employeeSet.add(ValidateUtil.parseNumber(listEmployees[i]));
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
                reminderModifyModel.setRoomId(ValidateUtil.parseNumber(roomId));
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

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String viewReminder(@RequestParam("reminderId") String reminderId,
                               HttpSession session, Model model) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "[reminderId] " + reminderId);
        ReminderModel reminderModel = reminderService.get(ValidateUtil.parseNumber(reminderId));
        session.setAttribute("ReminderModel", reminderModel);

        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_MANAGEMENT_REMINDER;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        return IViewConst.VIEW_REMINDER_VIEW;
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateReminder(Model model, HttpSession session) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        Long managerId = accountModel.getId();

        // get all employees for assigning participants
        List<AccountModel> accountModels = accountService.getEmployeesOfDepart(managerId);
        //get all rooms
        List<CoordinateModel> coordinateModels = coordinateService.getRoomPoint();
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "[size of list rooms] "
                + coordinateModels.size());

        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_MANAGEMENT_REMINDER;

        //get list only employeeID thui, qua bên kia dể làm hơn
        ReminderModel reminderModel = (ReminderModel) session.getAttribute("ReminderModel");
        List<Long> employeeIds = reminderModel.getListEmployee().stream().map(AccountNotificationModel::getId).collect(Collectors.toList());

        model.addAttribute("ListAccounts", accountModels);
        model.addAttribute("ListRooms", coordinateModels);
        model.addAttribute("ListEmployeeId", employeeIds);
        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info(IContanst.END_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
        return IViewConst.UPDATE_REMINDER_VIEW;
    }

    @RequestMapping(value = "/updating", method = RequestMethod.POST)
    public String updating(@RequestParam("title") String title,
                           @RequestParam("time") String time,
                           @RequestParam("roomId") String roomId,
                           @RequestParam("listEmployees") String[] listEmployees,
                           @RequestParam("message") String message,
                           HttpSession session) {
        TimeUtil timeUtil = new TimeUtil();
        Date timeParser = timeUtil.parseStringToDate(time);

        List<Long> employeeSet = new ArrayList<Long>();
        for (int i = 0; i < listEmployees.length; i++) {
            employeeSet.add(ValidateUtil.parseNumber(listEmployees[i]));
        }

        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        Long managerId = accountModel.getId();
        ReminderModel reminderModel = (ReminderModel) session.getAttribute("ReminderModel");
        Long reminderId = reminderModel.getId();

        ReminderModifyModel reminderModifyModel = new ReminderModifyModel();
        reminderModifyModel.setId(reminderId);
        reminderModifyModel.setTitle(title);
        reminderModifyModel.setMessage(message);
        reminderModifyModel.setTime(timeParser.getTime());
        reminderModifyModel.setManagerId(managerId);
        reminderModifyModel.setEmployeeSet(employeeSet);
        reminderModifyModel.setRoomId(ValidateUtil.parseNumber(roomId));

        BaseResponseG<ReminderModel> response = reminderService.update(reminderModifyModel);
        if (response.isSuccess()) {
            return "redirect:/manager/reminders/view?reminderId=" + reminderId;
        }

        return IViewConst.VIEW_REMINDER_VIEW;
    }
}
