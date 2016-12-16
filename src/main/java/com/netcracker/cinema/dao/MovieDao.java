package com.netcracker.cinema.dao;

import com.netcracker.cinema.model.Movie;

import java.util.List;

/**
 * Created by dimka on 16.11.2016.
 */
public interface MovieDao {

    List<Movie> findAll();

    List<Movie> findMoviesWhichHaveSessionsForThisWeek();

    Movie getById(long id);

    void save(Movie movie);

    void delete(Movie movie);
}
