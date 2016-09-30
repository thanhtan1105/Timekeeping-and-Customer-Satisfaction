package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import com.timelinekeeping.model.AccountAttendanceModel;
import com.timelinekeeping.model.AccountTKDetailsModel;
import com.timelinekeeping.model.TimekeepingResponseModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by TrungNN on 9/30/2016.
 */
@Controller
@RequestMapping("/manager/timekeeping")
public class TimekeepingControllerWeb {

    private Logger logger = Logger.getLogger(TimekeepingControllerWeb.class);

    @Autowired
    private TimekeepingServiceImpl timekeepingService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadTimekeepingView(Model model) {
        logger.info("[Controller- Load Timekeeping View] BEGIN");
        Long managerId = ValidateUtil.validateNumber("3");
        // get current date
        Calendar calendar = Calendar.getInstance();
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        logger.info("[Controller- Load Timekeeping View] current month: " + month);
        logger.info("[Controller- Load Timekeeping View] current year: " + year);

        // get timekeeping
        TimekeepingResponseModel timekeepingResponseModel
                = timekeepingService.getTimeKeeping(managerId, year, month);

        model.addAttribute("TimekeepingResponseModel", timekeepingResponseModel);

        logger.info("[Controller- Load Timekeeping View] END");
        return ViewConst.TIME_KEEPING_VIEW;
    }

    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public String loadTimekeepingDetailsView(@RequestParam("accountTKDetailsModel") String accountTKDetailsModelJson,
                                             Model model) {
        logger.info("[Controller- Load Timekeeping Details View] BEGIN");
        logger.info("[Controller- Load Timekeeping Details View] accountTKDetailsModelJson: " + accountTKDetailsModelJson);
        String pattern = "MMMM-yyyy";
        // parse string-json to object
        AccountTKDetailsModel accountTKDetailsModel
                = JsonUtil.convertObject(accountTKDetailsModelJson, AccountTKDetailsModel.class, pattern);
        if (accountTKDetailsModel != null) {
            Long accountId = accountTKDetailsModel.getAccountId();
            Date selectedDate = accountTKDetailsModel.getSelectedDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer year = calendar.get(Calendar.YEAR);

            AccountAttendanceModel accountAttendanceModel = timekeepingService.getAttendance(accountId, year, month);

            model.addAttribute("AccountAttendanceModel", accountAttendanceModel);
            model.addAttribute("SelectedDate", selectedDate);
        }

        logger.info("[Controller- Load Timekeeping Details View] END");

        return ViewConst.TIME_KEEPING_DETAILS_VIEW;
    }
}
