package com.davelabine.resterapp.controller;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Root resource (exposed at "roster" path)
 *
 * I plan to expand this later by uploading a OneRoster-compliant CSV and processing it via SQS Queue
 * For now, just define some basic placeholder resources.
 *
 */
@Singleton
@Path("/api/roster")
public class ControllerRosterAPI {
    public static final String ROSTER_TEXT_PLAIN = "Text Roster!";
    public static final String ROSTER_TEXT_XML = "<?xml version=\"1.0\"?><Roster>A Roster in XML!</Roster>";

    private static final Logger logger = LoggerFactory.getLogger(ControllerRosterAPI.class);

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getTextPlainRoster() {
        logger.info("getTextPlainRoster");
        return ROSTER_TEXT_PLAIN;
    }

    // This method is called if XML is request
    @GET
    @Produces(MediaType.TEXT_XML)
    public String GetXMLRoster() {
        logger.info("getXMLRoster");
        return ROSTER_TEXT_XML;
    }
}

