package com.davelabine.resterapp.module;

//import com.sun.java.swing.plaf.windows.TMSchema;
import com.davelabine.resterapp.controller.ControllerMainApp;
import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;
//import com.github.patrickianwilson.template.java.web.controllers.ErrorCodeExceptionMapper;
import com.davelabine.resterapp.controller.ControllerRosterAPI;
import com.davelabine.resterapp.controller.ControllerStudentsAPI;
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
        bind(ControllerMainApp.class);
        bind(ControllerRosterAPI.class);
        bind(ControllerStudentsAPI.class);

        //boot up the resteasy dispatcher.
        bind(FilterDispatcher.class).asEagerSingleton();
        filter("/*").through(FilterDispatcher.class);
    }
}
