package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Zone;

import java.util.List;

public interface ZoneService {

    List<Zone> findAll();

    Zone getById(long id);

    void save(Zone zone);

    void delete(Zone zone);
}
