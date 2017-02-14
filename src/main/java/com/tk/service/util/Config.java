package com.tk.service.util;

import com.tk.domain.enums.ConfigKeys;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Config
 *
 * @author: Trim Kadriu <trim.kadriu@gmail.com>
 */
public class Config {
    private static final String configFileName = "config.properties";
    private static Properties properties = null;

    private static void initializeProperties() {
        try {
            if (properties == null) {
                String appLocation = "D:\\Projects\\Decentralized-Auth-Simulation\\target\\";
                FileInputStream configStream = new FileInputStream(appLocation + configFileName);
                //InputStream configStream = configFileStram.
                //InputStream configStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
                properties = new Properties();
                properties.load(configStream);
                configStream.close();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static String readValue(ConfigKeys key) {
        initializeProperties();
        return properties.getProperty(key.toString().toLowerCase());
    }

    public static int readInt(ConfigKeys key) {
        initializeProperties();
        return Integer.parseInt(properties.getProperty(key.toString().toLowerCase()));
    }

    public static boolean readBoolean(ConfigKeys key) {
        initializeProperties();
        return Boolean.parseBoolean(properties.getProperty(key.toString().toLowerCase()));
    }
}
