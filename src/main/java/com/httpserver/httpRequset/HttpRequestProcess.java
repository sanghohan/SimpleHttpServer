package com.httpserver.httpRequset;

import com.config.Config;
import com.config.VirtualHostInfo;
import com.httpserver.requestHandler.DefaultHandler;
import com.httpserver.requestHandler.RequestHandlerManager;
import com.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by 1002074 on 2016. 4. 29..
 */

public class HttpRequestProcess {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestProcess.class);

    private File rootDirectory = new File(rootPath);
    private Config config;
    Map<String, String> requestHeaderMap;
    private final static String rootPath = "/httpTest";
    private RequestHandlerManager requestHandlerManager;

    public HttpRequestProcess(Map<String, String> requestHeaderMap, Config config, RequestHandlerManager manager) throws Exception{

        this.requestHeaderMap = requestHeaderMap;
        this.config = config;
        this.requestHandlerManager = manager;
        setRootDirectory(requestHeaderMap.get("Host"));
    }

    public void processHttpRequst(Socket connection) throws Exception{
        try {

            if (requestHeaderMap == null)
                return;

            String method = requestHeaderMap.get("Method");
            String version = requestHeaderMap.get("Version");
            String fileName = requestHeaderMap.get("FileName");

            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            Writer out = new OutputStreamWriter(raw);

            InnerHttpRequest request = new InnerHttpRequest();
            request.setMethod(method);
            request.setRequestUrl(requestHeaderMap.get("Host"));
            request.setVersion(version);

            if(StringUtils.isNotEmpty(fileName))
                request.setRequestFile(fileName.substring(1));

            InnerHttpResponse response = new InnerHttpResponse(out, raw);

            logger.debug("request file name " + request.getRequestFile());
            logger.debug("request url " + request.getRequestUrl());

            if (requestHandlerManager == null || requestHandlerManager.getServletMap().get(request.getRequestFile()) == null) {
                DefaultHandler defaultSevlet = new DefaultHandler(rootDirectory.getAbsolutePath());
                defaultSevlet.service(request, response);
            }

            else {
                requestHandlerManager.getServletMap().get(request.getRequestFile()).service(request, response);
            }

        } catch (IOException ex) {
            logger.error("Error talking to " + connection.getRemoteSocketAddress() +"\n" + LogUtil.getStackTrace(ex));
        } finally {
            try {
                connection.close();
            } catch (IOException ex) {
            }
        }


    }


    private void setRootDirectory(String requsetHostName) throws Exception {

        if(requsetHostName !=null) {
            ArrayList<VirtualHostInfo> virtualHostInfos = config.getVirtualHostInfo();

            for (VirtualHostInfo vhostInfo : virtualHostInfos) {
                if (vhostInfo != null & vhostInfo.getHostName() != null & vhostInfo.getDocRoot() != null) {
                    if (requsetHostName.startsWith(vhostInfo.getHostName()))
                        rootDirectory = new File(vhostInfo.getDocRoot());
                }
            }

        }

        checkRootDirectory();

        logger.info("rootDirectory : " + rootDirectory.getPath());

    }

    private void checkRootDirectory() {

        if(rootDirectory == null)
            rootDirectory = new File(rootPath);

        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException(
                    "rootDirectory must be a directory, not a file");
        }

    }

    /*
    private List<Map<String, String> parameterParser(String fileName) {
        String paramLine = fileName.substring(0, fileName.indexOf("?"));
        logger.debug("parameter Line : " + paramLine);

        String[] keyValueArray = paramLine.split(";");

        return null;
    }
    */
}
