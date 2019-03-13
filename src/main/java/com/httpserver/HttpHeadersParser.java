package com.httpserver;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 1002074 on 2016. 4. 29..
 */

public class HttpHeadersParser {

    private static final Logger logger = LoggerFactory.getLogger(HttpHeadersParser.class);

    public Map<String, String> parse(InputStream inputStream) throws Exception {


        Reader reader = new InputStreamReader(new BufferedInputStream(inputStream), "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);

        String nextLine;
        int lineCount = 0;

        Map<String, String> headerMap = new HashMap<String, String>();
        while ((nextLine = bufferedReader.readLine()) != null) {

            logger.info(nextLine);
            if(StringUtils.isEmpty(nextLine))
                break;

            if (isFirstLine(lineCount)) {
                String[] tokens = nextLine.split("\\s+");
                String version = "";

                headerMap.put("Method", tokens[0]);
                headerMap.put("FileName", tokens[1]);

                if (tokens.length > 2)
                    version = tokens[2];

                headerMap.put("Version", version);
                lineCount++;

            } else {
                String[] tokens = parseHeader(nextLine);
                headerMap.put(tokens[0], tokens[1]);
            }

        }

        return headerMap;
    }

    private static String[] parseHeader(String headerLine) throws Exception {

        int colonIndex = headerLine.indexOf(':');
        if (colonIndex == -1 || colonIndex == 0 || colonIndex == headerLine.length() - 1) {
            throw new Exception(String.format("Improperly formatted header: %s", headerLine));
        }
        return new String[]{headerLine.substring(0, colonIndex), headerLine.substring(colonIndex + 1, headerLine.length()).trim()};
    }

    private boolean isFirstLine(int count) {

        if(count == 0)
            return true;
        else
            return false;
    }
}