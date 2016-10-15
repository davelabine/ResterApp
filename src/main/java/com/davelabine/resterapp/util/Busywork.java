package com.davelabine.resterapp.util;

/**
 * Created by dave on 10/14/16.
 */
public class Busywork {

    public static void getBusy(int milliseconds) {
        long sleepTime = milliseconds*1000000L; // convert to nanoseconds
        long startTime = System.nanoTime();
        while ((System.nanoTime() - startTime) < sleepTime) {}
    }
}
