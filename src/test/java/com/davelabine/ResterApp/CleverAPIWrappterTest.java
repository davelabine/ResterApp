package com.davelabine.ResterApp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.AbstractModule;

import org.junit.*;
import static org.junit.Assert.*;


/**
 * Created by davidl on 5/23/16.
 */
public class CleverAPIWrappterTest {

    private CleverAPI clever;
    private Injector injector;

    @Before
    public void setUp() throws Exception {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(CleverAPI.class).to(CleverAPIWrapper.class);
            }
        });
        clever = injector.getInstance(CleverAPI.class);
    }

    @After
    public void tearDown() throws Exception {

    }

    /*
     * Test to see that we can connect to the wrapper;
     */
    @Test
    public void testSample() {
        assertEquals(200, clever.sample());
    }
}
