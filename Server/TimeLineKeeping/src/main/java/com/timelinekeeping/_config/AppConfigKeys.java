package com.timelinekeeping._config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by lethanhtan on 9/7/16.
 */
public class AppConfigKeys {

    private Properties propertiesApi = null;
    private Properties propertiesMessage = null;
    private static AppConfigKeys instance = null;

    private static final String NAME_FILE_API_PROPERTIES = "apiConfig.properties";
    private static final String NAME_FILE_MESSAGE_PROPERTIES = "message.properties";

    public static AppConfigKeys getInstance() {
        if (instance == null) {
            instance = new AppConfigKeys();
        }
        return instance;
    }

    //TODO log in file

    public AppConfigKeys() {
        propertiesApi = new Properties();
        propertiesMessage = new Properties();
        initProperties(propertiesApi, NAME_FILE_API_PROPERTIES);
        initProperties(propertiesMessage, NAME_FILE_MESSAGE_PROPERTIES);
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
                inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
            }
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiPropertyValue(String key) {
        try {
            return propertiesApi.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    public String getMessagePropertyValue(String key) {
        try {
            return propertiesMessage.getProperty(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(AppConfigKeys.getInstance().getMessagePropertyValue("message.test"));
        System.out.println(AppConfigKeys.getInstance().getApiPropertyValue("api.person.group.train.person"));
    }





}
