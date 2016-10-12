package com.timelinekeeping.accessAPI;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.BaseResponse;
import com.timelinekeeping.util.HTTPClientUtil;
import com.timelinekeeping.util.JsonUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 10/12/2016.
 */
public class SMSNotification {
    private Logger logger = LogManager.getLogger(PersonServiceMCSImpl.class);
    private String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.sms.url");

    public BaseResponse sendSms(String phone, String message) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getName());

            /*** url*/
            URIBuilder builder = new URIBuilder(rootPath)
                    .addParameter("key", "tandeptrai")
                    .addParameter("phone", phone)
                    .addParameter("content", message);

            /** entity*/

            return HTTPClientUtil.getInstanceFace().toGet(builder.build(), JsonUtil.MAP_PARSER, String.class);
        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println(JsonUtil.toJson(new SMSNotification().sendSms("0936714994", "Xin chao a Tan Xau trai.")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
