package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = MovieDetailsView.VIEW_NAME, ui = UserUI.class)
public class MovieDetailsView extends CustomComponent implements View {
    public static final String VIEW_NAME = "details";

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
            getUI().getNavigator().navigateTo(MoviesView.VIEW_NAME);
            return;
        }
        //TODO: get all seances for this movie
        //TODO: catch exception for non existing movie
        Movie movie = movieService.getById(movieId);
        setCompositionRoot(new MovieDetailsComponent(movie, null));
    }
}
