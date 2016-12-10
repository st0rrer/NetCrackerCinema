package com.netcracker.cinema.dao;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Seance;

import java.util.List;

public interface SeanceDao {

    List<Seance> findAll();
    List<Seance> findAll(SeanceFilter filter);
    Seance getById(long id);
    void save(Seance seance);
    void delete(Seance seance);
    Paginator<Seance> getPaginator(int pageSize);
    Paginator<Seance> getPaginator(int pageSize, SeanceFilter filter);
}
