package com.davelabine.resterapp;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.Response;

// import org.glassfish.jersey.client.ClientConfig;

/*
/**
 * Created by davidl on 5/23/16.
 */

/*
public class CleverAPIWrapper implements CleverAPI {

    // Base URI the CleverAPIWrapper will call
    public static final String BASE_URI = "https://api.clever.com/v1.1/";

    private ClientConfig    clientConfig;
    private Client          client;
    private WebTarget       baseURI;

    @Inject
    CleverAPIWrapper() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        baseURI = client.target(BASE_URI);
    }

    public int sample()
    {
        //WebTarget sURI = client.target("https://api.github.com/users/davelabine");

        // The long way to do it is below for future reference
        // Invocation.Builder invocation = sURI.request();
        // Response response = invocation.get();
        // System.out.println(response.toString());

        //return sURI.request().get(Response.class).getStatus();
    }

}
*/