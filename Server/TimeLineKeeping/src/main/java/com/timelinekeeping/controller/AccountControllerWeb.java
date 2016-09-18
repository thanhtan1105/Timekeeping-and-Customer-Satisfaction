package com.timelinekeeping.controller;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by TrungNN on 9/18/2016.
 */
@Controller
@RequestMapping("/admin/accounts")
public class AccountControllerWeb {

    private Logger logger = Logger.getLogger(AccountControllerWeb.class);

    @Autowired
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadManagementAccountView(Model model) {
        logger.info("[Controller- Load Management Account View] BEGIN");
        int page = 0;
        int size = 1000;

        BaseResponse response = accountService.listAll(page, size);
        List<AccountEntity> listAccounts = (List<AccountEntity>) response.getData();

        // List of accounts
        model.addAttribute("ListAccounts", listAccounts);
        logger.info("[Controller- Load Management Account View] END");

        return "/views/admin/management_acc/management_acc";
    }
}
