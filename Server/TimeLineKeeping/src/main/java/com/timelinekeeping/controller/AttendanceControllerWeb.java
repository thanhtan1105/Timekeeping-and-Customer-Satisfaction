package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import com.timelinekeeping.model.AccountAttendanceModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String loadAttendanceView(Model model) {
        logger.info("[Controller- Load Attendance View] BEGIN");
        Long accountId = ValidateUtil.validateNumber("4");
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

        logger.info("[Controller- Load Attendance View] END");
        return ViewConst.ATTENDANCE_VIEW;
    }
}
