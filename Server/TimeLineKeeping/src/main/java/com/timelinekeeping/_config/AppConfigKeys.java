package com.timelinekeeping._config;

import com.timelinekeeping.util.UtilApps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Properties;

/**
 * Created by lethanhtan on 9/7/16.
 */
public class AppConfigKeys {

    private Properties propertiesApi = null;
    private Properties propertiesMessage = null;
    private Properties propertiesApplication = null;

    private static AppConfigKeys instance = null;

    private static final String NAME_FILE_API_PROPERTIES = "apiConfig.properties";
    private static final String NAME_FILE_APPLICATION_PROPERTIES = "application.properties";
    private static final String NAME_FILE_MESSAGE_PROPERTIES = "messages.properties";

    public static AppConfigKeys getInstance() {
        if (instance == null) {
            instance = new AppConfigKeys();
        }
        return instance;
    }

    public AppConfigKeys() {
        propertiesApi = new Properties();
        propertiesMessage = new Properties();
        propertiesApplication = new Properties();
        initProperties(propertiesApi, NAME_FILE_API_PROPERTIES);
        initProperties(propertiesMessage, NAME_FILE_MESSAGE_PROPERTIES);
        initProperties(propertiesApplication, NAME_FILE_APPLICATION_PROPERTIES);
    }


    private void initProperties(Properties properties, String fileName){

        File f = new File("/" + fileName);
        InputStream inputStream;
        try {
            if (f.exists()) {
                //if file exists use as default configuration
                inputStream = new FileInputStream(f);
            } else {
                //if the configuration file is not exists. Use the default file
                URL filePath = this.getClass().getClassLoader().getResource(fileName);
                inputStream = new FileInputStream(filePath.getFile());
            }
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiPropertyValue(String key) {
        try {
            return UtilApps.trim(propertiesApi.getProperty(key));
        } catch (Exception e) {
            return null;
        }
    }
    public double getApiPropertyDouble(String key) {
        try {
            String value = getApiPropertyValue(key);
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0d;
        }
    }

    public String getMessagePropertyValue(String key) {
        try {
            return UtilApps.trim(propertiesMessage.getProperty(key));
        } catch (Exception e) {
            return null;
        }
    }

    public String getApplicationPropertyValue(String key) {
        try {
            return UtilApps.trim(propertiesApplication.getProperty(key));
        } catch (Exception e) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        System.out.println(AppConfigKeys.getInstance().getMessagePropertyValue("message.test"));
//        System.out.println(AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.person"));
//        System.out.println(AppConfigKeys.getInstance().getAmazonPropertyValue("amazon.s3.link"));
//    }





}
