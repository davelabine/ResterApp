package com.davelabine.resterapp.integration.platform;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.davelabine.resterapp.platform.dao.DaoStudentDynamo;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamoItTest {
    private static final Config awsConfig = ConfigFactory.load("aws.conf");

    AmazonDynamoDB   dynamoClient;
    DaoStudentDynamo daoStudent;

    @Before
    public void before() {
        dynamoClient = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                awsConfig.getString("dynamo.host"), awsConfig.getString("dynamo.region")))
                                    .build();
        daoStudent = new DaoStudentDynamo(dynamoClient, awsConfig);
    }

    @Test
    public void testCreateTable() {
        daoStudent.createTable();
        daoStudent.createItem();
        daoStudent.readItem();
        daoStudent.updateItem();
        daoStudent.deleteItem();
    }
}


