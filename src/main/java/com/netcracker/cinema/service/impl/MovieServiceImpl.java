package com.netcracker.cinema.service.impl;

import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
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
    MovieServiceImpl(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public List<Movie> findAll() {
        return movieDao.findAll();
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
}
