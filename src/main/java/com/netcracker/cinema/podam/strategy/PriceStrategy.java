package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.*;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Random;

/**
 * Created by gaya on 16.11.2016.
 */
public class PriceStrategy extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random(System.currentTimeMillis());

    public PriceStrategy() {
        super();
    }


    public String getStringValue(AttributeMetadata attributeMetadata) {
        SeanceStrategy seanceStrategy = new SeanceStrategy();
        MovieStrategy movieStrategy = new MovieStrategy();
        HallStrategy hallStrategy = new HallStrategy();
        ZoneStrategy zoneStrategy = new ZoneStrategy();


        if (Hall.class.equals(attributeMetadata.getPojoClass())) {
            return hallStrategy.getStringValue(attributeMetadata);
        } else if (Seance.class.equals(attributeMetadata.getPojoClass())) {
            return seanceStrategy.getStringValue(attributeMetadata);
        } else if (Movie.class.equals(attributeMetadata.getPojoClass())) {
            return movieStrategy.getStringValue(attributeMetadata);
        } else if (Zone.class.equals(attributeMetadata.getPojoClass())) {
            return zoneStrategy.getStringValue(attributeMetadata);
        } else if (Price.class.equals(attributeMetadata.getPojoClass())) {
            return "";
        }

        return super.getStringValue(attributeMetadata);
    }


    @Override
    public Integer getInteger(AttributeMetadata attributeMetadata) {

        if (Price.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(100);
            }
            if ("price".equals(attributeMetadata.getAttributeName())) {
                return 40 + random.nextInt(500);
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
        if (Hall.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(2);
            }
        }
        if (Zone.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(6);
            }
        }

        if (Seance.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(2000);
            }
        }
        return super.getInteger(attributeMetadata);
    }


}

