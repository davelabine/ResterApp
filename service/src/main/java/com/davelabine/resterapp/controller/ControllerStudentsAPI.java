package com.davelabine.resterapp.controller;

/**
 * Created by davidl on 9/29/16.
 */
import com.davelabine.resterapp.service.StudentManager;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.util.Busywork;
import com.google.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.*;
import javax.validation.constraints.NotNull;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import java.util.List;


/**
 * Root resource for student information (exposed at "students" path)
 */
@Singleton
// We only want one instance shared across all servlet threads to make more efficient use of memory.
@Path("/api/students")
public class ControllerStudentsAPI {
    // TODO: get config working so I don't have to hardcode stuff like this
    private static final String STUDENTS_ENDPOINT_BASE_PATH = "http://localhost:8080/resterapp/students/";
    private static final String STUDENT_KEY_HEADER = "student-key";

    private static final String QUERY_PARAM_BUSYTIME = "busyTime";
    private static final String QUERY_PARAM_NAME = "name";

    private static final Logger logger = LoggerFactory.getLogger(ControllerStudentsAPI.class);

    @Inject
    private StudentManager studentManager;

    /**
     * Method for creating students.
     * Take a some student metadata and return a unique ID
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(
            @QueryParam(QUERY_PARAM_BUSYTIME) int busyTime,
            @NotNull Student student)
            throws URISyntaxException {
        logger.info("Students/post busyTime:{} Student:{}", busyTime, student);

        if ( (student.getName() == null) || (student.getId() == null) ) {
            logger.info("Student name or ID is null");
            return Response.status(BAD_REQUEST).type(MediaType.APPLICATION_JSON).build();
        }

        Busywork.doBusyWork(busyTime);

        // TODO: Add profile photo
        String studentKey = studentManager.createStudent(student, null);

        if (studentKey == null) {
            logger.error("Student create failed");
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        URI retURI = new URI(STUDENTS_ENDPOINT_BASE_PATH + studentKey);
        logger.info("Student created successfully:{}", retURI.toString());
        return Response.created(retURI)
                .header(STUDENT_KEY_HEADER, studentKey).build();
    }

    /**
     * Get a list of students
     */
    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getByName(
            @DefaultValue("")@QueryParam(QUERY_PARAM_NAME) String name,
            @QueryParam(QUERY_PARAM_BUSYTIME) int busyTime){
        logger.info("name: {},  busyTime:{} ", name, busyTime);

        Busywork.doBusyWork(busyTime);
        List<Student> studentList = studentManager.getStudents(name);
        // A null result is acceptable - no students match the search
        logger.info("Students retrieved successfully:{}", studentList);
        return Response.ok().entity(studentList).build();
    }

    /**
     * Get the metadata of an uploaded resource.
     */
    @GET
    @Path("{key}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(
            @QueryParam(QUERY_PARAM_BUSYTIME) int busyTime,
            @NotNull @PathParam("key") String key) {
        logger.info("Student:{} busyTime:{}", key, busyTime);

        Busywork.doBusyWork(busyTime);

        Student studentGet = studentManager.getStudent(key);
        if (studentGet == null) {
            logger.info("Student not found");
            return Response.status(NOT_FOUND).type(MediaType.APPLICATION_JSON).build();
        }

        logger.info("Student found: {}", studentGet);
        return Response.ok().entity(studentGet).build();
    }

    /**
     * Update an existing resource.
     */
    @POST
    @Path("{key}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(
            @QueryParam(QUERY_PARAM_BUSYTIME) int busyTime,
            @NotNull @PathParam("key") String key,
            @NotNull Student student)
            throws URISyntaxException {
        logger.info("Students/post busyTime:{} Student:{}", busyTime, student);

        if ( (student.getName() == null) || (student.getId() == null) ) {
            logger.info("Student name or ID is null");
            return Response.status(BAD_REQUEST).type(MediaType.APPLICATION_JSON).build();
        }

        Busywork.doBusyWork(busyTime);

        // TODO: Add profile photo
        student.setKey(key);
        studentManager.updateStudent(student);

        return Response.ok().build();
    }

    @POST
    @Path("/populate")
    public Response populate(
            @DefaultValue("1") @QueryParam("num") int numToPopulate) {
        logger.info("Populating {} students...", numToPopulate);

        studentManager.populateFakeData(numToPopulate);
        logger.info("Done populating");
        return Response.created(null).build();
    }

}