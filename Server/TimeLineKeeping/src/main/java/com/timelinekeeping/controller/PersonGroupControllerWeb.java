package com.timelinekeeping.controller;

import com.timelinekeeping.service.PersonGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by lethanhtan on 9/7/16.
 */
public class PersonGroupControllerWeb {

    @Autowired
    private PersonGroupService groupService;

    @RequestMapping(value = {"/persongroups"}, method = RequestMethod.GET)
    public String create(@RequestParam("groupName") String groupName) {
        boolean isSuccess = groupService.create(groupName);
        return "Testing";
    }
}
