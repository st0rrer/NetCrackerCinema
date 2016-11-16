package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;


import java.util.Random;

/**
 * Created by gaya on 16.11.2016.
 */
public class SeanceStrategy extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random(System.currentTimeMillis());

    public SeanceStrategy() {
        super();
    }


    public String getStringValue(AttributeMetadata attributeMetadata) {
        MovieStrategy movieStrategy = new MovieStrategy();
        HallStrategy hallStrategy = new HallStrategy();
        if ("description".equals(attributeMetadata.getAttributeName())) {
            if (Movie.class.equals(attributeMetadata.getPojoClass())) {
                return movieStrategy.getStringValue(attributeMetadata);
            }
        }
        if ("name".equals(attributeMetadata.getAttributeName())) {
            if (Hall.class.equals(attributeMetadata.getPojoClass())) {
                return hallStrategy.getStringValue(attributeMetadata);
            } else if (Movie.class.equals(attributeMetadata.getPojoClass())) {
                return movieStrategy.getStringValue(attributeMetadata);

            } else if (Seance.class.equals(attributeMetadata.getPojoClass())) {
                return "";
            }
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
        if (Hall.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(2);
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
}
