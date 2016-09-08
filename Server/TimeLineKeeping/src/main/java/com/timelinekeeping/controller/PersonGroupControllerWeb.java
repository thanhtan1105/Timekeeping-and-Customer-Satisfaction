package com.timelinekeeping.controller;

import com.timelinekeeping.entity.PersonGroup;
import com.timelinekeeping.service.PersonGroupService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by lethanhtan on 9/7/16.
 */

@Controller
public class PersonGroupControllerWeb {


    @RequestMapping(value = {"/persongroups"}, method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(@RequestParam("groupName") String groupName,
                         @RequestParam("descriptions") String descriptions) {
        PersonGroupService groupService = new PersonGroupService();
        try {
            JSONObject response = groupService.create(groupName, descriptions);
            System.out.println("RESPONSE: " + response);

            return response;
        } catch (URISyntaxException e) {
            System.out.println(e.getMessage());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", e.getMessage());
            return jsonObject;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("error", e.getMessage());
            return jsonObject;
        }
    }

    @RequestMapping(value = {"/persongroups/listAll"}, method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<PersonGroup>> listAll(@RequestParam("start") int start,
                                                     @RequestParam("top") int top) {
        PersonGroupService groupService = new PersonGroupService();
        try {
            List<PersonGroup> personGroupList = groupService.listAll(start, top);
            return new ResponseEntity<>(personGroupList, HttpStatus.OK);

        } catch (URISyntaxException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
