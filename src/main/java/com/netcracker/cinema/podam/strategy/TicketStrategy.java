package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.*;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by gaya on 16.11.2016.
 */
public class TicketStrategy extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random(System.currentTimeMillis());

    public TicketStrategy() {
        super();
    }


    public String getStringValue(AttributeMetadata attributeMetadata) {
        PlaceStrategy placeStrategy = new PlaceStrategy();
        SeanceStrategy seanceStrategy = new SeanceStrategy();
        MovieStrategy movieStrategy = new MovieStrategy();
        HallStrategy hallStrategy = new HallStrategy();
        ZoneStrategy zoneStrategy = new ZoneStrategy();

        if (Place.class.equals(attributeMetadata.getPojoClass())) {
            return placeStrategy.getStringValue(attributeMetadata);
        } else if (Seance.class.equals(attributeMetadata.getPojoClass())) {
            return seanceStrategy.getStringValue(attributeMetadata);
        } else if (Movie.class.equals(attributeMetadata.getPojoClass())) {
            return movieStrategy.getStringValue(attributeMetadata);
        } else if (Hall.class.equals(attributeMetadata.getPojoClass())) {
            return hallStrategy.getStringValue(attributeMetadata);
        } else if (Ticket.class.equals(attributeMetadata.getPojoClass())) {
            return TicketStrategy.Email.randomEmail();
        } else if (Zone.class.equals(attributeMetadata.getPojoClass())) {
            return zoneStrategy.getStringValue(attributeMetadata);
        }

        return super.getStringValue(attributeMetadata);
    }


    private enum Email {

        HARUTYUNYAN_MAIL_RU;

        private static final List<TicketStrategy.Email> values = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int size = values.size();
        private static final Random random = new Random();

        public static String randomEmail() {
            return values.get(random.nextInt(size)).toString();
        }
    }

}
