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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamo {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentDynamo.class);

    private AmazonDynamoDB client;
    private DynamoDB dynamoDB;

    DaoStudentDynamo() {
        logger.info("constructor...");
        try {
            client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                    new AwsClientBuilder.EndpointConfiguration(
                            "http://localhost:8000", "us-west-1"))
                                .build();
            dynamoDB = new DynamoDB(client);
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
        }
    }

    boolean tableExists(String tableName) {
        logger.info("tablexists {}", tableName);
        try {
            ListTablesResult result = client.listTables();
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

    void createTable(String tableName) {
        logger.info("createTable {}", tableName);

        if (tableExists(tableName)) {
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
