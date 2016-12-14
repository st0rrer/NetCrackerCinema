package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Place;

import java.util.List;

/**
 * Created by Илья on 08.12.2016.
 */
public interface PlaceDao {
    List<Place> findAll();

    Place getById(long id);

    List<Place> getByHall(long id);

    List<Place> getByZone(long id);

    void save(Place place);

    void delete(Place place);
}
