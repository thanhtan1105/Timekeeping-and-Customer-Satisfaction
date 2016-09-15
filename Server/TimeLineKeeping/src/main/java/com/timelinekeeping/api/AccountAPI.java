package com.timelinekeeping.api;

import com.timelinekeeping.model.Account;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HienTQSE60896 on 9/14/2016.
 */
@RestController
@RequestMapping("/api/account")
public class AccountAPI {

    Logger logger = LogManager.getLogger(AccountAPI.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Account create(@ModelAttribute("account") Account account){
        logger.info("Account: " + JsonUtil.toJson(account));
        return account;

    }
}
