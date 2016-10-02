package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.model.AccountAttendanceModel;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.TimeUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TrungNN on 10/1/2016.
 */
@Controller
@RequestMapping("/employee/attendance")
public class AttendanceControllerWeb {

    private Logger logger = Logger.getLogger(AttendanceControllerWeb.class);

    @Autowired
    private TimekeepingServiceImpl timekeepingService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadAttendanceView(Model model, HttpSession session) {
        logger.info("[Controller- Load Attendance View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            String role = accountModel.getRole().getName().toUpperCase();
            // check is employee
            if ("EMPLOYEE".equals(role)) {
                Long accountId = accountModel.getId();
                // get current date
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                Integer month = calendar.get(Calendar.MONTH) + 1;
                Integer year = calendar.get(Calendar.YEAR);
                logger.info("[Controller- Load Timekeeping View] current month: " + month);
                logger.info("[Controller- Load Timekeeping View] current year: " + year);

                AccountAttendanceModel accountAttendanceModel = timekeepingService.getAttendance(accountId, year, month);

                model.addAttribute("AccountAttendanceModel", accountAttendanceModel);
                model.addAttribute("SelectedDate", currentDate);
                url = IViewConst.ATTENDANCE_VIEW;
            }
        }

        logger.info("[Controller- Load Attendance View] END");
        return url;
    }

    @RequestMapping(value = "/change_month", method = RequestMethod.POST)
    public String changeMonthAttendanceView(@RequestParam("selectedMonth") String selectedMonth,
                                            Model model, HttpSession session) {
        logger.info("[Controller- Change Month Attendance View] BEGIN");
        logger.info("[Controller- Change Month Attendance View] selected month: " + selectedMonth);
        String pattern = "MMMM-yyyy";
        // parse to date
        Date selectedDate = TimeUtil.parseToDate(selectedMonth, pattern);
        logger.info("[Controller- Change Month Attendance View] selected date: " + selectedDate);
        // get month, year
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        logger.info("[Controller- Change Month Attendance View] selected month: " + month);
        logger.info("[Controller- Change Month Attendance View] selected year: " + year);

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            String role = accountModel.getRole().getName().toUpperCase();
            // check is employee
            if ("EMPLOYEE".equals(role)) {
                Long accountId = accountModel.getId();

                // get attendance
                AccountAttendanceModel accountAttendanceModel = timekeepingService.getAttendance(accountId, year, month);

                model.addAttribute("AccountAttendanceModel", accountAttendanceModel);
                model.addAttribute("SelectedDate", selectedDate);
                url = IViewConst.ATTENDANCE_VIEW;
            }
        }

        logger.info("[Controller- Change Month Attendance View] END");
        return url;
    }
}
