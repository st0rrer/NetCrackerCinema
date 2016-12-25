package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.SeanceService;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringComponent
@ViewScope
public class MovieListContainer extends VerticalLayout {
    @Autowired
    private SeanceService seanceService;
    @Autowired
    private HallService hallService;

    private List<MovieComponent> movieComponents;

    public void buildForMovies(List<Movie> movies) {
        movieComponents = new ArrayList<>();

        if(movies.size() != 0) {
            for (Movie movie : movies) {
                movieComponents.add(new MovieComponent(movie, seanceService.findAll(new SeanceFilter().actual()
                        .forMovieId(movie.getId()).orderByStartDateAsc())));
            }

            for (MovieComponent movieComponent : movieComponents) {
                addComponent(movieComponent);
            }
        } else {
            Label noData = new Label("Sorry. Movies are not available now");
            addComponent(noData);
        }

        setSpacing(true);
    }

    public void sortByImdb() {
        movieComponents.sort((o1, o2) -> {
            if(o1.getMovie().getImdb() > o2.getMovie().getImdb()) {
                return -1;
            } else if(o1.getMovie().getImdb() < o2.getMovie().getImdb()) {
                return 1;
            } else {
                return 0;
            }
        });
        redraw();
    }

    public void sortByPrice() {
        movieComponents.sort((o1, o2) -> {
            if(o1.getMovie().getBasePrice() > o2.getMovie().getBasePrice()) {
                return 1;
            } else if(o1.getMovie().getBasePrice() < o2.getMovie().getBasePrice()) {
                return -1;
            } else {
                return 0;
            }
        });
        redraw();
    }

    private void redraw() {
        for(MovieComponent movieComponent: movieComponents) {
            removeComponent(movieComponent);
        }

        for(MovieComponent movieComponent: movieComponents) {
            addComponent(movieComponent);
        }
    }

    private class MovieComponent extends HorizontalLayout {
        private Movie movie;

        public MovieComponent(Movie movie, List<Seance> seances) {
            this.movie = movie;

            setSpacing(true);
            addComponent(createPoster(movie));
            addComponent(createDetails(movie));
            addComponent(createSeances(seances));
        }

        public Movie getMovie() {
            return movie;
        }

        private Component createSeances(List<Seance> seances) {
            VerticalLayout root = new VerticalLayout();
            root.setSpacing(true);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm");
            for (Seance seance : seances) {
                VerticalLayout seanceInfo = new VerticalLayout();

                Label date = new Label(dateFormat.format(seance.getSeanceDate()));
                seanceInfo.addComponent(date);

                Label hall = new Label(hallService.getById(seance.getHallId()).getName());
                seanceInfo.addComponent(hall);

                seanceInfo.addLayoutClickListener(event -> getUI().getNavigator()
                        .navigateTo(HallDetailsViewUser.VIEW_NAME + "/" + seance.getId()));

                root.addComponent(seanceInfo);
            }

            return root;
        }

        private Component createDetails(Movie movie) {
            VerticalLayout root = new VerticalLayout();
            root.setSpacing(true);

            Label title = new Label(movie.getName());
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

        private Component createPoster(Movie movie) {
            ExternalResource res = new ExternalResource(movie.getPoster());
            Image poster = new Image(null, res);
            poster.setWidth("200px");
            poster.setHeight("300px");
            return poster;
        }
    }

}
