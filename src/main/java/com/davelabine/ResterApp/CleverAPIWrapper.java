package com.davelabine.ResterApp;

import java.net.URI;

import javax.print.DocFlavor;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import javax.inject.Singleton;

/**
 * Created by davidl on 5/23/16.
 */

@Singleton
public class CleverAPIWrapper implements CleverAPI {
    // Base URI the CleverAPIWrapper will call
    public static final String BASE_URI = "https://api.clever.com/v1.1/";

    private ClientConfig    clientConfig;
    private Client          client;
    private WebTarget       baseURI;

    CleverAPIWrapper() {
        clientConfig = new ClientConfig();
        client = ClientBuilder.newClient(clientConfig);
        baseURI = client.target(BASE_URI);
    }

    public int sample()
    {
        WebTarget sURI = client.target("https://api.github.com/users/davelabine");

        /* The long way to do it is below for future reference
         *
        Invocation.Builder invocation = sURI.request();
        Response response = invocation.get();
        System.out.println(response.toString());
        */

        return sURI.request().get(Response.class).getStatus();
    }

}
