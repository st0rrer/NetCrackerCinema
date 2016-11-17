package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.podam.strategy.PlaceStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 16.11.2016.
 */
public class PlaceCreatedWithStrategy {
    public static void main(String[] args) {
        DataProviderStrategy strategy = new PlaceStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Place myPojo = factory.manufacturePojo(Place.class);
        System.out.println(ReflectionToStringBuilder.toString(myPojo,new RecursiveToStringStyle()));
    }
}
