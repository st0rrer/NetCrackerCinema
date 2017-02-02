package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Hall;

import java.util.List;

public interface HallService {

    List<Hall> findAll();

    Hall getById(long id);

    void save(Hall hall);

    void delete(Hall hall);
}
