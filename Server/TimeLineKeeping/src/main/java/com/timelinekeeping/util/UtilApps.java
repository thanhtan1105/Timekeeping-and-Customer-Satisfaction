package com.timelinekeeping.util;

import com.timelinekeeping.entity.NotificationEntity;
import com.timelinekeeping.model.AccountNotificationModel;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by HienTQSE60896 on 9/10/2016.
 */
public class UtilApps {


    public static String trim(String text) {
        return ValidateUtil.isEmpty(text) ? null : text.trim();
    }



    public static InputStream[] muitleStream(InputStream stream, int size) {
        InputStream[] streamResult = new InputStream[size];
        try {
            byte[] bytes = IOUtils.toByteArray(stream);
            for (int i = 0; i < size; i++) {
                streamResult[i] = new ByteArrayInputStream(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return streamResult;

    }

    public static AccountNotificationModel getAccountFromNotify(NotificationEntity notificationEntity) {
        return new AccountNotificationModel(notificationEntity.getAccountReceive());
    }

    public static String generatePassword() {
        String password = "";
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            int p = 97 + r.nextInt(122 - 97);
            password += r.nextInt(100) +"" + ((char) p);
        }
        return password;
    }

    public static void main(String[] args) {
        System.out.println(generatePassword());
    }
}
