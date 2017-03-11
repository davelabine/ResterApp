package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import freemarker.template.*;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import java.io.IOException;
import java.io.StringWriter;

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
        fmConfig.setServletContextForTemplateLoading(context, "WEB-INF/content/");
        fmConfig.setDefaultEncoding("UTF-8");
        fmConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        return fmConfig;
    }

    // * A simple helper method for processing Freemarker templates across multiple controllers
    public static String ProcessTemplateUtil(Configuration configuration, String dataName, Object data,
                                             String templateName)
            throws IOException, TemplateException {
        StringWriter out = new StringWriter();
        SimpleHash root = new SimpleHash();

        if (data != null) {
            root.put(dataName, data);
        }

        Template template = configuration.getTemplate(templateName);
        template.process(root, out);
        return out.toString();
    }
}

