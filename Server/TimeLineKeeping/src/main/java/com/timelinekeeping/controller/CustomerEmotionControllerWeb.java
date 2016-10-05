package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.model.AccountModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 * Created by TrungNN on 10/4/2016.
 */
@Controller
@RequestMapping("/employee/customer_emotion")
public class CustomerEmotionControllerWeb {

    private Logger logger = Logger.getLogger(CustomerEmotionControllerWeb.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadCustomerEmotionView(Model model, HttpSession session) {
        logger.info("[Controller- Load Customer Emotion View] BEGIN");
        String url = IViewConst.LOGIN_VIEW;
        // get session
        AccountModel accountModel = (AccountModel) session.getAttribute("UserSession");
        if (accountModel != null) {
            String role = accountModel.getRole().getName().toUpperCase();
            // check is employee
            if ("EMPLOYEE".equals(role)) {
                url = IViewConst.CUSTOMER_EMOTION_VIEW;
            }
        }

        logger.info("[Controller- Load Customer Emotion View] END");
        return url;
    }
}
