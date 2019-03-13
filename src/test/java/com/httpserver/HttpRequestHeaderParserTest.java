package com.httpserver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by 1002074 on 2016. 4. 29..
 */

public class HttpRequestHeaderParserTest {

        HttpHeadersParser parser;

        @Before
        public void setUp() throws Exception {
            parser = new HttpHeadersParser();
        }


        @Test
        public void parsesAHeader() throws Exception {
            Map<String, String> requestHeaders = parser.parse(this.stringToStream("GET /s localhost \nSome: header"));
            assertTrue(requestHeaders.containsKey("Some"));
            assertEquals("header", requestHeaders.get("Some"));
        }


        private InputStream stringToStream(String str) {
            return new ByteArrayInputStream(str.getBytes());
        }
}
