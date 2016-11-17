package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Zone;
import com.netcracker.cinema.podam.strategy.ZoneStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 16.11.2016.
 */
public class ZoneCreatedWithStrategy {

    public static void main(String[] args) {
        DataProviderStrategy strategy = new ZoneStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Zone myPojo = factory.manufacturePojo(Zone.class);
        System.out.println(ReflectionToStringBuilder.toString(myPojo, new RecursiveToStringStyle()));
    }
}
