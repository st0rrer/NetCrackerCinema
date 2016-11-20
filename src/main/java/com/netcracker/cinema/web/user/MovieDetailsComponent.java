package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

import java.util.List;

public class MovieDetailsComponent extends CustomComponent {
    public MovieDetailsComponent(Movie movie, List<Seance> seances) {
        VerticalLayout root = new VerticalLayout();
        root.setMargin(true);
        root.setSpacing(true);

        HorizontalLayout posterAndTextDetails = new HorizontalLayout();
        posterAndTextDetails.setSpacing(true);
        root.addComponent(posterAndTextDetails);

        ExternalResource resource = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, resource);
        poster.setHeight("350px");
        poster.setWidth("230px");
        posterAndTextDetails.addComponent(poster);

        VerticalLayout textDetails = new VerticalLayout();
        posterAndTextDetails.addComponent(textDetails);

        Label title = new Label(movie.getName());
        textDetails.addComponent(title);

        Label description = new Label(movie.getDescription());
        description.setWidth("460px");
        textDetails.addComponent(description);

        Label imdb = new Label("IMDB: " + String.valueOf((float)movie.getImdb() / 10));
        textDetails.addComponent(imdb);

        Label duration = new Label("Duration: " + movie.getDuration() + " min");
        textDetails.addComponent(duration);

        Label estimatedPrice = new Label("Estimated price: " + movie.getBasePrice());
        textDetails.addComponent(estimatedPrice);

        Label seance = new Label("Seances");
        root.addComponent(seance);

        //TODO: display seances

        setCompositionRoot(root);
    }
}
