package com.httpserver.httpRequset;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Date;

/**
 * Created by 1002074 on 2016. 4. 29..
 */
class InnerHttpResponse implements SimpleHttpResponse {

    private Writer out;
    private String contentType;
    private OutputStream outputStream;

    public InnerHttpResponse(Writer writer, OutputStream outputStream) {

        this.out = writer;
        this.outputStream = outputStream;
    }


    public void writeBody(String body) throws IOException{
        out.write(body);
        out.flush();
    }

    public void writeBody(byte[] body) throws IOException{
        outputStream.write(body);
        outputStream.flush();
    }

    public void sendHeader(String httpCode, String contentType, int length)
            throws IOException {
        out.write(httpCode+ "\r\n");
        Date now = new Date();
        out.write("Date: " + now + "\r\n");
        out.write("Server: JHTTP 2.0\r\n");
        out.write("Content-length: " + length + "\r\n");
        out.write("Content-type: " + contentType + "\r\n\r\n");

        out.flush();
    }
}
