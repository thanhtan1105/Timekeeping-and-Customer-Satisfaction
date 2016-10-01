package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    public String loadAttendanceView() {
        logger.info("[Controller- Load Attendance View] BEGIN");

        return ViewConst.ATTENDANCE_VIEW;
    }
}
