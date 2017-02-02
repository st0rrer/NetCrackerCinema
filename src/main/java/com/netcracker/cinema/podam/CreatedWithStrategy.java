package com.netcracker.cinema.podam;

import com.netcracker.cinema.model.*;
import com.netcracker.cinema.podam.strategy.*;
import uk.co.jemos.podam.api.DataProviderStrategy;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

public class CreatedWithStrategy {

    public Hall getHallPodam() {
        DataProviderStrategy strategy = new HallStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Hall hall = factory.manufacturePojo(Hall.class);
        return hall;
    }

    public Movie getMoviePodam() {
        DataProviderStrategy strategy = new MovieStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Movie movie = factory.manufacturePojo(Movie.class);
        return movie;
    }

    public Place getPlacePodam() {
        DataProviderStrategy strategy = new PlaceStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Place place = factory.manufacturePojo(Place.class);
        return place;
    }

    public Price getPricePodam() {
        DataProviderStrategy strategy = new PriceStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Price price = factory.manufacturePojo(Price.class);
        return price;
    }

    public Seance getSeancePodam() {
        DataProviderStrategy strategy = new SeanceStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Seance seance = factory.manufacturePojo(Seance.class);
        return seance;
    }

    public Ticket getTicketPodam() {
        DataProviderStrategy strategy = new TicketStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Ticket ticket = factory.manufacturePojo(Ticket.class);
        return ticket;
    }

    public Zone getZonePodam() {
        DataProviderStrategy strategy = new ZoneStrategy();
        PodamFactory factory = new PodamFactoryImpl(strategy);
        Zone zone = factory.manufacturePojo(Zone.class);
        return zone;
    }
}
