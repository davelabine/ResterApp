package com.davelabine.resterapp.controller;

import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidl on 10/10/16.
 *
 * Test Plan:
 * - For now these are a placeholder that I plan to expand on later.
 * - Make sure GETs are returning the correct document types based on HTTP headers.
 */
@RunWith(MockitoJUnitRunner.class)
public class ControllerRosterTest {

    // TODO: Why is new appropriate here instead of @Inject?
    // I suspect because we want a real controller and not mocked out dependencies.
    // Usually Guice would instantiate this for us, we could try doing this with @Inject?
    @InjectMocks
    private ControllerRoster controllerRoster;

    @BeforeClass
    public static void beforeClass() throws Exception {

    }
    @AfterClass
    public static void afterClass() throws Exception {

    }


    @Test
    public void getBasicRoster() throws URISyntaxException {
        Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
        POJOResourceFactory noDefaults = new POJOResourceFactory(ControllerRoster.class);
        dispatcher.getRegistry().addResourceFactory(noDefaults);

        MockHttpRequest request = MockHttpRequest.get("/roster");
        MockHttpResponse response = new MockHttpResponse();

        dispatcher.invoke(request, response);

        assertEquals(response.getContentAsString(), ControllerRoster.ROSTER_TEXT_PLAIN);
    }

    /*
    @Test
    public void getBlobStoreStatus() {
        reset(mockStatusService);
        when(mockStatusService.getBlobstoreStatus()).thenReturn(new HealthStatus());
        statusController.getBlobStoreHealthCheck();
        verify(mockStatusService).getBlobstoreStatus();
    }

    @Test
    public void getDatabaseStatus() {
        reset(mockStatusService);
        when(mockStatusService.getDatabaseStatus()).thenReturn(new HealthStatus());
        statusController.getDatabaseHealthCheck();
        verify(mockStatusService).getDatabaseStatus();
    }
    */
}
