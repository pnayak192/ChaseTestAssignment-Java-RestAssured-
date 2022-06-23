package com.tools.config;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoggerControler {
    private static Logger logger = null;
    private static LoggerControler logg = null;

    public static LoggerControler getLogger(Class<?> T) {
        if (logger == null) {
//            instantiate a Properties Class that handles the log4j.Properties file
            Properties props = new Properties();
            try {
//                Get log4j configuration file path
                String envPaht = System.getProperty("user.dir") +
                        File.separator + "config" + File.separator + "log4j.properties";
                InputStream is = new FileInputStream(envPaht);
                props.load(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            log4j The configure method of the PropertyConfigurator class inputs the data stream
            PropertyConfigurator.configure(props);
            logger = Logger.getLogger(T);
            logg = new LoggerControler();
        }
        return logg;
    }

    public void info(Object msg) {
        logger.info(msg);
    }

    public void debug(Object msg) {
        logger.debug(msg);
    }

    public void warn(Object msg) {
        logger.warn(msg);
    }

    public void error(Object msg) {
        logger.error(msg);
    }
}
