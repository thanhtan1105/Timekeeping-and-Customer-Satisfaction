package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.model.AccountView;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;

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
    public BaseResponse create(@ModelAttribute("account") AccountEntity account){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.create(account);
        } catch (Exception e) {
            logger.error(IContanst.ERROR_LOGGER, e);
            return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }

    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search (@RequestParam(value = "start", required = false) Integer page,
                                @RequestParam(value = "top", required = false) Integer size){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, accountService.listAll(page, size));
        } catch (Exception e) {
            logger.error(IContanst.ERROR_LOGGER, e);
            return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = "/checkin_img", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse checkin (@RequestParam(value = "img") MultipartFile fileImg){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.checkin(fileImg.getInputStream());
        } catch (Exception e) {
            logger.error(IContanst.ERROR_LOGGER, e);
           return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }




}
