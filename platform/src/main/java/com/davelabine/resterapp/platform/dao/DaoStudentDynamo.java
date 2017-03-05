package com.davelabine.resterapp.platform.dao;

import java.util.*;

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
import com.davelabine.resterapp.platform.model.Student;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davelabine.resterapp.platform.model.Student;

import com.typesafe.config.Config;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamo {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentDynamo.class);
    public static final String DB_KEY = "_key";
    public static final String DB_DATA = "data";
    public static final String DB_ID = "id";
    public static final String DB_NAME = "name";
    public static final String DB_PHOTO = "photo";
    public static final String DB_VERSION = "version";
    public static final String DB_VERSION_VALUE = "0.0.1"; // So we can detect\convert schema changes in prod

    private final AmazonDynamoDB dynamoClient;
    private final DynamoDB dynamoDB;
    private final String tableName;

    private final Config awsConfig;


    @Inject
    public DaoStudentDynamo(final AmazonDynamoDB dynamoClient, final @Named("aws.conf") Config awsConfig) {
        logger.info("constructor...");
        this.dynamoClient = dynamoClient;
        this.awsConfig = awsConfig;

        this.dynamoDB = new DynamoDB(dynamoClient);
        this.tableName = awsConfig.getString("dynamo.student-table");  // A convenience
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

    public boolean createTable() {
        logger.info("createTable {}", tableName);

        if (tableExists()) {
            logger.info("{} already exists!", tableName);
            return true;
        }

        logger.info("Attempting to create table; please wait...");
        try {
            Table table = dynamoDB.createTable(tableName,
                    Arrays.asList(
                            new KeySchemaElement(DB_KEY, KeyType.HASH)), //Partition key
                    Arrays.asList(
                            new AttributeDefinition(DB_KEY, ScalarAttributeType.S)),
                    new ProvisionedThroughput(10L, 10L));
            table.waitForActive();
            logger.info("Success.  Table status: {}" + table.getDescription().getTableStatus());
        } catch (Exception e) {
            logger.error("Exception {}", e.getMessage());
            return false;
        }

        return true;
    }

    private String createKey() {
        return UUID.randomUUID().toString();
    }

    public String createItem(Student student)
    {
        student.setKey(createKey());
        logger.info("createItem {}", student);

        Table table = dynamoDB.getTable(tableName);

        // TODO: There must be a way to do this with GSON or Lombok-Jackson.  Do it the verbose way for now.
        final Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put(DB_VERSION, DB_VERSION_VALUE);
        dataMap.put(DB_ID, student.getId());
        dataMap.put(DB_NAME, student.getName());
        dataMap.put(DB_PHOTO, student.getPhoto());

        try {
            logger.info("Adding a new item...");
            PutItemOutcome outcome = table.putItem(new Item()
                    .withPrimaryKey(DB_KEY, student.getKey())
                    .withMap(DB_DATA, dataMap));
            logger.info("PutItem succeeded: {}\n" + outcome.getPutItemResult());

        } catch (Exception e) {
            logger.error("Unable to add item - error: {}", e.getMessage());
            return null;
        }

        return student.getKey();
    }

    public Student readItem(String key)
    {
        logger.info("readItem ");
        Table table = dynamoDB.getTable(tableName);

        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey(DB_KEY, key);

        Item outcome;
        try {
            logger.info("Attempting to read the item...");
            outcome = table.getItem(spec);
            logger.info("GetItem succeeded: {}", outcome);
        } catch (Exception e) {
            logger.error("Unable to read item: - error: {}", e.getMessage());
            return null;
        }

        // TODO: Yuck.  We definitely need some sort of JSON conversion...
        Student ret = new Student();
        ret.setKey(key);
        Map<String, Object> dataMap = outcome.getMap(DB_DATA);
        if (dataMap != null) {
            ret.setId((String) dataMap.get(DB_ID));
            ret.setName((String) dataMap.get(DB_NAME));
            //ret.setPhoto(outcome.get);
        }
        return ret;
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
