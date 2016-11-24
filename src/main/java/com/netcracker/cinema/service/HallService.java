package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Hall;

import java.util.List;

/**
 * Created by dimka on 24.11.2016.
 */
public interface HallService {

    List<Hall> findAll();

    Hall getById(int id);

    void save(Hall hall);

    void delete(Hall hall);
}
