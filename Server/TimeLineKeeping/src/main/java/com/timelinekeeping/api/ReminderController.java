package com.timelinekeeping.api;

import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.ReminderModifyModel;
import com.timelinekeeping.model.ReminderQueryModel;
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
    public BaseResponse listByManager(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) int start,
                                      @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) int top,
                                      @RequestParam(value = "managerId") Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, reminderService.listByEmployee(managerId, start, top));
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_GET}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse get(@RequestParam(value = "id") Long reminderId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            logger.info("Id: " + reminderId);
            return new BaseResponse(true, reminderService.get(reminderId));
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_CREATE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@ModelAttribute ReminderModifyModel reminderModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return reminderService.create(reminderModel).toBaseResponse();
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_UPDATE}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse update(@ModelAttribute ReminderModifyModel reminderModel) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return reminderService.update(reminderModel).toBaseResponse();
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }


    @RequestMapping(value = {I_URI.API_DELETE}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse delete(@RequestParam("reminderId") Long reminderId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            Boolean result = reminderService.delete(reminderId);
            return new BaseResponse(result);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_SEARCH}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) int start,
                               @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) int top,
                               @RequestParam(value = "title", required = false) String title,
                               @RequestParam(value = "managerId") Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, reminderService.search(title, managerId, start, top));
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_SEARCH_ADV}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse search(@RequestParam(value = "start", required = false, defaultValue = IContanst.PAGE_PAGE) int start,
                               @RequestParam(value = "top", required = false, defaultValue = IContanst.PAGE_SIZE) int top,
                               @ModelAttribute ReminderQueryModel reminderQueryModel,
                               @RequestParam(value = "managerId") Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, reminderService.search(reminderQueryModel, managerId, start, top));
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

    @RequestMapping(value = {I_URI.API_REMINDER_AUTO_COMPLETE}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse autoComplete(@RequestParam(value = "title")String title,
                               @RequestParam(value = "managerId") Long managerId) {
        try {
            logger.info(IContanst.BEGIN_METHOD_CONTROLLER + Thread.currentThread().getStackTrace()[1].getMethodName());
            return new BaseResponse(true, reminderService.autoCompleTitle(title, managerId));
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(false, e.getMessage());
        } finally {
            logger.info(IContanst.END_METHOD_CONTROLLER);
        }
    }

}
