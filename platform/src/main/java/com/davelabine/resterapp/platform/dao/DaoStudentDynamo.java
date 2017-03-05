package com.davelabine.resterapp.platform.dao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
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

    public void createItem()
    {
        logger.info("createItem ");
        Table table = dynamoDB.getTable(tableName);

        int year = 2015;
        String title = "The Big New Movie";
        final Map<String, Object> infoMap = new HashMap<String, Object>();
        infoMap.put("plot",  "Nothing happens at all.");
        infoMap.put("rating",  0);

        try {
            logger.info("Adding a new item...");
            PutItemOutcome outcome = table.putItem(new Item()
                    .withPrimaryKey("year", year, "title", title)
                    .withMap("info", infoMap));

            logger.info("PutItem succeeded: {}\n" + outcome.getPutItemResult());

        } catch (Exception e) {
            logger.error("Unable to add item: {}, {} - error: ", year, title, e.getMessage());
        }
    }

    public void readItem()
    {
        logger.info("readItem ");
        Table table = dynamoDB.getTable(tableName);

        int year = 2015;
        String title = "The Big New Movie";

        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey("year", year, "title", title);

        try {
            logger.info("Attempting to read the item...");
            Item outcome = table.getItem(spec);
            logger.info("GetItem succeeded: {}", outcome);

        } catch (Exception e) {
            logger.error("Unable to read item: - error: {}", e.getMessage());
        }
    }

    public void updateItem() {
        logger.info("updateItem ");
        Table table = dynamoDB.getTable(tableName);

        int year = 2015;
        String title = "The Big New Movie";

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey("year", year, "title", title)
                .withUpdateExpression("set info.rating = :r, info.plot=:p, info.actors=:a")
                .withValueMap(new ValueMap()
                        .withNumber(":r", 5.5)
                        .withString(":p", "Everything happens all at once.")
                        .withList(":a", Arrays.asList("Larry","Moe","Curly")))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            logger.info("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            logger.info("UpdateItem succeeded: {}" + outcome.getItem().toJSONPretty());
        } catch (Exception e) {
            logger.error("Unable to update item - error: {}", e.getMessage());
        }
    }

    public void deleteItem() {
        logger.info("deleteItem ");
        Table table = dynamoDB.getTable(tableName);

        int year = 2015;
        String title = "The Big New Movie";

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(new PrimaryKey("year", year, "title", title))
                .withConditionExpression("info.rating <= :val")
                .withValueMap(new ValueMap()
                        .withNumber(":val", 5.0));

        // Conditional delete (we expect this to fail)

        try {
            logger.info("Attempting a conditional delete...");
            table.deleteItem(deleteItemSpec);
            logger.info("DeleteItem succeeded");
        } catch (Exception e) {
            logger.error("Unable to delete item: error: {}", e.getMessage());
        }
    }

}
