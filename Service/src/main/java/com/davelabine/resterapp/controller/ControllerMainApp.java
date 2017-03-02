package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.model.Student;
import com.davelabine.resterapp.service.StudentManager;
import com.google.inject.Singleton;
import freemarker.core.ParseException;
import freemarker.template.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * Root resource for the main app
 */
@Singleton
// We only want one instance shared across all servlet threads to make more efficient use of memory.
@Path("/app")
public class ControllerMainApp {
    private static final Logger logger = LoggerFactory.getLogger(ControllerMainApp.class);

    @Inject
    private Configuration fmConfig;

    @Inject
    private StudentManager studentManager;

    /**
     * Get a list of students
     */
    @GET
    @Path("/")
    @Produces(MediaType.TEXT_HTML)
    public Response get() {
        logger.info("MainAppGet()");

        /*
        studentManager.populateFakeData();
        List<Student> studentList = studentManager.getStudents();
        if (studentList == null) {
            logger.error("getStudents() failed");
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).build();
        }

        logger.info("Students retrieved successfully:{}", studentList);

        fmConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        StringWriter out = new StringWriter();

        try {
            Template template = fmConfig.getTemplate("WEB-INF/content/student-list.ftl");
            SimpleHash root = new SimpleHash();
            root.put("studentList", studentList);
            template.process(root, out);
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */

        return Response.ok().entity("I'm A-OK!").build();
    }
}
