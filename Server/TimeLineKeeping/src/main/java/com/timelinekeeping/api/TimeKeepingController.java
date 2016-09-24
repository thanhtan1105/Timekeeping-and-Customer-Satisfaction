package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountCheckInModel;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.TimekeepingServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<AccountCheckInModel> checkInManual(@RequestParam("departmentId") Long departmentId){
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return timekeepingService.getEmployeeDepartment(departmentId);
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

}
