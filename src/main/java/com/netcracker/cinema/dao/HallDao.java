package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Hall;

import java.util.List;

public interface HallDao {
    List<Hall> findAll();

    Hall getById(long id);

    void save(Hall hall);

    void delete(Hall hall);
}
