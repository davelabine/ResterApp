package com.davelabine.resterapp.platform.dao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamoTest {
    DaoStudentDynamo dynamo;

    @Before
    public void before() {
        dynamo = new DaoStudentDynamo();
    }

    @Test
    public void testCreateTable() {
        dynamo.createTable("movies");
    }
}


