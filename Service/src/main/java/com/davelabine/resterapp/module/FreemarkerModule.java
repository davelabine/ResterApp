package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import com.davelabine.resterapp.controller.ControllerMainApp;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

/**
 * Created by dave on 3/1/17.
 */
public class FreemarkerModule extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    @Singleton
    Configuration getFreemarkerConfig(@Context ServletContext context) {
        Configuration fmConfig = new Configuration(Configuration.VERSION_2_3_23);
        fmConfig.setServletContextForTemplateLoading(context, "/");
        fmConfig.setDefaultEncoding("UTF-8");
        fmConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return fmConfig;
    }
}

