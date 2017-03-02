package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;

import com.google.inject.name.Names;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;

/**
 * Created by davidl on 2/24/17.
 */
public class ConfigModule extends AbstractModule {
    @Getter
    private static final Config awsConfig = ConfigFactory.load("aws");

    @Override
    protected void configure() {
        bind(Config.class).annotatedWith(Names.named("aws.conf")).toInstance(awsConfig);
    }
}
