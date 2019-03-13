package com.httpserver.requestHandler;

import com.httpserver.httpRequset.SimpleHttpRequest;
import com.httpserver.httpRequset.SimpleHttpResponse;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by 1002074 on 2016. 4. 29..
 */
public class RequestHandlerManagerTest {

    @Test
    public void testManager() {

        try {
            RequestHandlerManager manager = new RequestHandlerManager();
            manager.putServlet(new TestHandler());

            SimpleHandler servlet = manager.getServletMap().get("TestHandler");
            servlet.service(null, null);
            assertTrue(true);

          /*  TestHandler testHandler = new TestHandler();
            System.out.print(testHandler.getClass().getCanonicalName());*/

        }catch(Exception e) {
            System.out.print(e.getStackTrace());
            assertTrue(false);
        }




    }

    private class TestHandler implements SimpleHandler {

        @Override
        public void service(SimpleHttpRequest req, SimpleHttpResponse response) {
            System.out.print("success!");
        }

    }
}
