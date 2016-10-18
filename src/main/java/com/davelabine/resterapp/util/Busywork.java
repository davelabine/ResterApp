package com.davelabine.resterapp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dave on 10/14/16.
 */
public class Busywork {
    private static final Logger logger = LoggerFactory.getLogger(Busywork.class);

    public static void getBusy(int milliseconds) {
        logger.info("Getting busy...");
        long sleepTime = milliseconds*1000000L; // convert to nanoseconds
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {}
        logger.info("Done getting busy!");
    }
}
