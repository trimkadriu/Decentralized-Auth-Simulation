package com.tk.service.util;

import com.tk.domain.enums.ConfigKeys;

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
    private static InputStream configStream = null;

    public static String readValue(ConfigKeys key) {
        if(configStream == null) {
            configStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
        }

        String result = null;
        try {
            if (configStream != null) {
                Properties prop = new Properties();
                prop.load(configStream);
                result = prop.getProperty(key.toString().toLowerCase());
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
