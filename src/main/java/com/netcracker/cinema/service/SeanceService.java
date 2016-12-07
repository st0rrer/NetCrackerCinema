package com.netcracker.cinema.service;


import com.netcracker.cinema.model.Seance;
import java.util.List;

public interface SeanceService {
    List<Seance> findAll();
    Seance getById(long id);
    void delete(Seance seance);
    void save(Seance seance);
}
