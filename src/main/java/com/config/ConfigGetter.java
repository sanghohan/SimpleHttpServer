package com.config;

import com.google.gson.Gson;
import com.util.LogUtil;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;


public class ConfigGetter {

    private static final Logger logger = LoggerFactory.getLogger(ConfigGetter.class);
    public final static String DEFAULT_CONF_FILE = "/httpTest/serverConfig.json";

    private static String configString;
    private static Config config;

    private final static int DEFAULT_SERVER_PORT = 8080;

    /*static {
        loadFile();
    }
    */

    private ConfigGetter(){}

    private static void loadFile(String configFilePath) {

        String thisLine;
        StringBuilder sb = new StringBuilder();
        try {

            if(StringUtils.isEmpty(configFilePath))
                configFilePath = DEFAULT_CONF_FILE;

            File configFile = new File(configFilePath);
            if(!configFile.exists())
                throw new Exception("Config File Not Found! - " + configFilePath);

            BufferedReader br = new BufferedReader(new FileReader(configFile.getPath())) ;
            while ((thisLine = br.readLine()) != null) {
                sb.append(thisLine);
            }

            configString = sb.toString();
        }
        catch(Exception e){
            logger.error("load Config file error!! - serverConfig.json \n set default port 8080 \n" + LogUtil.getStackTrace(e));
            setDefaultConfig();
        }
    }

    @Deprecated
    private static void loadFile() {

        String thisLine;
        StringBuilder sb = new StringBuilder();

        try {

            URL url = ClassLoader.getSystemResource(DEFAULT_CONF_FILE);
            BufferedReader br = new BufferedReader(new FileReader(url.getPath())) ;
            while ((thisLine = br.readLine()) != null) {
                sb.append(thisLine);
            }

            configString = sb.toString();
        }
        catch(Exception e){
            logger.error("load Config file error!! - serverConfig.json \n set default port 8080\n" + LogUtil.getStackTrace(e) );
            setDefaultConfig();
        }
    }

    @Deprecated
    public static Config getConfig() {

        loadFile();

        if(StringUtils.isEmpty(configString)) {
            setDefaultConfig();
            return config;
        }

        if(config ==  null) {
            config = new Config();
            Gson gson = new Gson();
            config = gson.fromJson(configString, Config.class);
        }

        checkPort();

        logger.debug(config.toString());

        return config;
    }

    public static Config getConfig(String configFilePath) {

        loadFile(configFilePath);

        if(StringUtils.isEmpty(configString)) {
            setDefaultConfig();
            return config;
        }

        if(config ==  null) {
            config =  new Config();
            Gson gson = new Gson();
            config = gson.fromJson(configString, Config.class);
        }

        checkPort();

        logger.debug(config.toString());

        return config;
    }

    private static void setDefaultConfig() {
        config = new Config();
        config.setServerPort(DEFAULT_SERVER_PORT);
        config.setVirtualHostInfo(null);
    }

    private static void checkPort() {
        try {

            int port = config.getServerPort();
            if (port < 0 || port > 65535)
                throw new RuntimeException();

        } catch (RuntimeException ex) {
            logger.error("invaild server port number! \n" + LogUtil.getStackTrace(ex) );
            config.setServerPort(DEFAULT_SERVER_PORT);
        }
    }

}
