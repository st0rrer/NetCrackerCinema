package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.podam.strategy.TicketStrategy;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Created by gaya on 16.11.2016.
 */
public class TicketCreatedWithStrategy {
    public static void main(String[] args) {
        DataProviderStrategy strategy = new TicketStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Ticket myPojo = factory.manufacturePojo(Ticket.class);
        System.out.println(ReflectionToStringBuilder.toString(myPojo,new RecursiveToStringStyle()));
    }
}
