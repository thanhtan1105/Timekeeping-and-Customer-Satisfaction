package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.Account;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.AccountService;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
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
    private AccountServiceImpl accountService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Account create(@ModelAttribute("account") Account account,
                          @RequestParam("groupId") String groupId){
        Account accountRespone = accountService.create(account, groupId);
        logger.info("Account: " + JsonUtil.toJson(accountRespone));
        return accountRespone;
    }

//    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
//    @ResponseBody
//    public List<Account> view (){
//        return accountService.listAll();
//    }


    @RequestMapping(value = {"/listAll"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse listAll(@RequestParam("start") int start,
                                @RequestParam("top") int top,
                                @RequestParam("departmentId") int departmentId) {
        logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getStackTrace()[1].getMethodName());
        BaseResponse response = accountService.listAll(departmentId, start, top);
        logger.info(IContanst.END_METHOD_SERVICE);
        return response;
    }
}
