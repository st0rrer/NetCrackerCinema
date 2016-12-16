package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.SeanceService;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.List;

public class MovieListContainer extends VerticalLayout {
    private List<MovieComponent> movieComponents;

    public MovieListContainer(List<Movie> movies, SeanceService seanceService, HallService hallService) {
        movieComponents = new ArrayList<>();

        for(Movie movie: movies) {
            movieComponents.add(new MovieComponent(movie, seanceService.findAll(new SeanceFilter().actual()
                    .forMovieId(movie.getId()).orderByStartDateAsc()), hallService));
        }

        for(MovieComponent movieComponent: movieComponents) {
            addComponent(movieComponent);
        }

        setImmediate(true);
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
}
