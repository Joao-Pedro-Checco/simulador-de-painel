package br.com.fulltime.fullarm.core.logger;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    public static void log(String logMessage) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SS");
        String formattedDateTime = currentTime.format(dateTimeFormatter);
        System.out.printf("%s: %s\n", formattedDateTime, logMessage);
    }
}
