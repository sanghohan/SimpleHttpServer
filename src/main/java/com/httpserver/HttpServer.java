package com.httpserver;


import com.config.Config;
import com.config.ConfigGetter;
import com.httpserver.requestHandler.RequestHandlerManager;
import com.util.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class HttpServer {

    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    private static final int NUM_THREADS = 50;
    private Config config;

    private RequestHandlerManager requestHandlerManager;

    public void setRequestHandlerManager(RequestHandlerManager requestHandlerManager) {
        this.requestHandlerManager = requestHandlerManager;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public void start() throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        ServerSocket server = new ServerSocket(config.getServerPort());

        logger.info("Accepting connections on port " + server.getLocalPort());

        while (true) {
            try {
                Socket request = server.accept();
                Runnable r = new RequestProcessor(request, config, requestHandlerManager);
                pool.submit(r);
            } catch (IOException ex) {
                logger.warn("Error accepting \n" + LogUtil.getStackTrace(ex));
            }
        }
    }


    public static void main(String[] args) {

        try {
            HttpServer server = new HttpServer();
            server.setConfig(ConfigGetter.getConfig(ConfigGetter.DEFAULT_CONF_FILE));
            server.start();

        }catch (IOException e) {

        }

    }
}