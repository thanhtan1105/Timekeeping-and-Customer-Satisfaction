package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.model.CheckinManualModel;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by HienTQSE60896 on 9/24/2016.
 */
@RestController
@RequestMapping(I_URI.API_TIMEKEEPING)
public class TimeKeepingController {

    @Autowired
    private TimekeepingServiceImpl timekeepingService;

    private Logger logger = LogManager.getLogger(TimeKeepingController.class);

    @RequestMapping(I_URI.API_TIMEKEEPING_LIST_EMPLOYEE)
    public List<AccountCheckInModel> getEmployeeDepartment(@RequestParam("departmentId") Long departmentId){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return timekeepingService.getEmployeeDepartment(departmentId);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = I_URI.API_TIMEKEEPING_CHECK_IN_MANUAL, method = RequestMethod.POST)
    public List<CheckinManualModel> checkInManual(@RequestParam(value = "accountId", required = false) Long[] listAccountId){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return timekeepingService.checkInManual(Arrays.asList(3l,4l,5l));
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }


    @RequestMapping(value = I_URI.API_ACCOUNT_SEARCH_DEPARTMENT, method = RequestMethod.POST)
    public List<CheckinManualModel> getTimeKeeping(@RequestParam(value = "accountId", required = false) Long[] listAccountId){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return timekeepingService.checkInManual(Arrays.asList(3l,4l,5l));
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

}