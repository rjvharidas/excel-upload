package com.tle.bootcamp.excelupload.util;

import java.time.Duration;
import java.time.Instant;

public final class Timer {

    private static Instant start;

    private Timer() {
    }

    public static void start() {
        start = Instant.now();
    }

    public static void end() {
        if (start == null) {
            System.out.println("Timer is not started!!");
        }
        Instant finish = Instant.now();
        System.out.println("Execution Time :" + Duration.between(start, finish).toMillis()+" ms");
    }
}
