package com.timelinekeeping.service;

import com.timelinekeeping.config.AppConfigKeys;

/**
 * Created by lethanhtan on 9/7/16.
 */
public class PersonGroupService {

    public boolean create(String groupName) {
        String key = AppConfigKeys.getInstance().getPropertyValue("Ocp.Apim.Subscription.Key");

        System.out.println(key);
        return true;
    }
}
