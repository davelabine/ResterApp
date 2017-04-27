package com.davelabine.resterapp.platform.dao;

import com.davelabine.resterapp.platform.api.exceptions.DaoException;
import com.davelabine.resterapp.platform.api.model.Student;
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
import static org.junit.Assert.*;

/**
 * Created by davidl on 3/6/17.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("unchecked")
public class DaoStudentHbnTest {
    public static final String FAKE_EXCEPTION = "Fake Exception!";
    public static final String FAKE_KEY = "FAKE_KEY";

    @InjectMocks
    private DaoStudentHbn underTest;

    @Mock
    HbnTxManager hbnTxManager;

    /*
     * In general, we want to verify this class is passing exceptions back properly.
     * Unit testing happy cases is basically unit testing Hibernate, so don't bother
     * Instead, cover happy cases in our integration tests.
     */

    @Test(expected = DaoException.class)
    public void testCreateStudentException() {
        reset(hbnTxManager);
        when(hbnTxManager.processTx(any(HbnTxManager.HbnTxFunction.class), any(Student.class))).
                thenThrow(new DaoException(FAKE_EXCEPTION));
        underTest.createStudent(Student.randomStudent());
    }

    @Test(expected = DaoException.class)
    public void testGetStudentException() {
        reset(hbnTxManager);
        when(hbnTxManager.processTx(any(HbnTxManager.HbnTxFunction.class), any(String.class))).
                thenThrow(new DaoException(FAKE_EXCEPTION));
        underTest.getStudent(FAKE_KEY);
    }


    @Test(expected = DaoException.class)
    public void testUpdateStudentException() {
        reset(hbnTxManager);
        when(hbnTxManager.processTx(any(HbnTxManager.HbnTxFunction.class), any(Student.class))).
                thenThrow(new DaoException(FAKE_EXCEPTION));
        underTest.updateStudent(Student.randomStudent());
    }

    @Test(expected = DaoException.class)
    public void testDeleteStudentException() {
        reset(hbnTxManager);
        when(hbnTxManager.processTx(any(HbnTxManager.HbnTxFunction.class), any(Student.class))).
                thenThrow(new DaoException(FAKE_EXCEPTION));
        underTest.deleteStudent(Student.randomStudent());
    }

}
