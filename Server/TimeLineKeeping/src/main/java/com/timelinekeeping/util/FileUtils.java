package com.timelinekeeping.util;

import com.timelinekeeping._config.AppConfigKeys;
import com.timelinekeeping.constant.IContanst;
import com.timelinekeeping.constant.I_TIME;
import com.timelinekeeping.constant.I_URI;
import com.timelinekeeping.model.AccountModel;

import java.io.File;
import java.util.Date;

/**
 * Created by HienTQSE60896 on 11/16/2016.
 */
public class FileUtils {


    public static String correctUrl(String uri) {
        if (ValidateUtil.isEmpty(uri)) {
            return uri;
        }
        if (File.separatorChar != uri.charAt(0)) {
            uri = File.separator + uri;
        }
        return IContanst.PREFIX_STORE + uri;
    }

    public static String addParentFolderImage(String nameFile) {
        String path = AppConfigKeys.getInstance().getApplicationPropertyValue("file.store.path");
        if (File.separatorChar != nameFile.charAt(0) && path.charAt(path.length() - 1) != File.separatorChar) {
            nameFile = File.separatorChar + nameFile;
        }

        return path + nameFile;

    }

    public static String createFolderEmotion(String customerCode) {
        return IContanst.DIRECTOR_EMOTION + File.separator + TimeUtil.timeToString(new Date(), I_TIME.TIME_NAME) + File.separator + I_URI.SESSION_API_EMOTION_CUSTOMER_CODE + customerCode + "." + IContanst.EXTENSION_FILE_IMAGE;
    }

    public static String createFolderTrain(AccountModel accountModel) {
        return IContanst.DIRECTOR_TIMEKEEPING + File.separator + String.format("%s_%s", accountModel.getDepartment().getId(), accountModel.getDepartment().getCode())
                + File.separator + String.format("%s_%s", accountModel.getId(), accountModel.getUsername()) + File.separator
                + IContanst.DIRECTOR_TRAIN + File.separator + TimeUtil.timeToString(new Date(), I_TIME.TIME_NAME_FULL) + "." + IContanst.EXTENSION_FILE_IMAGE;
    }

    public static String createFolderCheckin(AccountModel accountModel) {
        return IContanst.DIRECTOR_TIMEKEEPING + File.separator + String.format("%s_%s", accountModel.getDepartment().getId(), accountModel.getDepartment().getCode())
                + File.separator + String.format("%s_%s", accountModel.getId(), accountModel.getUsername()) + File.separator
                + IContanst.DIRECTOR_CHECKIN + File.separator + TimeUtil.timeToString(new Date(), I_TIME.TIME_NAME_FULL) + "." + IContanst.EXTENSION_FILE_IMAGE;
    }

//    public static void main(String[] args) {
////        AccountModel accountModel = new AccountModel();
////        accountModel.setId(1l);
////        accountModel.setUsername("quanghien");
////        accountModel.setDepartment(new DepartmentModel(1l, "fpt_university"));
////        System.out.println(createFolderEmotion("2763276372"));
////        System.out.println(createFolderTrain(accountModel));
////        System.out.println(createFolderCheckin(accountModel));
//
//        System.out.println(addParentFolderImage("abcd.jpg"));
//    }
}
