package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;

import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;
import org.hibernate.cfg.Configuration;

/**
 * Created by davidl on 2/24/17.
 */
public class ConfigModule extends AbstractModule {
    @Getter
    private static final Config awsConfig = ConfigFactory.load("aws");

    @Getter
    private static final Config appConfig = ConfigFactory.load("application.conf");

    @Getter
    private static final Configuration hbnConfig = new Configuration().configure("hibernate.cfg.xml");


    @Override
    protected void configure() {
        bind(Config.class).annotatedWith(Names.named("aws.conf")).toInstance(awsConfig);
        bind(Config.class).annotatedWith(Names.named("application.conf")).toInstance(appConfig);
        bind(Configuration.class).toInstance(hbnConfig);
    }
}
