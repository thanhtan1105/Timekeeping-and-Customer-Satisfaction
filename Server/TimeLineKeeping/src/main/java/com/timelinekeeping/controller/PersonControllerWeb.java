package com.timelinekeeping.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by TrungNN on 9/14/2016.
 */
@Controller
@RequestMapping("/persons")
public class PersonControllerWeb {
    private Logger logger = Logger.getLogger(PersonControllerWeb.class);

    @RequestMapping(value = "/selectperson", method = RequestMethod.POST)
    public String loadSelectPersonView(@RequestParam("personGroupId") String personGroupId,
                                       Model model) {
        System.out.println("[Load view_selectPerson] personGroupId: " + personGroupId);

        // personGroupId
        model.addAttribute("personGroupId", personGroupId);
        
        return "/views/staff/training_image/select_person";
    }
}
