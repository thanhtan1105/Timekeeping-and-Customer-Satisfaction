package com.timelinekeeping.controller;

import com.timelinekeeping.constant.ViewConst;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.model.CheckinManualModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import com.timelinekeeping.util.ValidateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
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

    @RequestMapping(value = "/checkinManualProcessing", method = RequestMethod.POST)
    public String checkinManual(@RequestParam("accountIds") String[] accountIds,
                                @RequestParam("notes") String[] notes,
                                @RequestParam("noteOfAccountIds") String[] noteOfAccountIds) {
        logger.info("[Controller- Check-in Manual] BEGIN");
        logger.info("[Controller- Check-in Manual] number of accounts: " + accountIds.length);
        logger.info("[Controller- Check-in Manual] number of notes: " + notes.length);
        logger.info("[Controller- Check-in Manual] number of note of accountIds: " + noteOfAccountIds.length);
        List<Long> listAccount = new ArrayList<Long>();
        for (int i = 0; i < accountIds.length; i++) {
            logger.info("[Controller- Check-in Manual] [" + i + "] accountId: " + accountIds[i]);
            listAccount.add(ValidateUtil.validateNumber(accountIds[i]));
        }
        for (int i = 0; i < notes.length; i++) {
            logger.info("[Controller- Check-in Manual] [" + i + "] note: " + notes[i]);
        }
        for (int i = 0; i < noteOfAccountIds.length; i++) {
            logger.info("[Controller- Check-in Manual] [" + i + "] note of accountIds: " + noteOfAccountIds[i]);
        }

        List<CheckinManualModel> checkinManualModels = timekeepingService.checkInManual(listAccount);
        // TODO: check resutl check-in manual

        logger.info("[Controller- Check-in Manual] END");
        return "redirect:/manager/check_in/";
    }
}
