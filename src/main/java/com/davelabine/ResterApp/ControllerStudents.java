package com.davelabine.ResterApp;

/**
 * Created by davidl on 9/29/16.
 */

import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

/**
 * Root resource for student information (exposed at "students" path)
 */
@Path("/students")
public class ControllerStudents {
    private static final String ENDPOINT_BASE_PATH_REGEX = "%s/students/%s";

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
    public Response create() throws URISyntaxException{
        String studentKey = UUID.randomUUID().toString();
        URI retURI = new URI(ENDPOINT_BASE_PATH_REGEX, "TODO-ServerURL", studentKey);
        return Response.created(retURI).build();
    }

    /**
     * Get the metadata of an uploaded resource.
     */
    @GET
    @Path("/{id}")
    @ApiOperation(value = "Get metadata for a student.")
    @Produces(MediaType.APPLICATION_JSON)

    public Response get(
            @PathParam("id")
            String id) {
        return Response.ok().build();
    }

}