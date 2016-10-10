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
import static org.junit.Assert.assertTrue;

/**
 * Created by davidl on 10/10/16.
 */
public class ControllerStudentsTest {
    // @InjectMocks - don't think we need this
    private Dispatcher dispatcher;

    @Before
    public void before() throws Exception {
        this.dispatcher = MockDispatcherFactory.createDispatcher();
        POJOResourceFactory noDefaults = new POJOResourceFactory(ControllerStudents.class);
        this.dispatcher.getRegistry().addResourceFactory(noDefaults);
    }

    @Test
    public void getStudentTest() throws URISyntaxException {
        String queryParam = "1234";
        String uri = "/students/" + queryParam;

        MockHttpRequest request = MockHttpRequest.get(uri);
        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(response.getStatus(), HttpStatus.SC_OK);
        assertTrue(response.getContentAsString().contains(queryParam));
    }

}
