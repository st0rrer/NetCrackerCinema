package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class HallComponent extends VerticalLayout {

    public HallComponent(Hall hall, Movie movie) {
        setMargin(true);

        addTitle(hall);
        addTitleForSeance(movie);
        addPosterForSeance(movie);
        //TODO: show border when mouse enter component
         //addLayoutClickListener(event -> getUI().getNavigator().navigateTo(SeanceView.VIEW_NAME + "/" + hall.getId()));
    }

    private void addTitle(Hall hall) {
        Label title = new Label(hall.getName());
        title.setWidth("200px");
        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);
    }

    private void addTitleForSeance(Movie movie) {
        Label title = new Label(movie.getName());
        title.setWidth("50px");
        addComponent(title);
        setComponentAlignment(title, Alignment.MIDDLE_CENTER);
    }

    private void addPosterForSeance(Movie movie) {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
        poster.setWidth("50x");
        poster.setHeight("100px");
        addComponent(poster);
        setComponentAlignment(poster, Alignment.MIDDLE_CENTER);
    }
}
