package com.davelabine.resterapp.module;

import com.davelabine.resterapp.controller.CorsFilter;
import com.davelabine.resterapp.controller.ControllerMainApp;
import com.davelabine.resterapp.controller.ErrorCodeExceptionMapper;
import org.jboss.resteasy.plugins.server.servlet.FilterDispatcher;
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

        //exception mappers
        // TODO: add the custom generic mapper back in when I get farther along with the app
        // bind(GenericExceptionMapper.class);
        bind(ErrorCodeExceptionMapper.class);

        bind(CorsFilter.class);

        //controllers.
        bind(ControllerMainApp.class);
        bind(ControllerRosterAPI.class);
        bind(ControllerStudentsAPI.class);

        //boot up the resteasy dispatcher.
        bind(FilterDispatcher.class).asEagerSingleton();
        filter("/*").through(FilterDispatcher.class);
    }
}
