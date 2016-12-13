package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Zone;

import java.util.List;

/**
 * Created by dimka on 24.11.2016.
 */
public interface ZoneService {

    List<Zone> findAll();

    Zone getById(long id);

    void save(Zone zone);

    void delete(Zone zone);
}
