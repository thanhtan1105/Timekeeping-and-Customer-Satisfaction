package com.timelinekeeping.service;

import com.timelinekeeping._config.AppConfigKeys;

/**
 * Created by HienTQSE60896 on 9/11/2016.
 */
public class BaseService {
    protected String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.microsoft.cognitive.service.root.url");
}
