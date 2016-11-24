package com.netcracker.cinema.dao.impl;

import com.netcracker.cinema.dao.HallDao;
import com.netcracker.cinema.dao.ZoneDao;
import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Zone;
import com.netcracker.cinema.podam.CreatedWithStrategy;
import com.netcracker.cinema.service.MovieService;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by dimka on 21.11.2016.
 */
public class Main {
    public static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        Locale.setDefault(Locale.ENGLISH);
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("C:\\IdeaProjects\\workSpace\\NetCrackerCinema\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
        MovieService movieService = (MovieService) applicationContext.getBean("movieServiceImpl");
        CreatedWithStrategy strategy = new CreatedWithStrategy();
        List<Movie> movies1 = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            movies1.add(strategy.getMoviePodam());
        }
        List<Movie> movies2 = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            movies1.add(strategy.getMoviePodam());
        }

        ThreadMovieMerge merge = new ThreadMovieMerge(movies1, movieService);
        ThreadMovieMerge merge1 = new ThreadMovieMerge(movies2, movieService);
        merge.start();
        merge.sleep(50);
        merge1.start();
    }

    static class ThreadMovieMerge extends Thread {

        private List<Movie> movies;
        MovieService movieService;

        ThreadMovieMerge (List<Movie> movies, MovieService movieService) {
            this.movies = movies;
            this.movieService = movieService;
        }

        @Override
        public void run() {
            for(Movie movie1 : movies) {
                movie1.setId(0);
                LOGGER.info("Thread " +movie1);
                movieService.save(movie1);
                try {
                    this.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

