package com.timelinekeeping.controller;

import com.timelinekeeping.common.BaseResponseG;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_TIME;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.*;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.service.serviceImplement.CoordinateServiceImpl;
import com.timelinekeeping.service.serviceImplement.ReminderServiceImpl;
import com.timelinekeeping.util.TimeUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadManagementReminderView(Model model) {
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_MANAGEMENT_REMINDER;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        return IViewConst.MANAGEMENT_REMINDER_VIEW;
    }

    @RequestMapping(value = "/addReminder", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadAddReminderView(Model model, HttpSession session) {
        logger.info("[Controller- Load Add Reminder View] BEGIN");

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            url = IViewConst.LOGIN_VIEW;
            // check is manager
//                Long managerId = accountModel.getId();
            Long departmentId = accountModel.getDepartment().getId();
            Long roleId = accountModel.getRole().getId();

            // get all employees for assigning participants
//                List<AccountModel> accountModels = accountService.getEmployeesOfDepart(managerId);
            List<AccountModel> accountModels = accountService.getEmployeesOfDepart(departmentId);
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

        logger.info("[Controller- Load Add Reminder View] END");
        return url;
    }

    @RequestMapping(value = "/addReminderProcessing", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String addReminder(@RequestParam("title") String title,
                              @RequestParam("date") String date,
                              @RequestParam("time") String time,
                              @RequestParam("roomId") String roomId,
                              @RequestParam(value = "listEmployees", required = false) String[] listEmployees,
                              @RequestParam("message") String message,
                              HttpSession session) {
        logger.info("[Controller- Add Reminder] BEGIN");
        logger.info("[Controller- Add Reminder] title: " + title);
        logger.info("[Controller- Add Reminder] time: " + time);
        logger.info("[Controller- Add Reminder] roomId: " + roomId);
        logger.info("[Controller- Add Reminder] size of list employees: " + (listEmployees != null ? listEmployees.length : 0));
        logger.info("[Controller- Add Reminder] message: " + message);
        String dateTime = date + " " + time;
        logger.info("[Controller- Add Reminder] date time: " + dateTime);
        Date timeParser = TimeUtil.parseToDate(dateTime, I_TIME.FULL_TIME_MINUS_AM);
        logger.info("[Controller- Add Reminder] parse to Date: " + timeParser);
        Set<Long> employeeSet = new HashSet<>();
        if (listEmployees != null && listEmployees.length > 0) {
            for (int i = 0; i < listEmployees.length; i++) {
                logger.info("[Controller- Add Reminder] employeeId[" + i + "]: " + listEmployees[i]);
                employeeSet.add(ValidateUtil.parseNumber(listEmployees[i]));
            }
        }
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            url = IViewConst.LOGIN_VIEW;
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

        logger.info("[Controller- Add Reminder] END");
        return url;
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

    @RequestMapping(value = "/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String updateReminder(Model model, HttpSession session) {
        logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());

        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
//        Long managerId = accountModel.getId();

        // get all employees for assigning participants
        List<AccountModel> accountModels = accountService.getEmployeesOfDepart(accountModel.getDepartment().getId());
        //get all rooms
        List<CoordinateModel> coordinateModels = coordinateService.getRoomPoint();
        logger.info(Thread.currentThread().getStackTrace()[1].getMethodName() + "[size of list rooms] "
                + coordinateModels.size());

        // set side-bar7
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

    @RequestMapping(value = "/updating", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String updating(@RequestParam("title") String title,
                           @RequestParam("date") String date,
                           @RequestParam("time") String time,
                           @RequestParam("roomId") String roomId,
                           @RequestParam(value = "listEmployees", required = false) String[] listEmployees,
                           @RequestParam("message") String message,
                           HttpSession session) {
        logger.info("[Controller- Add Reminder] BEGIN");
        logger.info("[Controller- Add Reminder] title: " + title);
        logger.info("[Controller- Add Reminder] time: " + time);
        logger.info("[Controller- Add Reminder] roomId: " + roomId);
        logger.info("[Controller- Add Reminder] size of list employees: " + (listEmployees != null ? listEmployees.length : 0));
        logger.info("[Controller- Add Reminder] message: " + message);
        String dateTime = date + " " + time;
        logger.info("[Controller- Add Reminder] date time: " + dateTime);
        Date timeParser = TimeUtil.parseToDate(dateTime, I_TIME.FULL_TIME_MINUS_AM);
        logger.info("[Controller- Add Reminder] parse to Date: " + timeParser);

        Set<Long> employeeSet = new HashSet<>();
        if (listEmployees != null && listEmployees.length > 0) {
            for (int i = 0; i < listEmployees.length; i++) {
                logger.info("[Controller- Add Reminder] employeeId[" + i + "]: " + listEmployees[i]);
                employeeSet.add(ValidateUtil.parseNumber(listEmployees[i]));
            }
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
