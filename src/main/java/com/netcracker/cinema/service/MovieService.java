package com.netcracker.cinema.service;

import com.netcracker.cinema.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by dimka on 21.11.2016.
 */
public interface MovieService {

    List<Movie> findAll();

    List<Movie> findWhereRollingPeriodWasStarted();

    Movie getById(long id);

    void save(Movie movie);

    void delete(Movie movie);

    boolean editableMovie(long id);
}
