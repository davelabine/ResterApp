package com.davelabine.resterapp.module;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.davelabine.resterapp.platform.api.dao.DaoStudent;
import com.davelabine.resterapp.platform.api.service.BlobStoreService;
import com.davelabine.resterapp.platform.dao.DaoStudentHbn;
import com.davelabine.resterapp.platform.service.S3BlobStoreService;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import lombok.Getter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.Service;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by davidl on 3/10/17.
 */
public class HibernateModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DaoStudent.class).to(DaoStudentHbn.class);
    }

    @Provides
    @Singleton
    @Inject
    ServiceRegistry getServiceRegistry(Configuration hbnConfig) {
        return new StandardServiceRegistryBuilder().applySettings(hbnConfig.getProperties()).build();
    }
}
