package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.controller.exceptions.ErrorCodeMainAppException;
import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.typesafe.config.Config;

import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.davelabine.resterapp.module.FreemarkerModule;

import static com.davelabine.resterapp.util.JaxContextUtils.getContentLength;


/**
 * Root resource for the main app
 */
// We only want one instance shared across all servlet threads to make more efficient use of memory.
@Singleton
@Path("/students")
public class ControllerMainApp {
    private static final Logger logger = LoggerFactory.getLogger(ControllerMainApp.class);

    public static final String QUERY_NAME = "name";

    private final Configuration fmConfig;
    private final Config appConfig;
    private final StudentManager studentManager;

    // Convenience variables
    private final String rootUrl;
    private final String createUrl;

    @Inject
    public ControllerMainApp(final Configuration fmConfig,
                             @Named("application.conf")  final Config appConfig,
                             final StudentManager studentManager) {
        this.fmConfig = fmConfig;
        this.appConfig = appConfig;
        this.studentManager = studentManager;

        rootUrl = appConfig.getString("MainApp.rootUrl") + "/students/";
        createUrl = rootUrl + "create/";
    }

    @GET
    @Path("exception/")
    @Produces(MediaType.TEXT_HTML)
    public String throwException() {
        throw new RuntimeException("Good times!");
    }

