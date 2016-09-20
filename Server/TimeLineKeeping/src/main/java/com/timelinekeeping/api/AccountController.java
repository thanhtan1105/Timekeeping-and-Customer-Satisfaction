package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.entity.AccountEntity;
import com.timelinekeeping.entity.DepartmentEntity;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.AccountServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public BaseResponse create(@ModelAttribute("account") AccountEntity account) {
        BaseResponse accountViewRespone = accountService.create(account);
        logger.info("AccountModel: " + JsonUtil.toJson(accountViewRespone));
        return accountViewRespone;
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "start", required = false) Integer page,
                               @RequestParam(value = "top", required = false) Integer size) {
        return accountService.listAll(page, size);
    }

    @RequestMapping(value = "/searchByDepartment", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse searchByDepartment(@RequestParam(value = "departmentID") Integer departmentID,
                                            @RequestParam(value = "start") Integer start,
                                           @RequestParam(value = "top") Integer top) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.setData(accountService.searchByDepartment(departmentID, start, top));
            baseResponse.setSuccess(true);
        } catch (Exception e) {
            baseResponse.setSuccess(false);
        }

        return baseResponse;
    }

    @RequestMapping(value = "/add_face_img", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "image") MultipartFile imageFile,
                               @RequestParam(value = "accountId") String accountId,
                               @RequestParam(value = "departmentID") String departmentID) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.addFaceImg(departmentID, Long.valueOf(accountId), imageFile.getInputStream());
        } catch (Exception e){
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        }finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

}
