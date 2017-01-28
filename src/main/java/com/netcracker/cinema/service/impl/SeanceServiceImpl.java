package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.dao.SeanceDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SeanceServiceImpl implements SeanceService {

    private SeanceDao seanceDao;

    @Autowired
    public SeanceServiceImpl(SeanceDao seanceDao) {
        this.seanceDao = seanceDao;
    }

    @Override
    public List<Seance> findAll() {
        return seanceDao.findAll();
    }

    @Override
    public List<Seance> findAll(SeanceFilter filter) {
        return seanceDao.findAll(filter);
    }

    @Override
    public Seance getById(long id) {
        return seanceDao.getById(id);
    }

    @Override
    public List<Seance> getByHallAndDate(long id, Date date) {
        return seanceDao.getByHallAndDate(id, new java.sql.Date(date.getTime()));
    }

    @Override
    public void delete(Seance seance) {
        seanceDao.delete(seance);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void save(Seance seance) {
        seanceDao.save(seance);
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize) {
        return seanceDao.getPaginator(pageSize);
    }

    @Override
    public Paginator<Seance> getPaginator(int pageSize, SeanceFilter filter) {
        return seanceDao.getPaginator(pageSize, filter);
    }

    @Override
    public long getCountActiveMoviesById(long movieId) {
        return seanceDao.getCountActiveMoviesById(movieId);
    }
}