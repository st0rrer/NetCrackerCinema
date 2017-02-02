package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> findAll();

    List<Movie> findWhereRollingPeriodWasStarted();

    Movie getById(long id);

    void save(Movie movie);

    void delete(Movie movie);
}
