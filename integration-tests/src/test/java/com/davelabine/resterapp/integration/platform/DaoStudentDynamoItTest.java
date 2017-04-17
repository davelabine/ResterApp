package com.davelabine.resterapp.integration.platform;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * Created by dave on 3/3/17.
 */
public class DaoStudentDynamoItTest {
    private static final Config awsConfig = ConfigFactory.load("aws.conf");

    /* Comment this out for now...
       I've decided not to use this class

    AmazonDynamoDB   dynamoClient;
    DaoStudent daoStudent;

    @Before
    public void before() {
        dynamoClient = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                awsConfig.getString("dynamo.host"), awsConfig.getString("dynamo.region")))
                                    .build();
        daoStudent = new DaoStudentDynamo(dynamoClient, awsConfig);
    }

    @Test
    public void testCRUDStudentTable() {
        daoStudent.createTable();

        // Test create and read
        Student student = new RandomStudent();
        daoStudent.createStudent(student);
        Student getStudent = daoStudent.getStudent(student.getKey());

        assertEquals(student.getName(), getStudent.getName());

        // Now verify we can get by name
        List<Student> nameStudent = daoStudent.getStudentByName(student.getName());
        assertTrue(nameStudent.stream().anyMatch(item -> student.getName().equals(item.getName())));

        // Now verify we can update a student
        student.setName("Oderus Urungus");
        student.setId("123456");
        daoStudent.updateStudent(student);
        Student updateStudent = daoStudent.getStudent(student.getKey());
        assertEquals(student.getName(), updateStudent.getName());
        assertEquals(student.getId(), updateStudent.getId());

        // Finally, delete the item
        daoStudent.deleteStudent(student.getKey());
        Student deleteStudent = daoStudent.getStudent(student.getKey());
        assertNull(deleteStudent);
    }
    */
}


