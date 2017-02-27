package com.davelabine.resterapp;

import java.util.List;
import javax.servlet.ServletContext;

import com.davelabine.resterapp.platform.module.AwsModule;
import com.davelabine.resterapp.module.WebModule;
import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import com.google.common.collect.Lists;
import com.google.inject.Module;

/**
 * Created by pwilson on 3/7/16.
 */
public class ApplicationContextListener extends GuiceResteasyBootstrapServletContextListener {

    @Override
    protected List<Module> getModules(ServletContext context) {
        return Lists.newArrayList(new AwsModule(), (Module) new WebModule());
    }


}
