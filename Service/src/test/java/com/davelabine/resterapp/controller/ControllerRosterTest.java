package com.davelabine.resterapp.controller;

import org.apache.http.HttpStatus;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by davidl on 10/10/16.
 *
 * Test Plan:
 * - For now these are a placeholder that I plan to expand on later.
 * - Make sure GETs are returning the correct document types based on HTTP headers.
 */
public class ControllerRosterTest {

    // @InjectMocks - don't think we need this
    private Dispatcher dispatcher;

    @Before
    public void before() throws Exception {
        this.dispatcher = MockDispatcherFactory.createDispatcher();
        POJOResourceFactory noDefaults = new POJOResourceFactory(ControllerRoster.class);
        this.dispatcher.getRegistry().addResourceFactory(noDefaults);
    }

    @Test
    public void getTextPlainRosterTest() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/roster").accept(MediaType.TEXT_PLAIN);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(response.getStatus(), HttpStatus.SC_OK);
        assertEquals(response.getContentAsString(), ControllerRoster.ROSTER_TEXT_PLAIN);
    }

    @Test
    public void getTextXMLRosterTest() throws URISyntaxException {
        MockHttpRequest request = MockHttpRequest.get("/roster").accept(MediaType.TEXT_XML);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(response.getStatus(), HttpStatus.SC_OK);
        assertEquals(response.getContentAsString(), ControllerRoster.ROSTER_TEXT_XML);
    }

}
