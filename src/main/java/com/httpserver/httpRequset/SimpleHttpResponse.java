package com.httpserver.httpRequset;

import java.io.IOException;

/**
 * Created by 1002074 on 2016. 4. 30..
 */
public interface SimpleHttpResponse {

    void writeBody(String body) throws IOException;
    void writeBody(byte[] body) throws IOException;
    void sendHeader(String httpCode, String contentType, int length)
            throws IOException;

}
