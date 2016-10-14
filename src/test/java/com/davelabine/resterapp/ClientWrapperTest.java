package com.davelabine.resterapp;

import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 10/13/16.
 */
public class ClientWrapperTest {

    @Test
    public void testHelloWorld() {
        ClientWrapper client = new ClientWrapper();
        assertEquals(client.HelloWorld(), HttpStatus.SC_OK);
    }
}
