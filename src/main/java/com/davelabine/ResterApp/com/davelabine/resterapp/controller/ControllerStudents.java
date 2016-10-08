package com.davelabine.ResterApp.com.davelabine.resterapp.controller;

/**
 * Created by davidl on 9/29/16.
 */

import com.google.inject.Inject;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;

/**
 * Root resource for student information (exposed at "students" path)
 */
@Path("/students")
public class ControllerStudents {
    private static final String ENDPOINT_BASE_PATH_REGEX = "%s/students/%s";

    private Gson gson = new Gson();

    private final StudentManager studentManager = new StudentManager();

    /* TODO - Figure out when it is appropriate to inject dependencies
    @Inject
    public ControllerStudents(final StudentManager studentManager) {
        this.studentManager = studentManager;
    }
    */


    /**
     * Method for creating students.
     * Take a some student metadata and return a unique ID
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(String studentJsonData) throws URISyntaxException {

        //Student newStudent = gson.fromJson(studentJsonData, Student.class);
        URI retURI = new URI(ENDPOINT_BASE_PATH_REGEX, "FIXME", "FIXME");

        return Response.created(retURI).build();
    }

    /**
     * Get the metadata of an uploaded resource.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @PathParam("id")
            String id) {
        return Response.ok("The param you sent me was + " + id).build();
    }

}