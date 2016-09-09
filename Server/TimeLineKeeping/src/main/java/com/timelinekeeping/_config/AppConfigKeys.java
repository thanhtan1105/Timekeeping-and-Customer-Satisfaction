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
        initApi();
        initMessage();
    }

    private void initApi(){
        propertiesApi = new Properties();
        File f = new File("/" + NAME_FILE_API_PROPERTIES);
        InputStream inputStream;
        try {
            if (f.exists()) {
                //if file exists use as default configuration
                inputStream = new FileInputStream(f);
            } else {
                //if the configuration file is not exists. Use the default file
                inputStream = this.getClass().getClassLoader().getResourceAsStream(NAME_FILE_API_PROPERTIES);
            }
            propertiesApi.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initMessage(){
        propertiesMessage = new Properties();
        File f = new File("/" + NAME_FILE_MESSAGE_PROPERTIES);
        InputStream inputStream;
        try {
            if (f.exists()) {
                //if file exists use as default configuration
                inputStream = new FileInputStream(f);
            } else {
                //if the configuration file is not exists. Use the default file
                inputStream = this.getClass().getClassLoader().getResourceAsStream(NAME_FILE_MESSAGE_PROPERTIES);
            }
            propertiesMessage.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getApiPropertyValue(String key) {
        return propertiesApi.getProperty(key);
    }

    public String getMessagePropertyValue(String key) {
        return propertiesMessage.getProperty(key);
    }

    public static void main(String[] args) {
        System.out.println(AppConfigKeys.getInstance().getMessagePropertyValue("message.test"));
    }





}
