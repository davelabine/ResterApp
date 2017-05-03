package com.davelabine.resterapp.controller;

import com.davelabine.resterapp.module.FreemarkerModule;
import freemarker.template.*;
import com.davelabine.resterapp.controller.exceptions.ErrorCodeMainAppException;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.typesafe.config.Config;


import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.HashMap;


/**
 * Created by davidl on 5/3/17.
 */
@Provider
public class ErrorCodeExceptionMapper implements ExceptionMapper<ErrorCodeMainAppException> {
    private Config appConfig;

    // TODO: inject freemarker config properly in ErrorCodeExceptionMapper
    // Yes, this is ugly.
    // There is an issue injecting eager singletons for FM config into multiple objects.  Didn't have time to get to
    // the bottom of what is happening and it seems pretty ugly...

    @Inject
    public ErrorCodeExceptionMapper(final Configuration fmConfig,
                                    @Named("application.conf") final Config appConfig)
    {
        this.appConfig = appConfig;
    }


    @Override
    public Response toResponse(ErrorCodeMainAppException  e) {
        HashMap<String, Object> root = new HashMap<String,Object>();
        root.put("body", e.getBody());
        root.put("status", e.getStatus());
        root.put("message", e.getMessage());
        root.put("rootUrl", appConfig.getString("MainApp.rootUrl") + "/students/");
        String html = null;
        try {
            html = FreemarkerModule.ProcessTemplateUtil(e.getFmConfig(), root,"error.ftl");
        }
        catch (IOException io) {
            html = "Freemarker IOException: " + io.getMessage() + " ErrorCodeMainAppException: " + e.getMessage();
        } catch (TemplateException t) {
            html = "Freemarker TemplateException: " + t.getMessage() + " ErrorCodeMainAppException: " + e.getMessage();
        }
        return Response.status(e.getStatus()).entity(html).build();
    }
}
