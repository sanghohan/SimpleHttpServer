package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by 1002074 on 2016. 4. 30..
 */
public class GetHttpHtml {

    public /*static*/ byte[] loadFile(String errorCode) throws Exception {

        String thisLine;
        StringBuilder sb = new StringBuilder();
        InputStream in = getClass().getClassLoader().getResourceAsStream(errorCode + ".html");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        while ((thisLine = br.readLine()) != null) {
            sb.append(thisLine);
        }

        byte[] htmlData = sb.toString().getBytes();

        return htmlData;
    }

}
