package com.davelabine.resterapp.platform.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamo {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentDynamo.class);

    private AmazonDynamoDB dynamoClient;
    private DynamoDB dynamoDB;

    private Config awsConfig;
    private String tableName;

    @Inject
    public DaoStudentDynamo(final AmazonDynamoDB dynamoClient, final @Named("aws.conf") Config awsConfig) {
        logger.info("constructor...");
        this.dynamoClient = dynamoClient;
        this.awsConfig = awsConfig;
        this.tableName = awsConfig.getString("dynamo.student-table");  // A convenience
        dynamoDB = new DynamoDB(dynamoClient);
    }

    public boolean tableExists() {
        logger.info("tablexists");
        try {
            ListTablesResult result = dynamoClient.listTables();
            List<String> tableList = result.getTableNames();
            Iterator<String> it = tableList.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(tableName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
        return false;
    }

    public void createTable() {
        logger.info("createTable {}", tableName);

        if (tableExists()) {
            logger.info("{} already exists!", tableName);
            return;
        }

        logger.info("Attempting to create table; please wait...");
        try {
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(
                            new KeySchemaElement("year", KeyType.HASH), //Partition key
                            new KeySchemaElement("title", KeyType.RANGE)), //Sort key
                    Arrays.asList(
                            new AttributeDefinition("year", ScalarAttributeType.N),
                            new AttributeDefinition("title", ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            logger.info("Success.  Table status: " + table.getDescription().getTableStatus());
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }


}
