package com.davelabine.resterapp;

import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.Response;


/*
/**
 * Created by davidl on 5/23/16.
 */


public class ClientWrapper {
    private Client client;

    @Inject
    ClientWrapper() {
        client = ClientBuilder.newBuilder().build();
    }

    public int HelloWorld()
    {
        WebTarget sURI = client.target("https://www.google.com");

        // The short way to do it for future reference
        // sURI.request().get(Response.class).getStatus()
        Response response = sURI.request().get();
        String value = response.readEntity(String.class);
        int retStatus = response.getStatus();
        response.close();  // You should close connections!

        return retStatus;
    }

}