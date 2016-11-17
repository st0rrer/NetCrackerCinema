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
        ZoneStrategy zoneStrategy=new ZoneStrategy();

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


    @Override
    public Integer getInteger(AttributeMetadata attributeMetadata) {

        if (Seance.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(2000);
            }
        }
        if (Place.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(120);
            } else if ("rowNumber".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(120);
            } else if ("number".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(120);
            }
        }
        if (Hall.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(2);
            }
        }
        if (Ticket.class.equals(attributeMetadata.getPojoClass())) {
            if ("price".equals(attributeMetadata.getAttributeName())) {
                return 40 + random.nextInt(500);
            }
        }
        if (Zone.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(6);
            }
        }
        if (Movie.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(2000);
            } else if ("duration".equals(attributeMetadata.getAttributeName())) {
                return 10 + random.nextInt(340);
            } else if ("periodicity".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(10);
            } else if ("basePrice".equals(attributeMetadata.getAttributeName())) {
                return 20 + random.nextInt(500);
            }
        }

        return super.getInteger(attributeMetadata);
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
