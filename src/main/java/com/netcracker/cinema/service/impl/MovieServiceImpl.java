package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by dimka on 21.11.2016.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private MovieDao movieDao;
    @Autowired
    private SeanceService seanceService;
    @Autowired
    TicketService ticketService;

    @Autowired
    MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @Override
    public List<Movie> findMoviesWhichHaveSessionsForThisWeek() {
        return movieDao.findMoviesWhichHaveSessionsForThisWeek();
    }

    @Override
    public Movie getById(long id) {
        return movieDao.getById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void save(Movie movie) {
        movieDao.save(movie);
    }

    @Override
    public void delete(Movie movie) {
        movieDao.delete(movie);
    }

    @Override
    public boolean editableMovie(long id) {
        List<Seance> list = seanceService.findAll(new SeanceFilter().forMovieId(id).actual());
        int i = 0;
        for(Seance seance: list){
            if(i > 0) break;
           List<Ticket> tickets = ticketService.getBySeanceOrPlace(seance.getId());
            i += tickets.size();
        }
        if(i > 0) {
            return false;
        }
        else {
            return true;
        }
    }
}
