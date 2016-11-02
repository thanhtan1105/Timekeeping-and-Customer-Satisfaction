package com.timelinekeeping.service.blackService;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.Gender;
import com.timelinekeeping.model.AccountModel;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by HienTQSE60896 on 11/2/2016.
 */
public class OneSignalNotification {


    private OneSignalNotification() {
    }

    private static OneSignalNotification oneSignalNotification;

    public static OneSignalNotification instance() {
        if (oneSignalNotification == null) {
            oneSignalNotification = new OneSignalNotification();
        }
        return oneSignalNotification;
    }

    /**
     * push notification for device
     */
    public void pushNotification(AccountModel account, String header, String message) {
        try {
            String jsonResponse;
            String urlString = AppConfigKeys.getInstance().getApiPropertyValue("api.one.signal.notification.url");
            String key = AppConfigKeys.getInstance().getApiPropertyValue("api.one.signal.notification.key");
            URL url = new URL(urlString);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", key);
            con.setRequestMethod("POST");


            String strJsonBody = "{"
                    + "\"app_id\": \"7c01cb54-6bca-4ff2-8f12-b2a2e5248b9a\","
                    + "\"include_player_ids\" : [\"" + account.getToken() + "\"],"
                    + "\"data\": {\"id\": " + account.getId() + "},"
                    + "\"headings\": {\"en\": \"" + header + "\"},"
                    + "\"contents\": {\"en\": \"" + message + "\"}"
                    + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (httpResponse >= HttpURLConnection.HTTP_OK && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            } else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void main(String[] args) {
        AccountModel accountModel = new AccountModel();
        accountModel.setGender(Gender.MALE);
        accountModel.setId(1l);
        accountModel.setToken("4a6b934d-beb7-458c-b5b0-b76ec95b86eb");

        OneSignalNotification.instance().pushNotification(accountModel, "Test", "dang test gi day");
    }
}
