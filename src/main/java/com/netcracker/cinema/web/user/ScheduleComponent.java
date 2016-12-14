package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import java.text.SimpleDateFormat;

public class ScheduleComponent extends VerticalLayout {

    public ScheduleComponent(Seance seances, MovieService movieService) {
        setMargin(true);
        addPoster(seances, movieService);
        addSeanceAttributes(seances, movieService);
        addLayoutClickListener(event -> getUI().getNavigator().navigateTo(ScheduleDetailsView.VIEW_NAME + "/" + seances.getId()));
    }

    private void addSeanceAttributes(Seance seance, MovieService movieService) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Label name = new Label(movieService.getById(seance.getMovieId()).getName());
        addComponent(name);
        Label date = new Label("Date: " + dateFormat.format(seance.getSeanceDate()));
        addComponent(date);
        Label time = new Label("Time: " + timeFormat.format(seance.getSeanceDate()));
        addComponent(time);
        Label hall = new Label("Hall: " + seance.getHallId());
        addComponent(hall);
    }

    private void addPoster(Seance seance, MovieService movieService) {
        ExternalResource resource = new ExternalResource(movieService.getById(seance.getMovieId()).getPoster());
        Image poster = new Image(null, resource);
        poster.setHeight("350px");
        poster.setWidth("230px");
        addComponent(poster);
    }
}
