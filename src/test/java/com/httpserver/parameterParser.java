package com.httpserver;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1002074 on 2016. 5. 1..
 */
public class parameterParser {

    @Test
    public void parameterParser() {
        String requsetString = "Hello?name=sangho;age=37";

        Map<String, String> keyValueMap = new HashMap<String, String>();

        String requsetFile = requsetString.substring(0, requsetString.indexOf("?"));
        System.out.println("requsetFile Line : " + requsetFile);

        String paramLine = requsetString.substring(requsetString.indexOf("?")+1, requsetString.length());
        System.out.println("parameter Line : " + paramLine);

        String[] keyValueArray = paramLine.split(";");
        for(String keyValue : keyValueArray) {
            System.out.println("keyValue Line : " + keyValue);
            keyValueMap.put(keyValue.split("=")[0], keyValue.split("=")[1]);
        }

        System.out.println(keyValueMap.get("age"));
        System.out.println(keyValueMap.get("name"));

    }
}
