package com.davelabine.resterapp.controller;

/**
 * Created by davidl on 9/29/16.
 */
import com.davelabine.resterapp.service.StudentManager;
import com.davelabine.resterapp.model.Student;
import com.davelabine.resterapp.util.Busywork;
import com.google.common.collect.ConcurrentHashMultiset;
import com.google.gson.Gson;
import com.google.inject.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

/**
 * Root resource for student information (exposed at "students" path)
 */
@Singleton
@Path("/students")
public class ControllerStudents {
    private static final String ENDPOINT_BASE_PATH_REGEX = "%s/students/%s";
    private static final String STUDENT_KEY_HEADER = "student-key";

    private static final int BUSYTIME_MS = 200; // Milliseconds

    private Gson gson = new Gson();

    @Inject
    private StudentManager studentManager;

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
    public Response create(
            @QueryParam("getBusy") boolean getBusy,
            Student student)
            throws URISyntaxException {


        if (getBusy) {
            Busywork.getBusy(BUSYTIME_MS);
        }

        String studentKey = studentManager.createStudent(student);

        if (studentKey == null) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        URI retURI = new URI("http://localhost","/Student/",studentKey);
        return Response.created(retURI).build();
    }

    /**
     * Get the metadata of an uploaded resource.
     */
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @QueryParam("getBusy") boolean getBusy,
            @PathParam("id")
            String id) {

        // TODO: try to get not empty to work

        if (getBusy) {
            Busywork.getBusy(BUSYTIME_MS);
        }

        Student studentGet = studentManager.getStudent(id);
        if (studentGet == null) {
            return Response.status(Response.Status.NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
        }

        return Response.ok().entity(studentGet).build();
    }

}