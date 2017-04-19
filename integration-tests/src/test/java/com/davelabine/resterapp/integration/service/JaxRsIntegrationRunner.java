package com.davelabine.resterapp.integration.service;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.apache.http.HttpStatus.*;

import com.davelabine.resterapp.platform.api.model.Student;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

/**
 * JAX-RS Integration Test.
 *
 * Testing Plan:
 * 1. Verify that methods annotated with @GET are mapped to the controller.
 * 2. Verify that params are parsed using the @QueryParam are actually the values on the URL using the ?queryparamkey=value
 * 3. Verify the exception mapping stuff is well understood
 *     a. pass a flag parameter that instructs the controller to throw a mapped exception - then verify we see the status + body we expect.
 */
public class JaxRsIntegrationRunner {
    private static final String URI_BASE = "http://localhost:8080/api";
    private static final String URI_ROSTER = URI_BASE + "/roster";
    private static final String URI_STUDENTS = URI_BASE + "/students";
    private static final String URI_STUDENTS_CREATE = URI_STUDENTS + "/create";

    private static final Logger logger = LoggerFactory.getLogger(JaxRsIntegrationRunner.class);
    private static final Config config = ConfigFactory.load("application.conf");
    private static Runner runner = null;
    private static Gson gson = new Gson();
    private static CloseableHttpClient client = null;

    @BeforeClass
    public static void startServer() throws Exception {
        String war_file = config.getString("JaxRSItRunner.war_file");
        Preconditions.checkNotNull(war_file, "This test requires the web path for the service project!  Run with JaxRSItRunner.war_file set in resources/application.conf");

        runner = new Runner(war_file);
        runner.start();
    }

    @Test
    public void verifyTextPlainRosters() throws IOException {
        logger.info("verifyTextPlainRosters()");
        refreshHTTPClient();

        HttpGet rosterGet = new HttpGet(URI_ROSTER);
        CloseableHttpResponse rosterResp = client.execute(rosterGet);
        assertThat("Non 200 status response received", rosterResp.getStatusLine().getStatusCode(), is(SC_OK));
    }

    @Test
    public void verifyStudentCRUD() throws IOException {
        logger.info("verifyStudentCRUD()");
        refreshHTTPClient();

        // Create a random student and post it to the create URI
        Student studentCreate = Student.randomStudent();
        CloseableHttpResponse postResp = postStudent(URI_STUDENTS_CREATE, studentCreate);
        assertThat("Unexpected create response", postResp.getStatusLine().getStatusCode(), is(SC_CREATED));

        String studentKey = postResp.getFirstHeader("Student-Key").getValue();
        assertThat("Empty Student-Key header", studentKey, notNullValue());

        // Verify you can access the student in a subsequent get
        Student studentGet = getStudent(studentKey);
        assertThat("IDs for created student are not equal", studentGet.getId(), is(studentCreate.getId()));
        assertThat("Names for created student are not equal", studentGet.getName(), is(studentCreate.getName()));

        // Verify you can update the student
        studentCreate.setName("Oderus Urungus");
        postResp = postStudent(URI_STUDENTS + "/" + studentKey, studentCreate);
        assertThat("Unexpected update response", postResp.getStatusLine().getStatusCode(), is(SC_OK));
        studentGet = getStudent(studentKey);
        assertThat("Names for updated student are not equal", studentGet.getName(), is(studentCreate.getName()));

        // Delete the student

    }

    public void closeHTTPClient() throws IOException {
        if (client != null) { client.close(); } ;
    }

    public void refreshHTTPClient() throws IOException {
        closeHTTPClient();
        client = HttpClients.createDefault();
    }

    public CloseableHttpResponse postStudent(String uri, Student postStudent) throws IOException {
        HttpPost post = new HttpPost(uri);
        StringEntity stringEntity = new StringEntity(gson.toJson(postStudent));
        post.setEntity(stringEntity);
        post.setHeader("Content-type", "application/json");
        return client.execute(post);
    }

    public Student getStudent(String key) throws IOException {
        HttpGet get = new HttpGet(URI_STUDENTS + "/" + key);
        CloseableHttpResponse getResp = client.execute(get);

        assertThat("Non 200 status response received", getResp.getStatusLine().getStatusCode(), is(SC_OK));
        return gson.fromJson(EntityUtils.toString(getResp.getEntity()), Student.class);
    }

    @Test
    public void verifyStudentCreateBadParams() throws IOException {
        logger.info("verifyStudentCreateBadParams()");
        refreshHTTPClient();

        // Create a null student
        CloseableHttpResponse postResp = postNullStudent(URI_STUDENTS_CREATE);

        assertThat("Unexpected create response", postResp.getStatusLine().getStatusCode(), is(SC_BAD_REQUEST));
    }

    @Test
    public void verifyStudentUpdateBadParams() throws IOException {
        logger.info("verifyStudentCreateBadParams()");
        refreshHTTPClient();

        // Update Student with no key
        Student studentUpdate = Student.randomStudent();
        CloseableHttpResponse postResp = postStudent(URI_STUDENTS, studentUpdate);
        assertThat("Can't post a student with no key", postResp.getStatusLine().getStatusCode(), is(SC_METHOD_NOT_ALLOWED));

        // Update a valid key, with a null student
        postResp = postStudent(URI_STUDENTS_CREATE, studentUpdate);
        String studentKey = postResp.getFirstHeader("Student-Key").getValue();
        postResp = postNullStudent(URI_STUDENTS + "/" + studentKey);
        assertThat("Can't post null student", postResp.getStatusLine().getStatusCode(), is(SC_BAD_REQUEST));
    }

    public CloseableHttpResponse postNullStudent(String uri) throws IOException {
        HttpPost post = new HttpPost(uri);
        StringEntity stringEntity = new StringEntity("");
        post.setEntity(stringEntity);
        post.setHeader("Content-type", "application/json");
        return client.execute(post);
    }

    @AfterClass
    public static void stopServer() throws Exception {
        runner.stop();
    }

}
