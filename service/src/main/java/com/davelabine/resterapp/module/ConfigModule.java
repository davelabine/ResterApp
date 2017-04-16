package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.getenv;

/**
 * Created by davidl on 2/24/17.
 */
public class ConfigModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(ConfigModule.class);
    private static final String DB_ENV_UNAME = "DB_ENV_UNAME";
    private static final String DB_ENV_PW = "DB_ENV_PW";

    @Getter
    private static final Config awsConfig = ConfigFactory.load("aws");

    @Getter
    private static final Config appConfig = ConfigFactory.load("application.conf");

    @Override
    protected void configure() {
        bind(Config.class).annotatedWith(Names.named("aws.conf")).toInstance(awsConfig);
        bind(Config.class).annotatedWith(Names.named("application.conf")).toInstance(appConfig);
    }

    @Provides
    @Singleton
    Configuration getHbnConfig() {
        Configuration hbnConfig = new Configuration();
        hbnConfig.configure("hibernate.cfg.xml");
        String uname = System.getenv(DB_ENV_UNAME).replace("\r","");
        String pw = System.getenv(DB_ENV_PW).replace("\r","");

        if (!uname.isEmpty() && !pw.isEmpty()) {
            hbnConfig.setProperty("hibernate.connection.username", uname);
            hbnConfig.setProperty("hibernate.connection.password", pw);
        } else {
            logger.error("Hibernate DB username ({}) or password ({}) are not set!  Set these environment variables.",
                            DB_ENV_UNAME, DB_ENV_PW);
        }

        return hbnConfig;
    }
}
