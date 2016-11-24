package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountModel;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by TrungNN on 10/4/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_EMPLOYEE_EMOTION)
public class CustomerEmotionControllerWeb {

    private Logger logger = Logger.getLogger(CustomerEmotionControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String loadCustomerEmotionView(Model model, HttpSession session) {
        logger.info("[Controller- Load Customer Emotion View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            // check is employee
            url = IViewConst.CUSTOMER_EMOTION_VIEW;

            // get current date
            Date currentDate = new Date();
            // set side-bar
            String sideBar = IContanst.SIDE_BAR_EMPLOYEE_CUSTOMER_EMOTION;

            //current date
            model.addAttribute("CurrentDate", currentDate);
            // side-bar
            model.addAttribute("SideBar", sideBar);
        }

        logger.info("[Controller- Load Customer Emotion View] END");
        return url;
    }
}
