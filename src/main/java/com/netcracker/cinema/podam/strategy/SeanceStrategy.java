package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Random;

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

}
