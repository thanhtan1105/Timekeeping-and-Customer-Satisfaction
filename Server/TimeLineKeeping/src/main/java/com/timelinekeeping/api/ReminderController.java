package com.timelinekeeping.api;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.model.ReminderModifyModel;
import com.timelinekeeping.service.serviceImplement.ReminderServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HienTQSE60896 on 9/22/2016.
 */
@RestController
@RequestMapping(I_URI.API_REMINDER)
public class ReminderController {

    @Autowired
    private ReminderServiceImpl reminderService;

    private Logger logger = LogManager.getLogger(ReminderController.class);

    @RequestMapping(value = {I_URI.API_REMINDER_LIST_MANAGER}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse findAll(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) int start,
                                @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) int top,
                                @RequestParam(value = "managerId") Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, reminderService.listByEmployee(managerId, start, top));
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_CREATE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@ModelAttribute ReminderModifyModel reminderModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return reminderService.create(reminderModel).toBaseResponse();
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }
}