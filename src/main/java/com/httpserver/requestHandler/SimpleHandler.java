package com.httpserver.requestHandler;


import com.httpserver.httpRequset.SimpleHttpRequest;
import com.httpserver.httpRequset.SimpleHttpResponse;

/**
 * Created by 1002074 on 2016. 4. 29..
 */
public interface SimpleHandler {
    void service(SimpleHttpRequest req, SimpleHttpResponse response);
}
