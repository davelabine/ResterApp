package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by dave on 4/25/17.
 */
@SuppressWarnings("unchecked")
public class HbnTxManagerTest {
    HbnTxManager underTest;

    /*
     * In general, we want to verify this class is passing exceptions back properly.
     * Unit testing happy cases is basically unit testing Hibernate, so don't bother
     * Instead, cover happy cases in our integration tests.
     */

    @Before
    public void before() {
        // These configs are empty.
        // We don't need them because we're only testing our validation in the processTx function.
        underTest = new HbnTxManager(null, null);
    }

    @Test(expected=DaoException.class)
    public void testProcessTxNullFunc() {
        underTest.processTx(null, "");
    }

    @Test(expected=DaoException.class)
    public void testProcessTxNullArg() {
        underTest.processTx((a, b) -> {
            return null;
        }, null);
    }
}
