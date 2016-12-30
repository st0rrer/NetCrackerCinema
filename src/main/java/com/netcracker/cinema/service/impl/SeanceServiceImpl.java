package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.SeanceDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.dao.Paginator;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

//TODO: need to think about transactional
public class SeanceServiceImpl implements SeanceService {

    private SeanceDao seanceDao;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieService movieService;

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
        return seanceDao.getByHallAndDate(id, date);
    }

    @Override
    public void delete(Seance seance) {
        seanceDao.delete(seance);
    }

    @Transactional
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
    public boolean editableSeance(long seanceId) {
        List<Ticket> list = ticketService.getBySeance(seanceId);
        return list.size() == 0;
    }

    @Override
    public boolean checkDate(Seance seance) {
        Movie movie = movieService.getById(seance.getMovieId());
        return (seance.getSeanceDate().after(movie.getStartDate()) &&
                seance.getSeanceDate().before(movie.getEndDate()));
    }
}