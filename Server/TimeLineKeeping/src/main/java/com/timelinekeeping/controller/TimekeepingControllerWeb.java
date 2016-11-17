package com.timelinekeeping.controller;

import com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_TIME;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountAttendanceModel;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.AccountTKDetailsModel;
import com.timelinekeeping.model.TimekeepingResponseModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.TimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TrungNN on 9/30/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_MANAGER_TIMEKEEPING)
public class TimekeepingControllerWeb {

    private Logger logger = Logger.getLogger(TimekeepingControllerWeb.class);

    @Autowired
    private TimekeepingServiceImpl timekeepingService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadTimekeepingView(Model model, HttpSession session) {
        logger.info("[Controller- Load Timekeeping View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            String role = accountModel.getRole().getName().toUpperCase();
            // check is manager
            if ("MANAGER".equals(role)) {
                Long managerId = accountModel.getId();
                // get current date
                Calendar calendar = Calendar.getInstance();
                Integer month = calendar.get(Calendar.MONTH) + 1;
                Integer year = calendar.get(Calendar.YEAR);
                logger.info("[Controller- Load Timekeeping View] current month: " + month);
                logger.info("[Controller- Load Timekeeping View] current year: " + year);

                // get timekeeping
                TimekeepingResponseModel timekeepingResponseModel
                        = timekeepingService.getTimeKeeping(managerId, year, month);
                if (timekeepingResponseModel != null) {
                    // get selected date
                    Date selectedDate = timekeepingResponseModel.getNowDate();
                    model.addAttribute("SelectedDate", selectedDate);
                }

                // set side-bar
                String sideBar = IContanst.SIDE_BAR_MANAGER_TIMEKEEPING;

                model.addAttribute("TimekeepingResponseModel", timekeepingResponseModel);
                // side-bar
                model.addAttribute("SideBar", sideBar);

                url = IViewConst.TIME_KEEPING_VIEW;
            }
        }

        logger.info("[Controller- Load Timekeeping View] END");
        return url;
    }

    //TODO change to method GET
    @RequestMapping(value = "/details", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadTimekeepingDetailsView(@RequestParam("accountTKDetailsModel") String accountTKDetailsModelJson,
                                             Model model) {
        logger.info("[Controller- Load Timekeeping Details View] BEGIN");
        logger.info("[Controller- Load Timekeeping Details View] accountTKDetailsModelJson: " + accountTKDetailsModelJson);
        // parse string-json to object
        AccountTKDetailsModel accountTKDetailsModel
                = JsonUtil.convertObject(accountTKDetailsModelJson, AccountTKDetailsModel.class, I_TIME.FULL_YEAR_MONTH);
        if (accountTKDetailsModel != null) {
            Long accountId = accountTKDetailsModel.getAccountId();
            Date selectedDate = accountTKDetailsModel.getSelectedDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(selectedDate);
            Integer month = calendar.get(Calendar.MONTH) + 1;
            Integer year = calendar.get(Calendar.YEAR);

            AccountAttendanceModel accountAttendanceModel = timekeepingService.getAttendance(accountId, year, month, true);
            logger.info(JsonUtil.toJson(accountAttendanceModel));
            model.addAttribute("AccountAttendanceModel", accountAttendanceModel);
            model.addAttribute("SelectedDate", selectedDate);
        }
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_TIMEKEEPING;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info("[Controller- Load Timekeeping Details View] END");

        return IViewConst.TIME_KEEPING_DETAILS_VIEW;
    }

    //TODO change to method GET
    @RequestMapping(value = "/change_month", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String changeMonthTimekeepingView(@RequestParam("selectedMonth") String selectedMonth,
                                             Model model, HttpSession session) {
        logger.info("[Controller- Change Month Timekeeping View] BEGIN");
        logger.info("[Controller- Change Month Timekeeping View] selected month: " + selectedMonth);
        // parse to date
        Date selectedDate = TimeUtil.parseToDate(selectedMonth, I_TIME.FULL_YEAR_MONTH);
        logger.info("[Controller- Change Month Timekeeping View] selected date: " + selectedDate);
        // get month, year
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        logger.info("[Controller- Change Month Timekeeping View] selected month: " + month);
        logger.info("[Controller- Change Month Timekeeping View] selected year: " + year);

        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            String role = accountModel.getRole().getName().toUpperCase();
            // check is manager
            if ("MANAGER".equals(role)) {
                Long managerId = accountModel.getId();

                // get timekeeping
                TimekeepingResponseModel timekeepingResponseModel
                        = timekeepingService.getTimeKeeping(managerId, year, month);
                // set side-bar
                String sideBar = IContanst.SIDE_BAR_MANAGER_TIMEKEEPING;

                model.addAttribute("TimekeepingResponseModel", timekeepingResponseModel);
                model.addAttribute("SelectedDate", selectedDate);

                // side-bar
                model.addAttribute("SideBar", sideBar);

                url = IViewConst.TIME_KEEPING_VIEW;
            }
        }

        logger.info("[Controller- Change Month Timekeeping View] END");
        return url;
    }

    //TODO change to method GET
    @RequestMapping(value = "/details/change_month", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String changeMonthTimekeepingDetailsView(@RequestParam("selectedMonth") String selectedMonth,
                                                    @RequestParam("accountId") Long accountId,
                                                    Model model) {
        logger.info("[Controller- Change Month Timekeeping Details View] BEGIN");
        logger.info("[Controller- Change Month Timekeeping Details View] selected month: " + selectedMonth);
        logger.info("[Controller- Change Month Timekeeping Details View] accountId: " + accountId);

        // parse to date
        Date selectedDate = TimeUtil.parseToDate(selectedMonth, I_TIME.FULL_YEAR_MONTH);
        logger.info("[Controller- Change Month Timekeeping View] selected date: " + selectedDate);
        // get month, year
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        Integer month = calendar.get(Calendar.MONTH) + 1;
        Integer year = calendar.get(Calendar.YEAR);
        logger.info("[Controller- Change Month Timekeeping View] selected month: " + month);
        logger.info("[Controller- Change Month Timekeeping View] selected year: " + year);

        // get attendance
        AccountAttendanceModel accountAttendanceModel = timekeepingService.getAttendance(accountId, year, month, true);
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_MANAGER_TIMEKEEPING;

        model.addAttribute("AccountAttendanceModel", accountAttendanceModel);
        model.addAttribute("SelectedDate", selectedDate);

        // side-bar
        model.addAttribute("SideBar", sideBar);

        logger.info("[Controller- Change Month Timekeeping Details View] END");
        return IViewConst.TIME_KEEPING_DETAILS_VIEW;
    }
}
