package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

public class SeanceComponent extends CustomComponent {

    public SeanceComponent(Seance seances, MovieService movieService) {
        VerticalLayout root = new VerticalLayout();
        HorizontalLayout posterAndTextDetails = new HorizontalLayout();
        posterAndTextDetails.setSpacing(true);
        root.setMargin(true);
        root.setSpacing(true);
        root.addComponent(posterAndTextDetails);
        addPoster(seances, posterAndTextDetails, movieService);
        addSeanceAttributes(seances, posterAndTextDetails, movieService);
        setCompositionRoot(root);
        root.addLayoutClickListener(event -> getUI().getNavigator().navigateTo(SeanceDetailsView.VIEW_NAME + "/" + seances.getId()));
    }

    private void addSeanceAttributes(Seance seance, Layout posterAndTextDetails, MovieService movieService) {
        VerticalLayout textDetails = new VerticalLayout();
        posterAndTextDetails.addComponent(textDetails);

        Label name = new Label(movieService.getById(seance.getMovieId()).getName());
        textDetails.addComponent(name);

        Label date = new Label("Date: " + String.valueOf(seance.getSeanceDate()));
        textDetails.addComponent(date);

        Label time = new Label("Time: " + seance.getSeanceDate().getTime());
        textDetails.addComponent(time);

        Label hall = new Label("Hall: " + seance.getHallId());
        textDetails.addComponent(hall);
    }

    private void addPoster(Seance seance, Layout layout, MovieService movieService) {
        ExternalResource resource = new ExternalResource(movieService.getById(seance.getMovieId()).getPoster());
        Image poster = new Image(null, resource);
        poster.setHeight("350px");
        poster.setWidth("230px");
        layout.addComponent(poster);
    }

}
