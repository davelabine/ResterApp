package com.davelabine.resterapp;

//import com.sun.java.swing.plaf.windows.TMSchema;
import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;
//import com.github.patrickianwilson.template.java.web.controllers.ErrorCodeExceptionMapper;
import com.davelabine.resterapp.controller.ControllerRosters;
import com.davelabine.resterapp.controller.ControllerStudents;
import com.google.inject.servlet.ServletModule;

/**
 * Created by pwilson on 3/7/16.
 */
public class WebModule extends ServletModule {

    @Override
    protected void configureServlets() {
        super.configureServlets();

        //message handlers
        //bind(JsonMessageBodyHandler.class);

        //exception mappers
        //bind(ErrorCodeExceptionMapper.class);
        //bind(GenericServerErrorExceptionMapper.class);

        //controllers.
        bind(ControllerRosters.class);
        bind(ControllerStudents.class);

        //boot up the resteasy dispatcher.
        bind(FilterDispatcher.class).asEagerSingleton();
        filter("/*").through(FilterDispatcher.class);
    }
}
