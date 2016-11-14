package com.timelinekeeping.controller;

import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.IViewConst;
import com.timelinekeeping.constant.I_URI;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by TrungNN on 11/8/2016.
 */
@Controller
@RequestMapping(I_URI.WEB_ADMIN_SYNC_DATA)
public class SyncControllerWeb {

    private Logger logger = Logger.getLogger(SyncControllerWeb.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String loadSyncDataView(Model model) {
        // set side-bar
        String sideBar = IContanst.SIDE_BAR_ADMIN_SYNC_DATA;

        // side-bar
        model.addAttribute("SideBar", sideBar);

        return IViewConst.SYNC_DATA_VIEW;
    }
}
