package com.davelabine.ResterApp;


import org.junit.*;
import static org.junit.Assert.*;


/**
 * Created by davidl on 5/23/16.
 */
public class CleverAPIWrappterTest {

    private CleverAPIWrapper clever;


    @Before
    public void setUp() throws Exception {
        clever = new CleverAPIWrapper();
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
