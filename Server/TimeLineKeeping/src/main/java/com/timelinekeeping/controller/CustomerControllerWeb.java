package com.timelinekeeping.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

/**
 * Created by lethanhtan on 9/3/16.
 */

@Controller
public class CustomerControllerWeb {


    @RequestMapping(value = {"/test"}, method = RequestMethod.GET)
    @ResponseBody
    public String indexTest() {
        return "Hello World";
    }

    @RequestMapping("/home")
    public String loadAdminHome() {
        return "views/admin/home";
    }

    @RequestMapping(value = {"/testManagerSecurity"}, method = RequestMethod.GET)
    @ResponseBody
    public String testManagerSecurity() {
        return "Hello Manager";
    }

    @RequestMapping(value = {"/testEmployeeSecurity"}, method = RequestMethod.GET)
    @ResponseBody
    public String testEmployeeSecurity() {
        return "Hello Employee";
    }

    @RequestMapping(value = {"/testSASecurity"}, method = RequestMethod.GET)
    @ResponseBody
    public String testSASecurity() {
        return "Hello SA";
    }
}
