package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.platform.api.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import com.google.inject.Singleton;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import com.davelabine.resterapp.module.FreemarkerModule;



/**
 * Root resource for the main app
 */
@Singleton
// We only want one instance shared across all servlet threads to make more efficient use of memory.
@Path("/students")
public class ControllerMainApp {
    public enum EditState {
        EDIT,
        EDIT_SUCCESS;
    }

    private static final Logger logger = LoggerFactory.getLogger(ControllerMainApp.class);

    @Inject
    private Configuration fmConfig;

    @Inject
    private StudentManager studentManager;


    /**
     * Get a list of students
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    //TODO: Add some exception mappers
    public String getByName(
            @DefaultValue("")@QueryParam("name") String name)
            throws IOException, TemplateException {
        logger.info("getByName() - {}", name);

        List<Student> studentList = studentManager.getStudents(name);

        return FreemarkerModule.ProcessTemplateUtil(fmConfig,
                                                    "studentList", studentList,
                                                    "student-list.ftl");
    }

    @GET
    @Path("{key}")
    @Produces(MediaType.TEXT_HTML)
    public String getStudent(
            @PathParam("key") String key,
            @QueryParam("edit") EditState edit)
            throws IOException, TemplateException {
        logger.info("getStudent() - {}", key);

        Student student = studentManager.getStudent(key);

        return FreemarkerModule.ProcessTemplateUtil(fmConfig,
                "student", student,
                "student.ftl");
    }

    @POST
    @Path("{key}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.TEXT_HTML)
    public Response updateStudent(
            @PathParam("key") String key,
            @QueryParam("edit") EditState edit,
            MultipartFormDataInput multipart)
            throws IOException, TemplateException, URISyntaxException {
        logger.info("getStudent() - {}", key);

        // TODO: input checking for null keys, ID and Name can be null though
        Student upStudent = new Student(key,
                                    multipart.getFormDataPart("id", String.class, null),
                                    multipart.getFormDataPart("name", String.class, null));

        try {
            InputStream in = multipart.getFormDataPart("photo", InputStream.class, null);
        } catch (IOException e) {
            logger.error("Error processing photo input stream: ", e.getMessage());
            // Have the exception handlers deal with this.
            throw e;
        }

        studentManager.updateStudent(upStudent);

        return Response.seeOther(new URI("students/" + key + "?edit=" + EditState.EDIT_SUCCESS)).build();
    }

}
