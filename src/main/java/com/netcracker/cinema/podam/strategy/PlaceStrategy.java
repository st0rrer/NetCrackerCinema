package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.*;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Random;

/**
 * Created by gaya on 16.11.2016.
 */
public class PlaceStrategy extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random(System.currentTimeMillis());

    public PlaceStrategy() {
        super();
    }


    public String getStringValue(AttributeMetadata attributeMetadata) {
        HallStrategy hallStrategy = new HallStrategy();
        ZoneStrategy zoneStrategy = new ZoneStrategy();


        if ("name".equals(attributeMetadata.getAttributeName())) {
            if (Hall.class.equals(attributeMetadata.getPojoClass())) {
                return hallStrategy.getStringValue(attributeMetadata);
            } else if (Zone.class.equals(attributeMetadata.getPojoClass())) {
                return zoneStrategy.getStringValue(attributeMetadata);

            } else if (Place.class.equals(attributeMetadata.getPojoClass())) {
                return "";
            }
        }
        return super.getStringValue(attributeMetadata);
    }


    @Override
    public Integer getInteger(AttributeMetadata attributeMetadata) {
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
        if (Zone.class.equals(attributeMetadata.getPojoClass())) {
            if ("id".equals(attributeMetadata.getAttributeName())) {
                return 1 + random.nextInt(6);
            }
        }

        return super.getInteger(attributeMetadata);
    }


}

