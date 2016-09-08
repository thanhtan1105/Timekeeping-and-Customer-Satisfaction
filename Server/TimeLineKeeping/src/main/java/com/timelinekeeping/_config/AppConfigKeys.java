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

    private Properties properties = null;
    private static AppConfigKeys instance = null;

    public static AppConfigKeys getInstance() {
        if (instance == null) {
            instance = new AppConfigKeys();
        }
        return instance;
    }

    public AppConfigKeys() {
        properties = new Properties();
        File f = new File("/apiConfig.properties");
        InputStream inputStream;
        try {
            if (f.exists()) {
                //if file exists use as default configuration
                inputStream = new FileInputStream(f);
            } else {
                //if the configuration file is not exists. Use the default file
                System.out.println("Can not find configuration file on server");
                inputStream = this.getClass().getClassLoader().getResourceAsStream("apiConfig.properties");
            }
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

}
