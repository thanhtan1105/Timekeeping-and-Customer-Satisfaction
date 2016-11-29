package com.timelinekeeping.service.blackService;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.common.BaseResponse;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.model.AccountModel;
import com.timelinekeeping.util.JsonUtil;
import com.timelinekeeping.util.ServiceUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by HienTQSE60896 on 10/12/2016.
 */
public class SMSNotification {
    private Logger logger = LogManager.getLogger(SMSNotification.class);
    private String rootPath = AppConfigKeys.getInstance().getApiPropertyValue("api.sms.url");

    private String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) coc_coc_browser/58.3.130 Chrome/52.3.2743.130 Safari/537.36";

    private static SMSNotification sms;

    private SMSNotification() {
    }

    public static SMSNotification getInstance() {
        if (sms == null) {
            sms = new SMSNotification();
        }
        return sms;
    }

    /**
     * send sms to account with phone and message
     *
     * @param phone   receive messgae
     * @param message to send
     * @return com.timelinekeeping.common.BaseResponse
     */
    public BaseResponse sendSms(String phone, String message) throws URISyntaxException, IOException {
        try {
            logger.info(IContanst.BEGIN_METHOD_SERVICE + Thread.currentThread().getName());


            /*** url*/
            URIBuilder builder = new URIBuilder(rootPath)
                    .addParameter("key", "tandeptrai")
                    .addParameter("phone", phone)
                    .addParameter("content", message);

            /** entity*/
            HttpGet request = new HttpGet(builder.build());
            request.setHeader("Content-Type", "application/json");
            request.setHeader("User-Agent", USER_AGENT);

            /** create HttpClient*/
            CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
            /** Http Response*/
            HttpResponse response = httpClient.execute(request);
            String dataResponse = ServiceUtils.getDataResponse(response.getEntity());
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ||
                    response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
                return JsonUtil.convertObject(dataResponse, BaseResponse.class);
            } else {
                return new BaseResponse(false, dataResponse);
            }


        } finally {
            logger.info(IContanst.END_METHOD_SERVICE);
        }
    }

    public BaseResponse sendSms(String phoneNumber, AccountModel accountModel) throws IOException, URISyntaxException {
        // make data
        Gender gender = accountModel.getGender();
        String welcomeMessage = "Xin chao ";
        String prefix = gender == Gender.MALE ? "anh " : "chi ";
        welcomeMessage += prefix + " ";
        welcomeMessage += accountModel.getFullName() + " ";
        welcomeMessage += ". Chuc " + prefix + " " + "mot ngay lam viec tot lanh";
        return sendSms(phoneNumber, welcomeMessage);
    }

//    public static void main(String[] args) {
//        try {
//            System.out.println(JsonUtil.toJson(new SMSNotification().sendSms("0936714994", "Xin chao a Tan Xau trai.")));
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
