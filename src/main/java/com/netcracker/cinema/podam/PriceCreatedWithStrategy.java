package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.podam.strategy.PriceStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 17.11.2016.
 */
public class PriceCreatedWithStrategy {
    public static void main(String[] args) {
        DataProviderStrategy strategy = new PriceStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Price myPojo = factory.manufacturePojo(Price.class);
        System.out.println(ReflectionToStringBuilder.toString(myPojo,new RecursiveToStringStyle()));
    }
}
