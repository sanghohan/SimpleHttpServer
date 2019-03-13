package com.config;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

/**
 * Created by 1002074 on 2016. 4. 30..
 */
public class GetHtmlTest {

    GetHttpHtml getHttpHtml;

    @Before
    public void newGetHtml() {
        getHttpHtml = new GetHttpHtml();
    }

    @Test
    public void getHtmlTest() {

        try {
            getHttpHtml.loadFile("error/200");
            getHttpHtml.loadFile("index");
            assertTrue(true);
        } catch (Exception e){
            System.out.println(e.getMessage());
            assertTrue(false);
        }

    }

}
