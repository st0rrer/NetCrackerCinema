package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.podam.strategy.SeanceStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 16.11.2016.
 */
public class SeancelCreatedWithStrategy {

    public static void main(String[] args) {
        DataProviderStrategy strategy = new SeanceStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Seance myPojo = factory.manufacturePojo(Seance.class);
        System.out.println(ReflectionToStringBuilder.toString(myPojo,new RecursiveToStringStyle()));
    }
}
