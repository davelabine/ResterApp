package com.davelabine.resterapp.controller;

/**
 * Created by davidl on 9/29/16.
 */
import com.davelabine.resterapp.service.StudentManager;
import com.davelabine.resterapp.model.Student;
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
    public Response create(String studentJsonData) throws URISyntaxException {

        //Student newStudent = gson.fromJson(studentJsonData, Student.class);

        // TODO: Need to return created with the resource ID of the student in student-key

        // Create a random student for now
        Student test = new Student("12345", "Billy Bob");
        String studentKey = studentManager.createStudent(test);
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
            @PathParam("id")
            String id) {
        return Response.ok("The param you sent me was + " + id).build();
    }

}