    /**
     * Get a list of students
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    //TODO: Add some exception mappers
    public String getByName(
            @DefaultValue("")@QueryParam(QUERY_NAME) String name)
            throws IOException, TemplateException {
        logger.info("getByName() - {}", name);

        List<Student> studentList = studentManager.getStudents(name);
        HashMap<String, Object> root = getDefaultHashMap();
        root.put("studentList", studentList);

        return FreemarkerModule.ProcessTemplateUtil(fmConfig, root,"student-list.ftl");
    }


    /**
     * Show a form to create a new student
     */
    @GET
    @Path("create/")
    @Produces(MediaType.TEXT_HTML)
    //TODO: Add some exception mappers
    public String getCreate()
            throws IOException, TemplateException {
        logger.info("getCreate()");

        HashMap<String, Object> root = getDefaultHashMap();
        // Put nothing in student, means there is no existing student
        // We could populate with defaults this way though
        // root.put("student", student);

        // POST to the root URL to create students
        root.put("submitUrl", rootUrl);

        return FreemarkerModule.ProcessTemplateUtil(fmConfig, root,"student-create.ftl");
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public Response createStudent(@Context HttpServletRequest request,
            MultipartFormDataInput multipart)
            throws IOException, TemplateException, URISyntaxException {
        logger.info("createStudent()");

        Response response = parseStudentSubmission(null, multipart, getContentLength(request),
                (student, inputStream) -> {
                    studentManager.createStudent(student, inputStream);
                    String url = rootUrl + "id/" + student.getSkey();
                    logger.info("Redirecting to {}", url);
                    return Response.seeOther(new URI(url)).build();
                });

        return response;
    }

    @POST
    @Path("id/{key}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public Response updateStudent(@Context HttpServletRequest request,
            @NotNull @PathParam("key") String key,
            MultipartFormDataInput multipart)
            throws IOException, TemplateException, URISyntaxException {
        logger.info("updateStudent() - key: {}", key);

        Response response = parseStudentSubmission(key, multipart, getContentLength(request),
                (student, inputStream) -> {
                    studentManager.updateStudent(student);
                    String url = rootUrl + "id/" + key;
                    logger.info("Redirecting to {}", url);
                    return Response.seeOther(new URI(url)).build();
                });

        return response;
    }

    @FunctionalInterface
    interface StudentPostFunction {
        public Response apply(Student student, InputStream in) throws URISyntaxException;
    }

    private Response parseStudentSubmission(String key,
                                            MultipartFormDataInput multipart,
                                            long lContentLength,
                                            StudentPostFunction func)
            throws IOException, URISyntaxException {

        long lMaxSize = appConfig.getLong("Api.max-photo-size");
        if (lContentLength > lMaxSize) {
            logger.warn("too large of a file uploaded: " + lContentLength + "/" + lMaxSize);
            throw new ErrorCodeMainAppException(fmConfig, Response.Status.BAD_REQUEST, "Submitted file too large!");
        }

        Student student = new Student(key,
                multipart.getFormDataPart("id", String.class, null),
                multipart.getFormDataPart("name", String.class, null));
        if ( student.getId().isEmpty() || student.getName().isEmpty() ) {
            throw new ErrorCodeMainAppException(fmConfig, Response.Status.BAD_REQUEST, "Student Id or name empty!");
        }

        try {
            InputStream in = multipart.getFormDataPart("photo", InputStream.class, null);
            return func.apply(student, in);
        } catch (IOException e) {
            logger.error("Error processing photo input stream: ", e.getMessage());
            throw new ErrorCodeMainAppException(fmConfig, Response.Status.INTERNAL_SERVER_ERROR, "Error processing photo!");
        }
    }

    @GET
    @Path("delete/")
    @Produces(MediaType.TEXT_HTML)
    //TODO: Figure out how to add a path for /student/delete
    //TODO: Add some exception mappers
    public String getDelete()
            throws IOException, TemplateException {
        logger.info("getDelete()");

        HashMap<String, Object> root = getDefaultHashMap();

        return FreemarkerModule.ProcessTemplateUtil(fmConfig, root,"student-del.ftl");
    }

    @GET
    @Path("id/{key}/")
    @Produces(MediaType.TEXT_HTML)
    public String getStudent(
            @NotNull @PathParam("key") String key)
            throws IOException, TemplateException {
        logger.info("getStudent() - key: {}, edit: {}", key);

        HashMap<String, Object> root = getDefaultHashMap();
        Student student = studentManager.getStudent(key);
        logger.info("getStudent returned {}", student);
        // null is a legal result and meas no student was found.
        root.put("student", student);
        root.put("photoUrl", studentManager.getPhotoUrl(student.getPhoto()));

        return FreemarkerModule.ProcessTemplateUtil(fmConfig, root,"student.ftl");
    }

    @GET
    @Path("id/{key}/edit")
    @Produces(MediaType.TEXT_HTML)
    public String getStudentEdit(
            @NotNull @PathParam("key") String key)
            throws IOException, TemplateException {
        logger.info("getStudentEdit() - key: {}", key);

        HashMap<String, Object> root = getDefaultHashMap();
        Student student = studentManager.getStudent(key);
        logger.info("getStudent returned {}", student);
        // null is a legal result and meas no student was found.
        root.put("student", student);
        root.put("photoUrl", studentManager.getPhotoUrl(student.getPhoto()));

        String submitUrl = rootUrl + "id/" + key;
        String deleteUrl = submitUrl + "/delete";
        logger.info("submitUrl - {}, deleteUrl - {}", submitUrl, deleteUrl);
        root.put("submitUrl", submitUrl);
        root.put("deleteUrl", deleteUrl);

        return FreemarkerModule.ProcessTemplateUtil(fmConfig, root,"student-edit.ftl");
    }

    @POST
    @Path("id/{key}/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response deleteStudent(
            @PathParam("key") String key)
            throws IOException, TemplateException, URISyntaxException {
        logger.info("deleteStudent - key: {}", key);

        Student student = studentManager.getStudent(key);
        logger.info("getStudent returned: {}", student);
        if (student == null) {
            return Response.notModified().build();
        }

        studentManager.deleteStudent(student);

        String url = rootUrl + "delete/";
        logger.info("Redirecting to {}", url);
        return Response.seeOther(new URI(url)).build();
    }

    /* Helper to add any default config or page values we need */
    private HashMap<String, Object> getDefaultHashMap() {
        HashMap<String, Object> root = new HashMap<String,Object>();

        root.put("rootUrl", rootUrl);
        root.put("createUrl", createUrl);

        logger.info("rootUrl: {}, createUrl: {}", rootUrl, createUrl);

        return root;
    }

}
