package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;

@SpringView(name = MovieDetailsView.VIEW_NAME, ui = UserUI.class)
public class MovieDetailsView extends CustomComponent implements View {
    public static final String VIEW_NAME = "details";
    private static final Logger logger = Logger.getLogger(MovieDetailsView.class);

    @Autowired
    private MovieService movieService;

    @PostConstruct
    public void init() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        long movieId;
        try {
            movieId = Long.parseLong(event.getParameters());
        }
        catch (NumberFormatException e) {
            logger.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(MoviesView.VIEW_NAME);
            return;
        }
        //TODO: get all seances for this movie
        Movie movie = null;
        try {
            movie = movieService.getById(movieId);
        }
        catch (EmptyResultDataAccessException e) {
            logger.info("Can't find movie with this id " + movieId, e);
            setCompositionRoot(new Label("Can't find movie with this id " + movieId));
            return;
        }

        setCompositionRoot(new MovieDetailsComponent(movie, null));
    }
}
