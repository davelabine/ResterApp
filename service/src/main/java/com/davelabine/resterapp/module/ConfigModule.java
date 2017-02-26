package com.davelabine.resterapp.module;

import com.google.inject.AbstractModule;


import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import lombok.Getter;

/**
 * Created by davidl on 2/24/17.
 */
public class ConfigModule extends AbstractModule {
    @Getter
    private static final Config awsConfig = ConfigFactory.load("aws.conf");

    @Override
    protected void configure() {
    }
}
