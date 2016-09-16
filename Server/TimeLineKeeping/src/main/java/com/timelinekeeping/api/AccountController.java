package com.timelinekeeping.api;

import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.AccountView;
import com.timelinekeeping.service.AccountService;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by HienTQSE60896 on 9/14/2016.
 */
@RestController
@RequestMapping("/api/account")
public class AccountController {

    Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;



    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public AccountView create(@ModelAttribute("accountView") AccountEntity account,
                              @RequestParam("groupId") String groupId){
        AccountView accountViewRespone = accountService.create(account, groupId);
        logger.info("AccountView: " + JsonUtil.toJson(accountViewRespone));
        return accountViewRespone;
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public List<AccountView> search (@RequestParam("page") int page,
                                     @RequestParam("size") int size){
        return accountService.listAll(page, size);
    }
}
