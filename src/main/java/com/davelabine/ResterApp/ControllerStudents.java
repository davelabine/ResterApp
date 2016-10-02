package com.davelabine.ResterApp;

/**
 * Created by davidl on 9/29/16.
 */

import com.google.inject.Inject;
import io.swagger.annotations.ApiOperation;

import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Root resource for student information (exposed at "students" path)
 */
@Path("/students")
public class ControllerStudents {
    private static final String ENDPOINT_BASE_PATH_REGEX = "%s/students/%s";
    //private Gson gson = new Gson();

    private final StudentManager studentManager;

    @Inject
    public ControllerStudents(final StudentManager studentManager) {
        this.studentManager = studentManager;
    }


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
        URI retURI = new URI(ENDPOINT_BASE_PATH_REGEX, Main.BASE_URI, "FIXME");
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