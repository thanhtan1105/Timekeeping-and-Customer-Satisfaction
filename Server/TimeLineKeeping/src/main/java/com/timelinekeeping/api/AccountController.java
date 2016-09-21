package com.timelinekeeping.api;

import com.timelinekeeping.constant.ERROR;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.model.AccountModifyModel;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.BaseResponseG;
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
    public BaseResponse create(@ModelAttribute("account") AccountModifyModel account) {
        try {
            BaseResponseG<AccountModel> response = accountService.create(account);
            logger.info("AccountModel: " + JsonUtil.toJson(response));
            return response.toBaseResponse();
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) Integer page,
                               @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) Integer size) {

        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Page<AccountModel> accountModelList = accountService.listAll(page, size);
            return new BaseResponse(true, accountModelList);
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }

    @RequestMapping(value = "/searchByDepartment", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse searchByDepartment(@RequestParam(value = "departmentID") Long departmentID,
                                           @RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) Integer start,
                                           @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) Integer top) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("departmentID: " + departmentID);
            logger.info("start: " + start);
            logger.info("top: " + top);
            if (departmentID == null) {
                return new BaseResponse(false, ERROR.ACCOUNT_API_SEARCH_DEPARTMENT_EMPTY);
            }


            return new BaseResponse(true, accountService.searchByDepartment(departmentID, start, top));
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);

        }
    }


    @RequestMapping(value = "/add_face_img", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse addFaceToAccount(@RequestParam(value = "image") MultipartFile imageFile,
                                         @RequestParam(value = "accountId") Long accountId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.addFaceImg(Long.valueOf(accountId), imageFile.getInputStream());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }


    @RequestMapping(value = "/check_in_img", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse checkin(@RequestParam(value = "image") MultipartFile fileImg) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return accountService.checkin(fileImg.getInputStream());
        } catch (Exception e) {
            logger.error(IContanst.LOGGER_ERROR, e);
            return new BaseResponse(e);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }
}
