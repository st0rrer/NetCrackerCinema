package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.validation.SeancesValidation;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by titarenko on 28.01.2017.
 */
public class SeanceTimeSettings {

    public static final long ONE_MINUTE = 60_000;
    public static final long ONE_HOUR = ONE_MINUTE * 60;
    public static final long ONE_DAY = ONE_HOUR * 24;

    public static long START_TIME_OF_WORKING_DAY;           //  looks like: 10_00
    public static long LAST_START_TIME_OF_SEANCE;           //  looks like: 22_00
    public static long TIME_FOR_CLEANING;                   //  in minutes
    public static long MIN_TIME_TO_START_SEANCE;            //  in minutes

    private static final Logger LOGGER = Logger.getLogger(SeancesValidation.class);

    static {
        Properties property = new Properties();
        try {
            InputStream stream = SeancesValidation.class.getResourceAsStream("/cinema.properties");
            property.load(stream);

            START_TIME_OF_WORKING_DAY = Long.valueOf(property.getProperty("START_TIME_OF_WORKING_DAY").replace(":", ""));
            LAST_START_TIME_OF_SEANCE = Long.valueOf(property.getProperty("LAST_START_TIME_OF_SEANCE").replace(":", ""));
            TIME_FOR_CLEANING = Long.valueOf(property.getProperty("TIME_FOR_CLEANING"));
            MIN_TIME_TO_START_SEANCE = Long.valueOf(property.getProperty("MIN_TIME_TO_START_SEANCE"));
        } catch (IOException e) {
            LOGGER.warn("Need file 'resources/cinema.properties'");
        }
    }
}