package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.podam.strategy.MovieStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 15.11.2016.
 */
public class MovieCreatedWithStrategy {
    public static void main(String[] args) {
        DataProviderStrategy strategy = new MovieStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Movie myPojo = factory.manufacturePojo(Movie.class);
        System.out.println(ReflectionToStringBuilder.toString(myPojo,new RecursiveToStringStyle()));
    }
}
