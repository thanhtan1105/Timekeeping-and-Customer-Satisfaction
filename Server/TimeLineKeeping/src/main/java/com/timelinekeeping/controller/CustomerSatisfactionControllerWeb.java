package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.CustomerServiceReport;
import com.timelinekeeping.service.serviceImplement.EmotionServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by TrungNN on 10/5/2016.
 */
@Controller
@RequestMapping("/manager/cus_satisfaction")
public class CustomerSatisfactionControllerWeb {

    private Logger logger = Logger.getLogger(CustomerSatisfactionControllerWeb.class);

    @Autowired
    private EmotionServiceImpl emotionService;

    @RequestMapping(value = "/month/", method = RequestMethod.GET)
    public String loadCustomerSatisfactionMonthView(Model model, HttpSession session) {
        logger.info("[Controller- Load Customer Satisfaction Month View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            String role = accountModel.getRole().getName().toUpperCase();
            // check is manager
            if ("MANAGER".equals(role)) {
                Long managerId = accountModel.getId();
                // get current date
                Date currentDate = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                Integer month = calendar.get(Calendar.MONTH) + 1;
                Integer year = calendar.get(Calendar.YEAR);
                Integer day = Integer.valueOf(IContanst.DEFAULT_INT);
                logger.info("[Controller- Load Customer Satisfaction Month View] current year: " + year);
                logger.info("[Controller- Load Customer Satisfaction Month View] current month: " + month);
                logger.info("[Controller- Load Customer Satisfaction Month View] current date: " + day);

                //get customer satisfaction report
                CustomerServiceReport customerServiceReport
                        = emotionService.reportCustomerService(year, month, day, managerId);
                if (customerServiceReport != null) {
                    logger.info("[Controller- Load Customer Satisfaction Month View] " + customerServiceReport.getDepartment().getName());
                } else {
                    logger.info("[Controller- Load Customer Satisfaction Month View]  null");
                }

                // set side-bar
                String sideBar = IContanst.SIDE_BAR_MANAGER_CUSTOMER_SATISFACTION;

//                model.addAttribute("CustomerServiceReport", customerServiceReport);
                model.addAttribute("SelectedDate", currentDate);
                // side-bar
                model.addAttribute("SideBar", sideBar);

                url = IViewConst.CUSTOMER_SATISFACTION_MONTH_VIEW;
            }
        }

        logger.info("[Controller- Load Customer Satisfaction Month View] END");
        return url;
    }

    @RequestMapping(value = "/date/", method = RequestMethod.GET)
    public String loadCustomerSatisfactionDateView() {
        logger.info("[Controller- Load Customer Satisfaction Date View] BEGIN");
        return IViewConst.CUSTOMER_SATISFACTION_DATE_VIEW;
    }

    @RequestMapping(value = "/month/details", method = RequestMethod.GET)
    public String loadCustomerSatisfactionDetailsView() {
        logger.info("[Controller- Load Customer Satisfaction Month Details View] BEGIN");
        return IViewConst.CUSTOMER_SATISFACTION_MONTH_DETAILS_VIEW;
    }
}
