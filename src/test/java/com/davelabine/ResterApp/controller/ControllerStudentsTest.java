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

    private Dispatcher dispatcher;

    @Before
    public void before() throws Exception {
        // TODO - need to find a way to construct these objects via Guice DI
        this.dispatcher = MockDispatcherFactory.createDispatcher();
        POJOResourceFactory noDefaults = new POJOResourceFactory(ControllerStudents.class);
        this.dispatcher.getRegistry().addResourceFactory(noDefaults);
    }

    @Test
    public void postStudentTest() throws URISyntaxException {
        /*
        String content = new String("{\"StudentName\":\"Joe Blough\"}");

        MockHttpRequest request = MockHttpRequest.post("/students/post")
                                                .content(content.getBytes())
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .accept(MediaType.APPLICATION_JSON);

        MockHttpResponse response = new MockHttpResponse();
        dispatcher.invoke(request, response);

        assertEquals(response.getStatus(), HttpStatus.SC_CREATED);
        */
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
