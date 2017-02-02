package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Place;

import java.util.List;

public interface PlaceService {
    List<Place> findAll();

    Place getById(long id);

    List<Place> getByHall(long id);

    List<Place> getByZone(long id);

    void save(Place place);

    void delete(Place place);
}
