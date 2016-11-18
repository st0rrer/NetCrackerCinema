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
}

