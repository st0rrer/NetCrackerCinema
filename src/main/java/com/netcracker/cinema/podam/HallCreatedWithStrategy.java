package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.podam.strategy.HallStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 16.11.2016.
 */
public class HallCreatedWithStrategy {
    public static void main(String[] args) {
        /** Создаём нашу стратегию генерации */
        DataProviderStrategy strategy = new HallStrategy();
        /** Создаём фабрику на основании этой стратегии */
        PodamFactory factory = new PodamFactoryImpl(strategy);
        /** Генерим Фильм */
        Hall myPojo = factory.manufacturePojo(Hall.class);
        /** Печатаем Фильм */
        System.out.println(ReflectionToStringBuilder.toString(myPojo,new RecursiveToStringStyle()));
    }
}
