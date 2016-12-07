package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Seance;

import java.util.List;

public interface SeanceDao {

    List<Seance> findAll();
    Seance getById(long id);
    void save(Seance seance);
    void delete(Seance seance);
}
