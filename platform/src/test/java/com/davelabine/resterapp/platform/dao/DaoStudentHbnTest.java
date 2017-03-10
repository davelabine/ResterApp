package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.davelabine.resterapp.platform.api.model.Student;
import org.hibernate.service.ServiceRegistry;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.security.auth.login.Configuration;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

/**
 * Created by davidl on 3/6/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class DaoStudentHbnTest {
    public static final String FAKE_STUDENT_NAME = "FAKE_BUCKET";
    public static final String FAKE_STUDENT_ID = "FAKE_ID";
    public static final String FAKE_KEY = "FAKE_KEY";

    @InjectMocks
    private DaoStudentHbn daoStudent;

    @Mock
    Configuration hbnConfig;

    @Mock
    ServiceRegistry hbnRegistry;

    @Before
    public void before() {

    }

    /* Hm, this will be trickier than I thought...

    @Test(expected = DaoException.class)
    public void testcreateStudent() {
        reset(hbnRegistry);

        // Just a reference to delete when we implement for reals
        BlobData data = new BlobData(FAKE_FILENAME);
        when(mockS3.putObject(any(PutObjectRequest.class))).thenThrow(new AmazonServiceException("Fake Exception!"));
        underTest.putObject(data); // Should throw an exception

        Student student = new Student(FAKE_STUDENT_ID, FAKE_STUDENT_NAME);
        String key = daoStudent.createStudent(student);

    }
    */

}
