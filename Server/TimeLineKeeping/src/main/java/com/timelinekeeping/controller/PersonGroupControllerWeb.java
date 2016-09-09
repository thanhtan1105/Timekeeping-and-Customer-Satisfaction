package com.timelinekeeping.controller;

import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.service.serviceImplement.PersonGroupServiceImpl;
import com.timelinekeeping.util.JsonUtil;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by lethanhtan on 9/7/16.
 */

@Controller
@RequestMapping("/persongroups")
public class PersonGroupControllerWeb {

    private Logger logger = Logger.getLogger(PersonGroupControllerWeb.class);

    @Autowired
    private PersonGroupServiceImpl groupService;

    @RequestMapping(value = {"/create"}, method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse create(@RequestParam("groupName") String groupName,
                               @RequestParam("descriptions") String descriptions) {
        try {
            BaseResponse response = groupService.create(groupName, descriptions);
            logger.info("RESPONSE: " + JsonUtil.toJson(response));
            return response;

        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/listAll"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse listAll(@RequestParam("start") int start,
                                @RequestParam("top") int top) {
        try {
            return groupService.listAll(start, top);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/training"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse trainPersonGroup(@RequestParam("person_groups_id") String persongroupid) {
        try {
            return groupService.trainGroup(persongroupid);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }

    @RequestMapping(value = {"/training_status"}, method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse trainPersonGroupStatus(@RequestParam("person_groups_id") String persongroupid) {
        try {
            return groupService.trainPersonGroupStatus(persongroupid);
        } catch (Exception e) {
            logger.error(e);
            return new BaseResponse(e);
        }
    }


}
