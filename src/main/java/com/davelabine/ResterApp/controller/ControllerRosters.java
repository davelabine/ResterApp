package com.davelabine.resterapp.controller;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * Root resource (exposed at "roster" path)
 */
@Path("/roster")
public class ControllerRosters {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTextPlainRoster() {
        return "Text Roster!";
    }

    // This method is called if XML is request
    @GET
    @Produces(MediaType.TEXT_XML)
    public String GetXMLRoster() {
        return "<?xml version=\"1.0\"?>" + "<Roster>A Roster in XML!</Roster>";
    }
}

