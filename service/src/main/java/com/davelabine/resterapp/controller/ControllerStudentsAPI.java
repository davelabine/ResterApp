package com.davelabine.resterapp.controller;

/**
 * Created by davidl on 9/29/16.
 */
import com.davelabine.resterapp.service.StudentManager;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.util.Busywork;
import com.davelabine.resterapp.util.JaxContextUtils;
import com.google.inject.Singleton;

import com.typesafe.config.Config;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static com.davelabine.resterapp.util.JaxContextUtils.*;
import static javax.ws.rs.core.Response.Status.*;
import javax.validation.constraints.NotNull;

import java.io.IOException;
import java.io.InputStream;
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
    public static final String QUERY_PARAM_BUSYTIME = "busyTime";
    public static final String QUERY_PARAM_NAME = "name";

    public static final String FORM_STUDENT_NAME = "name";
    public static final String FORM_STUDENT_ID = "id";
    public static final String FORM_STUDENT_PHOTO = "photo";

    private static final Logger logger = LoggerFactory.getLogger(ControllerStudentsAPI.class);

    private final Config appConfig;
    private final StudentManager studentManager;

    @Inject
    public ControllerStudentsAPI(@Named("application.conf") final Config appConfig,
                             final StudentManager studentManager) {
        this.appConfig = appConfig;
        this.studentManager = studentManager;
    }

    /**
     * Method for creating students.
     * Take a some student metadata and return a unique ID
     *
     * @return String that will be returned as a text/plain response.
     */
    @POST
    @Path("/create")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public Response create(@Context HttpServletRequest request,
            @NotNull MultipartFormDataInput formDataInput,
            @QueryParam(QUERY_PARAM_BUSYTIME) int busyTime)
            throws IOException, URISyntaxException {
        logger.info("Students/post busyTime:{} ", busyTime);

        long lMaxSize = appConfig.getLong("Api.max-photo-size");
        long lContentLength = getContentLength(request);
        if (lContentLength > lMaxSize) {
            logger.warn("too large of a file uploaded: " + lContentLength+ "/" + lMaxSize);
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Student student = new Student(
                formDataInput.getFormDataPart(FORM_STUDENT_ID, String.class, null),
                formDataInput.getFormDataPart(FORM_STUDENT_NAME, String.class, null));
        if ( (student.getName() == null) || (student.getId() == null) ) {
            logger.info("Student name or ID is null");
            return Response.status(BAD_REQUEST).build();
        }
        logger.info("Student:{} ", student);

        InputStream in = formDataInput.getFormDataPart(FORM_STUDENT_PHOTO, InputStream.class, null);

        String studentKey = studentManager.createStudent(student, in);
        if (studentKey == null) {
            logger.error("Student create failed");
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        Busywork.doBusyWork(busyTime);

        URI retURI = new URI(appConfig.getString("Api.rootUrl") + studentKey);
        logger.info("Student created successfully:{}", retURI.toString());
        return Response.created(retURI)
                .header(appConfig.getString("App.student-key-header"), studentKey).build();
    }


    /**
     * Update an existing resource.
     */
    @POST
    @Path("{key}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@Context HttpServletRequest request,
                                  @NotNull MultipartFormDataInput formDataInput,
                                  @QueryParam(QUERY_PARAM_BUSYTIME) int busyTime,
                                  @NotNull @PathParam("key") String key)
            throws IOException, URISyntaxException {
        logger.info("Students/post busyTime:{} Student:{}", busyTime);

        Student student = new Student(
                formDataInput.getFormDataPart(FORM_STUDENT_ID, String.class, null),
                formDataInput.getFormDataPart(FORM_STUDENT_NAME, String.class, null));
        student.setSkey(key);
        if ( (student.getName() == null) || (student.getId() == null) ) {
            logger.info("Student name or ID is null");
            return Response.status(BAD_REQUEST).build();
        }
        logger.info("Student:{} ", student);

        InputStream in = formDataInput.getFormDataPart(FORM_STUDENT_PHOTO, InputStream.class, null);

        Busywork.doBusyWork(busyTime);

        studentManager.updateStudent(student, in);

        return Response.ok().build();
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