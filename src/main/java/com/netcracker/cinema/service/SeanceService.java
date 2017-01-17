package com.netcracker.cinema.service;


import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.dao.Paginator;

import java.util.Date;
import java.util.List;

public interface SeanceService {
    List<Seance> findAll();
    List<Seance> findAll(SeanceFilter filter);
    Seance getById(long id);
    List<Seance> getByHallAndDate(long id, Date date);
    void delete(Seance seance);
    void save(Seance seance);
    Paginator<Seance> getPaginator(int pageSize);
    Paginator<Seance> getPaginator(int pageSize, SeanceFilter seanceFilter);
    boolean editableSeance(long seanceId);
    boolean checkDate(Seance seance);
    boolean checkIfInWorkingTime(Seance seance);
    boolean checkIfHallIsFree(Seance newSeance);
    long getCountActiveMoviesById(long movieId);
}