package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by aogim on 02.01.2017.
 */
class MovieComponent extends HorizontalLayout {
    private Movie movie;
    private VerticalLayout seancesRoot;

    public MovieComponent(Movie movie) {
        setSpacing(true);
        this.movie = movie;
        seancesRoot = new VerticalLayout();

        addComponent(createPoster());
        addComponent(createDetails());
        addComponent(seancesRoot);
    }

    public void addSeance(Seance seance, Hall hall) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm");
        VerticalLayout seanceInfo = new VerticalLayout();
        seanceInfo.addStyleName("selectable-seance");

        Label date = new Label(dateFormat.format(seance.getSeanceDate()));
        seanceInfo.addComponent(date);

        Label hallName = new Label(hall.getName());
        seanceInfo.addComponent(hallName);

        seanceInfo.addLayoutClickListener(event -> getUI().getNavigator()
                .navigateTo(HallDetailsViewUser.VIEW_NAME + "/" + seance.getId()));

        seancesRoot.addComponent(seanceInfo);
    }

    public Movie getMovie() {
        return movie;
    }

    private Component createPoster() {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
        poster.setWidth("200px");
        poster.setHeight("300px");
        return poster;
    }

    private Component createDetails() {
        VerticalLayout root = new VerticalLayout();

        Label title = new Label(movie.getName());
        title.addStyleName("movie-title");
        root.addComponent(title);

        Label description = new Label(movie.getDescription());
        description.setWidth("500px");
        root.addComponent(description);

        Label imdb = new Label("IMDB: " + (double) movie.getImdb() / 10);
        root.addComponent(imdb);

        Label duration = new Label("Duration: " + movie.getDuration() + " min");
        root.addComponent(duration);

        Label basePrice = new Label("Estimated price: " + movie.getBasePrice());
        root.addComponent(basePrice);

        return root;
    }

}
