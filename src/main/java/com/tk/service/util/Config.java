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
    private static Properties properties = null;

    public static String readValue(ConfigKeys key) {
        String result = null;
        try {
            if(properties == null) {
                InputStream configStream = Config.class.getClassLoader().getResourceAsStream(configFileName);
                properties = new Properties();
                properties.load(configStream);
                configStream.close();
            }
            result = properties.getProperty(key.toString().toLowerCase());
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return result;
    }
}
