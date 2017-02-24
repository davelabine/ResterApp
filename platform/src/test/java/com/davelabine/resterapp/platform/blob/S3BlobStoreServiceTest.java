package com.davelabine.resterapp.platform.blob;

import com.davelabine.resterapp.platform.api.BlobStoreService;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.reset;


/**
 * Created by dave on 2/23/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class S3BlobStoreServiceTest {

    @InjectMocks
    private S3BlobStoreService underTest;

    //@Mock

    @Before
    public void before() {
    }

    // Test that a key is returned when creating a student
    @Test
    public void testPut() {
    }

}
