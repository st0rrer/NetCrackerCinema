package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Zone;

import java.util.List;

public interface ZoneDao {

    List<Zone> findAll();

    Zone getById(long id);

    void save(Zone hall);

    void update(Zone hall);

    void delete(Zone hall);
}
