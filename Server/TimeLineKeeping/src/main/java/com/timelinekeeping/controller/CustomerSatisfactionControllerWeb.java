package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IViewConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TrungNN on 10/5/2016.
 */
@Controller
@RequestMapping("/manager/cus_satisfaction")
public class CustomerSatisfactionControllerWeb {

    private Logger logger = Logger.getLogger(CustomerSatisfactionControllerWeb.class);

    @RequestMapping(value = "/month/", method = RequestMethod.GET)
    public String loadCustomerSatisfactionMonthView() {
        logger.info("[Controller- Load Customer Satisfaction Month View] BEGIN");
        return IViewConst.CUSTOMER_SATISFACTION_MONTH_VIEW;
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
