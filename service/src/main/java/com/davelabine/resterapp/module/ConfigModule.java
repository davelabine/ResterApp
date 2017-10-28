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

    @Getter
    private static final Config secretConfig = ConfigFactory.load("secret.conf");

    @Override
    protected void configure() {
        bind(Config.class).annotatedWith(Names.named("aws.conf")).toInstance(awsConfig);
        bind(Config.class).annotatedWith(Names.named("application.conf")).toInstance(appConfig);
        bind(Config.class).annotatedWith(Names.named("secret.conf")).toInstance(secretConfig);
    }

    @Provides
    @Singleton
    Configuration getHbnConfig(@Named("secret.conf") final Config secretConfig) {
        Configuration hbnConfig = new Configuration();
        hbnConfig.configure("hibernate.cfg.xml");

        String uname = secretConfig.getString("Secret.DB_UNAME");
        String pw = secretConfig.getString("Secret.DB_PW");

        if ( (uname != null) && (pw != null) ) {
            hbnConfig.setProperty("hibernate.connection.username", uname.replace("\r",""));
            hbnConfig.setProperty("hibernate.connection.password", pw.replace("\r",""));
        } else {
            logger.error("Hibernate DB username ({}) or password ({}) are not set!  Check secret.conf!");
            throw new RuntimeException("#### Need to set DB credentials!");
        }

        return hbnConfig;
    }
}
