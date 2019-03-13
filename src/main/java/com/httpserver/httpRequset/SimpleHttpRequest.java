package com.httpserver.httpRequset;

import java.util.List;
import java.util.Map;

/**
 * Created by 1002074 on 2016. 4. 30..
 */
public interface SimpleHttpRequest {

    String getMethod();
    String getRequestUrl();
    String getRequestFile();
    String getVersion();
    Map<String, String> getParams();

}
