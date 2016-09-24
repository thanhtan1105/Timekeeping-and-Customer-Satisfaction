package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by TrungNN on 9/24/2016.
 */
@Controller
@RequestMapping("/manager/check_in")
public class CheckinManualControllerWeb {

    private Logger logger = Logger.getLogger(CheckinManualControllerWeb.class);

    @Autowired
    private TimekeepingServiceImpl timekeepingService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadCheckinManualView(Model model) {
        logger.info("[Controller- Load Check-in Manual View] BEGIN");
        Long departmentId = ValidateUtil.validateNumber("1");
        // get list of employees by departmentId
        List<AccountCheckInModel> accountCheckInModels = timekeepingService.getEmployeeDepartment(departmentId);
        if (accountCheckInModels != null) {
            int sizeOfListAccounts = accountCheckInModels.size();
            model.addAttribute("SizeOfListAccounts", sizeOfListAccounts);
        }

        model.addAttribute("AccountCheckInModels", accountCheckInModels);
        model.addAttribute("Usernanme", "trungnn");
        logger.info("[Controller- Load Check-in Manual View] END");

        return ViewConst.CHECK_IN_MANUAL_VIEW;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String checkinManual(@RequestParam("accountIds") String[] accountIds) {
        logger.info("[Controller- Check-in Manual] BEGIN");
        logger.info("[Controller- Check-in Manual] number of accounts: " + accountIds.length);
        for (int i = 0; i < accountIds.length; i++) {
        }
        logger.info("[Controller- Check-in Manual] END");
        return "";
    }
}
