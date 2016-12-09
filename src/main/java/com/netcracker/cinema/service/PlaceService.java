package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Place;

import java.util.List;

/**
 * Created by Илья on 08.12.2016.
 */
public interface PlaceService {
    List<Place> findAll();

    Place getById(long id);

    Place getByHall(long id);

    Place getByZone(long id);

    void save(Place place);

    void delete(Place place);
}
