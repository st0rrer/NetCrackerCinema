package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> findAll();

    List<Movie> findWhereRollingPeriodWasStarted();

    Movie getById(long id);

    void save(Movie movie);

    void delete(Movie movie);

    boolean editableMovie(long id);
}
