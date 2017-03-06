package com.davelabine.resterapp.platform.dao;

import java.util.*;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Index;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;
import com.davelabine.resterapp.platform.api.DaoStudent;
import com.davelabine.resterapp.platform.model.Student;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davelabine.resterapp.platform.api.Student;

import com.typesafe.config.Config;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamo implements DaoStudent {
    private static final Logger logger = LoggerFactory.getLogger(DaoStudentDynamo.class);
    public static final String DB_KEY = "_key";
    public static final String DB_DATA = "data";
    public static final String DB_ID = "id";
    public static final String DB_NAME = "_name";
    public static final String DB_PHOTO = "photo";
    public static final String DB_VERSION = "version";
    public static final String DB_VERSION_VALUE = "0.0.1"; // So we can detect\convert schema changes in prod

    private final AmazonDynamoDB dynamoClient;
    private final DynamoDB dynamoDB;
    private final String tableName;
    private final String secIndexName;

    private final Config awsConfig;


    @Inject
    public DaoStudentDynamo(final AmazonDynamoDB dynamoClient, final @Named("aws.conf") Config awsConfig) {
        logger.info("constructor...");
        this.dynamoClient = dynamoClient;
        this.awsConfig = awsConfig;

        this.dynamoDB = new DynamoDB(dynamoClient);

        // Convenience variables...
        this.tableName = awsConfig.getString("dynamo.student-table.name");
        this.secIndexName = awsConfig.getString("dynamo.student-table.sec-index-name");
    }

    @Override
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

    @Override
    public boolean createTable() {
        logger.info("createTable {}", tableName);

        if (tableExists()) {
            logger.info("{} already exists!", tableName);
            return true;
        }

        logger.info("Setting up table attribute definitions.");

        // Primary Table Attribute definitions
        ArrayList<AttributeDefinition> attributeDefinitions = new ArrayList<AttributeDefinition>();

        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(DB_KEY)
                .withAttributeType("S"));
        attributeDefinitions.add(new AttributeDefinition()
                .withAttributeName(DB_NAME)
                .withAttributeType("S"));

        // Table key schema
        ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<KeySchemaElement>();
        tableKeySchema.add(new KeySchemaElement()
                .withAttributeName(DB_KEY)
                .withKeyType(KeyType.HASH));  //Partition key

        // Secondary name index...
        // TODO: We probably want to project less fields, fix this later.
        GlobalSecondaryIndex nameIndex = new GlobalSecondaryIndex()
                .withIndexName(secIndexName)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 10)
                        .withWriteCapacityUnits((long) 1))
                .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

        ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<KeySchemaElement>();

        indexKeySchema.add(new KeySchemaElement()
                .withAttributeName(DB_NAME)
                .withKeyType(KeyType.HASH));  //Partition key

        nameIndex.setKeySchema(indexKeySchema);

        CreateTableRequest createTableRequest = new CreateTableRequest()
                .withTableName(tableName)
                .withProvisionedThroughput(new ProvisionedThroughput()
                        .withReadCapacityUnits((long) 5)
                        .withWriteCapacityUnits((long) 1))
                .withAttributeDefinitions(attributeDefinitions)
                .withKeySchema(tableKeySchema)
                .withGlobalSecondaryIndexes(nameIndex);

        logger.info("Attempting to create table; please wait...");
        try {
            Table table = dynamoDB.createTable(createTableRequest);
            table.waitForActive();
            logger.info("Success.  Table status: {}" + table.getDescription().getTableStatus());
        } catch (Exception e) {
            logger.error("Exception creating table.  Error: {}", e.getMessage());
            return false;
        }

        return true;
    }

    private String createKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String createStudent(Student student)
    {
        student.setKey(createKey());
        logger.info("createStudent {}", student);

        Table table = dynamoDB.getTable(tableName);

        // TODO: There must be a way to do this with GSON or Lombok-Jackson.  Do it the verbose way for now.
        try {
            logger.info("Adding a new item...");
            PutItemOutcome outcome = table.putItem(new Item()
                    .withPrimaryKey(DB_KEY, student.getKey())
                    .withString(DB_NAME, student.getName())
                    .withString(DB_ID, student.getId())
                    .withString(DB_VERSION, DB_VERSION_VALUE));
            logger.info("PutItem succeeded: {}\n" + outcome.getPutItemResult());

        } catch (Exception e) {
            logger.error("Unable to add Student - error: {}", e.getMessage());
            return null;
        }

        return student.getKey();
    }

    @Override
    public Student getStudent(String key)
    {
        logger.info("getStudent: {}", key);
        if (key == null) return null;

        Table table = dynamoDB.getTable(tableName);

        GetItemSpec spec = new GetItemSpec()
                .withPrimaryKey(DB_KEY, key);

        Item outcome;
        try {
            logger.info("Attempting to read the Student...");
            outcome = table.getItem(spec);
            logger.info("GetItem succeeded: {}", outcome);
        } catch (Exception e) {
            logger.error("Unable to read item: - error: {}", e.getMessage());
            return null;
        }

        return itemToStudent(outcome);
    }

    private Student itemToStudent(Item item) {
        if (item == null) return null;

        // TODO: Yuck.  We definitely need some sort of JSON conversion...
        Student ret = new Student();
        ret.setKey(item.getString(DB_KEY));
        ret.setName(item.getString(DB_NAME));
        ret.setId(item.getString(DB_ID));
        //ret.setPhoto(outcome.get);
        return ret;
    }

    @Override
    public List<Student> getStudentByName(String name)
    {
        logger.info("getStudentByName: {}", name);
        if (name == null) return null;
        Table table = dynamoDB.getTable(tableName);
        Index index = table.getIndex(secIndexName);

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("#n = :v_name")
                .withNameMap(new NameMap()
                        .with("#n", DB_NAME))
                .withValueMap(new ValueMap()
                        .withString(":v_name", name));

        logger.info("Attempting to query by name...");
        List<Student> listStudent = new ArrayList<Student>();
        try {
            ItemCollection<QueryOutcome> items = index.query(spec);
            Iterator<Item> iter = items.iterator();
            logger.info("Query succeeded: {}", items);
            while (iter.hasNext()) {
                listStudent.add(itemToStudent(iter.next()));
            }
        } catch (Exception e) {
            logger.error("Unable to query - error: {}", e.getMessage());
            return null;
        }
        return listStudent;
    }

    @Override
    public boolean updateStudent(Student student) {
        logger.info("updateStudent: {}", student.getKey());
        Table table = dynamoDB.getTable(tableName);

        UpdateItemSpec updateItemSpec = new UpdateItemSpec()
                .withPrimaryKey(DB_KEY, student.getKey())
                .withUpdateExpression("set #n=:n, " + DB_ID + "=:i")
                .withNameMap(new NameMap()
                        .with("#n", DB_NAME))
                .withValueMap(new ValueMap()
                        .withString(":n",student.getName())
                        .withString(":i",student.getId()))
                .withReturnValues(ReturnValue.UPDATED_NEW);

        try {
            logger.info("Updating the item...");
            UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
            logger.info("UpdateItem succeeded: {}" + outcome.getItem().toJSONPretty());
        } catch (Exception e) {
            logger.error("Unable to update item - error: {}", e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deleteStudent(String key) {
        logger.info("deleteStudent {}", key);
        Table table = dynamoDB.getTable(tableName);

        DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
                .withPrimaryKey(DB_KEY, key);

        try {
            logger.info("Attempting to delete...");
            table.deleteItem(deleteItemSpec);
            logger.info("DeleteStudent succeeded");
        } catch (Exception e) {
            logger.error("Unable to delete student: error: {}", e.getMessage());
            return false;
        }

        return true;
    }

}
