package com.hqyg.dubbo.annotation.common;

import java.util.*;
import java.io.*;
import org.apache.commons.lang3.*;

import com.hqyg.dubbo.annotation.exception.DubboxException;

public class DubboxConfig
{
    private static final String CONFIG_PATH = "dubbo.properties";
    private static Properties prop_dubbox;
    
    static {
        DubboxConfig.prop_dubbox = getProperties("dubbo.properties");
    }
    
    private static Properties getProperties(final String url) {
        try {
            final Properties prop = new Properties();
            final InputStream in = DubboxConfig.class.getClassLoader().getResourceAsStream(url);
            prop.load(in);
            in.close();
            return prop;
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static String getConfigValue(final String configKey) {
        if (StringUtils.isEmpty((CharSequence)configKey)) {
            throw new IllegalArgumentException("parameter is invalid");
        }
        final String configValue = DubboxConfig.prop_dubbox.getProperty(configKey);
        if (StringUtils.isEmpty((CharSequence)configValue)) {
            throw new DubboxException(String.valueOf(configKey) + " value can`t be null");
        }
        return configValue;
    }
}

