package com.davelabine.ResterApp;

import com.google.inject.AbstractModule;

/**
 * Created by dave on 10/1/16.
 */
public class ResterAppInjector extends AbstractModule {

    @Override
    protected void configure() {
        // TODO not sure I even need this.
        //bind the service to implementation class
        //bind(MessageService.class).to(EmailService.class);
    }
}
