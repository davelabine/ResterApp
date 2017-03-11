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
import java.io.IOException;
import java.util.List;

import com.davelabine.resterapp.module.FreemarkerModule;

/**
 * Root resource for the main app
 */
@Singleton
// We only want one instance shared across all servlet threads to make more efficient use of memory.
@Path("/students")
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
    @Produces(MediaType.TEXT_HTML)
    //TODO: Add some exception mappers
    public String get(
            @QueryParam("name") String name) throws IOException, TemplateException {
        logger.info("MainAppGet()");

        studentManager.populateFakeData();
        List<Student> studentList = studentManager.getStudents(name);

        return FreemarkerModule.ProcessTemplateUtil(fmConfig,
                                                    "studentList", studentList,
                                                    "WEB-INF/content/student-list.ftl");
    }
}
