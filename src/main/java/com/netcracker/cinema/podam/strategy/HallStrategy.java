package com.netcracker.cinema.podam.strategy;

import com.netcracker.cinema.model.Hall;
import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by gaya on 16.11.2016.
 */
public class HallStrategy  extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random(System.currentTimeMillis());

    public HallStrategy() {
        super();
    }

    @Override
    public String getStringValue(AttributeMetadata attributeMetadata) {

        if ("name".equals(attributeMetadata.getAttributeName())) {
            if (Hall.class.equals(attributeMetadata.getPojoClass())) {
                return HallStrategy.NameHall.randomHall();
            }
        }

        return super.getStringValue(attributeMetadata);
    }

    private enum NameHall {

        HALL_ONE,HALL_TWO;

        private static final List<HallStrategy.NameHall> values = Collections.unmodifiableList(Arrays.asList(values()));
        private static final int size = values.size();
        private static final Random random = new Random();

        public static String randomHall() {
            return values.get(random.nextInt(size)).toString();
        }
    }
}
