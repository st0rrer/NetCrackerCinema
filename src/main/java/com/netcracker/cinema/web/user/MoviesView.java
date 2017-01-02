package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringView(name = MoviesView.VIEW_NAME, ui = UserUI.class)
public class MoviesView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";
	private static final String SORT_BY_LABEL_TITLE = "Sort by";
	private static final String SORT_BY_IMDB_OPTION = "IMDB";
	private static final String SORT_BY_PRICE_OPTION = "Price";

	@Autowired
	private MovieService movieService;
	@Autowired
    private SeanceService seanceService;
	@Autowired
    private HallService hallService;
	private List<MovieComponent> movieComponents;

	@PostConstruct
	void init() {
        movieComponents = new ArrayList<>();
        addComponent(createSortButtons());

        List<Movie> movies = movieService.findWhereRollingPeriodWasStarted();
        for (Movie movie: movies) {
            MovieComponent movieComponent = new MovieComponent(movie);
            movieComponents.add(movieComponent);
            addComponent(movieComponent);
            List<Seance> seances = seanceService.findAll(new SeanceFilter().forMovieId(movie.getId()).actual());
            for(Seance seance: seances) {
                Hall hall = hallService.getById(seance.getHallId());
                movieComponent.addSeance(seance, hall);
            }
        }

        setMargin(true);
        setSpacing(true);
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}


	private Component createSortButtons() {
		NativeSelect selector = new NativeSelect(SORT_BY_LABEL_TITLE);
		selector.addItem(SORT_BY_IMDB_OPTION);
		selector.addItem(SORT_BY_PRICE_OPTION);

		selector.addValueChangeListener((Property.ValueChangeListener) event -> {
            String value = (String) event.getProperty().getValue();
            if(value.equals(SORT_BY_IMDB_OPTION)) {
                movieComponents.sort((o1, o2) -> {
                    if(o1.getMovie().getImdb() > o2.getMovie().getImdb()) {
                        return -1;
                    } else if (o1.getMovie().getImdb() < o2.getMovie().getImdb()){
                        return 1;
                    } else {
                        return 0;
                    }
                });
            }

            if(value.equals(SORT_BY_PRICE_OPTION)) {
                movieComponents.sort((o1, o2) -> {
                    if (o1.getMovie().getBasePrice() > o2.getMovie().getBasePrice()) {
                        return 1;
                    } else if(o1.getMovie().getBasePrice() < o2.getMovie().getBasePrice()) {
                        return -1;
                    } else {
                        return 0;
                    }
                });
            }

            redrawMovies();
        });

		selector.setNullSelectionAllowed(false);
		selector.setValue(SORT_BY_IMDB_OPTION);

		return selector;
	}

	private void redrawMovies() {
	    for(MovieComponent movieComponent: movieComponents) {
	        removeComponent(movieComponent);
        }

        for (MovieComponent movieComponent: movieComponents) {
	        addComponent(movieComponent);
        }
    }
}
