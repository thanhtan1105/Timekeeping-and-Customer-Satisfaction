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

    // list all


}
