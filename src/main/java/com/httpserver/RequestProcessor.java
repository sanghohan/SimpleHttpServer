package com.httpserver;

import com.config.Config;
import com.httpserver.httpRequset.HttpRequestProcess;
import com.httpserver.requestHandler.RequestHandlerManager;
import com.util.LogUtil;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class RequestProcessor implements Runnable {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private Socket connection;
    private Config config;
    private RequestHandlerManager requestHandlerManager;

    public RequestProcessor(Socket connection, Config config, RequestHandlerManager requestHandlerManager) {
        this.connection = connection;
        this.config = config;
        this.requestHandlerManager = requestHandlerManager;
    }

    @Override
    public void run() {

        try {

            logger.info(connection.getInetAddress().toString());
            Map<String, String> httpHeaders = getHeader();

            HttpRequestProcess process = new HttpRequestProcess(httpHeaders, config, requestHandlerManager);
            process.processHttpRequst(connection);

        } catch (Exception ex) {
            logger.warn("Error talking to " + connection.getRemoteSocketAddress() + "\n" + LogUtil.getStackTrace(ex));
        } finally {
            try {
                connection.close();
                connection = null;
            } catch (IOException ex) {
            }
        }
    }

    private Map<String, String> getHeader() throws Exception {
        HttpHeadersParser parser = new HttpHeadersParser();
        return parser.parse(connection.getInputStream());
    }

}