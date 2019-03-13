package com.httpserver.requestHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1002074 on 2016. 4. 29..
 */
public class RequestHandlerManager {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandlerManager.class);
    private Map<String, SimpleHandler> servletMap = new HashMap<String, SimpleHandler>();

    public Map<String, SimpleHandler> getServletMap() {
        return servletMap;
    }

    public void putServlet(SimpleHandler handler) {

        logger.debug("implemented servlet.." + handler);

        if(handler == null) {
            throw new RuntimeException("can`t input null handler!");
        }
        servletMap.put(handler.getClass().getSimpleName(), handler);
    }
}
