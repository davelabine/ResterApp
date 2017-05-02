package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.davelabine.resterapp.platform.api.model.Student;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by davidl on 4/20/17.
 */
@Singleton
public class HbnTxManager {
    private static final Logger logger = LoggerFactory.getLogger(HbnTxManager.class);

    private final Configuration hbnConfig;
    private final ServiceRegistry hbnRegistry;
    private SessionFactory hbnSessionFactory;

    @Inject
    public HbnTxManager(Configuration hbnConfig, ServiceRegistry hbnRegistry) {
        this.hbnConfig = hbnConfig;
        this.hbnRegistry = hbnRegistry;
    }

    public void initialize() {
        logger.info("Initialize");
        try {
            hbnSessionFactory = hbnConfig.buildSessionFactory(hbnRegistry);
        } catch (HibernateException e) {
            throw new DaoException(("Initialize failed!"), e);
        }
    }

    public void close() {
        hbnSessionFactory.close();
    }

    /**
     * Process a Hibernate transaction using a generic function, properly wrapping exceptions in DaoException.
     * @param  A - input param
     * @return  R - return param
     */
    @FunctionalInterface
    interface HbnTxFunction<A, R> {
        public R apply(A arg, Session session);
    }

    public <A, R> R processTx(HbnTxFunction<A, R> func, A arg) {
        if ( (func == null) || (arg == null) ) {
            throw new DaoException("processTx null arg.");
        }
        logger.info("process {}", arg);

        R ret = null;
        Session session = null;
        Transaction transaction = null;
        try {
            session = hbnSessionFactory.getCurrentSession();
            transaction = session.getTransaction();
            transaction.begin();
            ret = func.apply(arg, session);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("processTx hibernate exception: ", e);
        } catch (RuntimeException e) {
            int i=0;
        } finally {
            // When getCurrentSession is used, close() is handled automatically
            //session.close();
        }
        return ret;
    }

}
