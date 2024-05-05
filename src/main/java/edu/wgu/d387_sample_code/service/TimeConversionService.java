package edu.wgu.d387_sample_code.service;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeConversionService {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm z");

    public String[] convertToTimeZones(ZonedDateTime inputTime) {
        /*TODO: Convert to Eastern Time (ET) */
        ZonedDateTime easternTime = inputTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        String formattedEastern = easternTime.format(formatter);

        /*TODO: Convert to Mountain Time (MT) */
        ZonedDateTime mountainTime = inputTime.withZoneSameInstant(ZoneId.of("America/Denver"));
        String formattedMountain = mountainTime.format(formatter);

        /*TODO: Convert to Coordinated Universal Time (UTC) */
        ZonedDateTime utcTime = inputTime.withZoneSameInstant(ZoneId.of("UTC"));
        String formattedUTC = utcTime.format(formatter);

        /*TODO: Return array with formatted times */
        return new String[]{formattedEastern, formattedMountain, formattedUTC};
    }
}
