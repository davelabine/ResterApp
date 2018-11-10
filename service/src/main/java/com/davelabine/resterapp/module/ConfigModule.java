package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;

import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by davidl on 2/24/17.
 */
public class ConfigModule extends AbstractModule {
    private static final Logger logger = LoggerFactory.getLogger(ConfigModule.class);

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

        String url = System.getenv("DB_URL");
        String uname = System.getenv("DB_UNAME");
        String pw = System.getenv("DB_PW");

        if ( (uname != null) && (pw != null) && (url != null) ) {
            hbnConfig.setProperty("hibernate.connection.url", url.replace("\r",""));
            hbnConfig.setProperty("hibernate.connection.username", uname.replace("\r",""));
            hbnConfig.setProperty("hibernate.connection.password", pw.replace("\r",""));
        } else {
            logger.error("Environment variables DB URL, DB_UNAME, or DB_PW are not set!");
            throw new RuntimeException("#### Need to set DB credentials!");
        }

        // Drop the existing tables and create new one
        // Should be set to "create", or not set to keep existing tables
        String create = System.getenv("DB_HBM2DDL_AUTO");
        if ( create != null ) {
            hbnConfig.setProperty("hibernate.hbm2ddl.auto", create.replace("\r",""));
            logger.warn("Dropping and recreating resterapp tables");
        }

        return hbnConfig;
    }
}
