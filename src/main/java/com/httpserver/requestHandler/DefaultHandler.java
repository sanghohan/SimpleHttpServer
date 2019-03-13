package com.httpserver.requestHandler;

import com.config.GetHttpHtml;
import com.httpserver.httpRequset.SimpleHttpRequest;
import com.httpserver.httpRequset.SimpleHttpResponse;
import com.util.LogUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
/**
 * Created by 1002074 on 2016. 4. 30..
 */
public class DefaultHandler implements SimpleHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultHandler.class);
    private static final String GET = "GET";
    private String rootDirectory;
    private static final String FAVICON = "favicon.ico";
    private static GetHttpHtml getHttpHtml = new GetHttpHtml();
    public DefaultHandler(String directory){
        rootDirectory = directory;
    }

    @Override
    public void service(SimpleHttpRequest req, SimpleHttpResponse response) {

        logger.debug("default servlet service start....");

        try {
            String fileName = req.getRequestFile();
            String version = req.getVersion();

            logger.debug("requset mehtod : " + req.getMethod());

            if (req.getMethod().equals(GET)) {
                if(StringUtils.isEmpty(fileName)) {

                    logger.debug("return index.html.... ");

                    byte [] body = getHttpHtml.loadFile("index");

                    if (version.startsWith("HTTP/")) // send a MIME header
                        response.sendHeader("HTTP/1.0 200", "text/html; charset=utf-8", body.length);

                    response.writeBody(body);
                    return;
                } else if (FAVICON.equals(fileName)){
                    return;
                }

                  /* else if(fileName.startsWith("..")){
                        byte [] body = getHttpHtml.loadFile("error/401");
                        if (version.startsWith("HTTP/")) { // send a MIME header
                            response.sendHeader("HTTP/1.0 401 Unauthorized",  "text/html; charset=utf-8", body.length);
                        }
                        response.writeBody(body);
                        return;
                    }*/

                String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
                File theFile = new File(rootDirectory, fileName);

                if (theFile.canRead()
                        // Don't let clients outside the document root
                        && theFile.getCanonicalPath().startsWith("/httpTest")) {

                    logger.debug("theFile.canRead().... : " + theFile.canRead());


                    byte[] theData = Files.readAllBytes(theFile.toPath());
                    if (version.startsWith("HTTP/")) { // send a MIME header
                        response.sendHeader("HTTP/1.0 200 OK", contentType, theData.length);
                    }
                    response.writeBody(theData);

                } else {

                    byte [] body = getHttpHtml.loadFile("error/404");

                    if (version.startsWith("HTTP/")) // send a MIME header
                        response.sendHeader("HTTP/1.0 404 File Not Found", "text/html; charset=utf-8", body.length);

                    response.writeBody(body);

                }
            } else {
                // method does not equal "GET"
                byte [] body = getHttpHtml.loadFile("error/501");

                if (version.startsWith("HTTP/")) // send a MIME header
                    response.sendHeader("HTTP/1.0 501 Not Implemented", "text/html; charset=utf-8", body.length);

                response.writeBody(body);

            }
        }catch (Exception e) {
            logger.error("DefalutServlet error \n" + LogUtil.getStackTrace(e));
        }
    }

}

