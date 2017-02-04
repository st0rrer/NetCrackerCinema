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
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringView(name = MoviesView.VIEW_NAME, ui = UserUI.class)
public class MoviesView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";
	private static final String SORT_BY_IMDB_OPTION = "IMDB";
	private static final String SORT_BY_PRICE_OPTION = "Price";
	private static final int MAX_SEANCE_BUTTON_COUNT = 5;

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
        List<Movie> movies = movieService.findWhereRollingPeriodWasStarted();

        HorizontalLayout filterToolsLayout = new HorizontalLayout();
        filterToolsLayout.setSpacing(true);
        filterToolsLayout.addComponent(createSortButtons());
        filterToolsLayout.addComponent(createTextFieldForFindByName(movies));
        addComponent(filterToolsLayout);

        for (Movie movie: movies) {
            MovieComponent movieComponent = new MovieComponent(movie);
            movieComponents.add(movieComponent);
            List<Seance> seances = seanceService.findAll(new SeanceFilter().forMovieId(movie.getId()).orderByStartDateAsc().actual());
            int buttonCount = seances.size() < MAX_SEANCE_BUTTON_COUNT ? seances.size() : MAX_SEANCE_BUTTON_COUNT;
            for(int i = 0; i < buttonCount; i++) {
                Seance seance = seances.get(i);
                Hall hall = hallService.getById(seance.getHallId());
                movieComponent.addSeance(seance, hall);
            }
        }
        movieComponents.sort(new ImdbComparator());
        for (MovieComponent movieComponent: movieComponents) {
            addComponent(movieComponent);
        }

        setMargin(true);
        setSpacing(true);

	}

	@Override
	public void enter(ViewChangeEvent event) {
	}

	private Component createTextFieldForFindByName(List<Movie> movies) {
	    ComboBox findBox = new ComboBox("Filter by movie's title");
	    findBox.setFilteringMode(FilteringMode.CONTAINS);
	    findBox.setWidth("400px");
        for (Movie movie : movies) {
            findBox.addItem(movie.getName());
        }
        findBox.addValueChangeListener((Property.ValueChangeListener) event -> {
            String enteredMovieTitle = (String) event.getProperty().getValue();
            redrawAllMovies();
            if(enteredMovieTitle != null) {
                for (MovieComponent movieComponent : movieComponents) {
                    if (!movieComponent.getMovie().getName().equals(enteredMovieTitle)) {
                        removeComponent(movieComponent);
                    }
                }
            }
        });
        return findBox;
    }

	private Component createSortButtons() {
		NativeSelect selector = new NativeSelect("Sort by");
		selector.addItem(SORT_BY_IMDB_OPTION);
		selector.addItem(SORT_BY_PRICE_OPTION);

		selector.addValueChangeListener((Property.ValueChangeListener) event -> {
            String value = (String) event.getProperty().getValue();
            if(value.equals(SORT_BY_IMDB_OPTION)) {
                movieComponents.sort(new ImdbComparator());
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

            redrawAllMovies();
        });

		selector.setNullSelectionAllowed(false);
		selector.setValue(SORT_BY_IMDB_OPTION);

		return selector;
	}

	private void redrawAllMovies() {
	    for(MovieComponent movieComponent: movieComponents) {
	        removeComponent(movieComponent);
        }

        for (MovieComponent movieComponent: movieComponents) {
	        addComponent(movieComponent);
        }
    }

    private class ImdbComparator implements Comparator<MovieComponent> {
        @Override
        public int compare(MovieComponent o1, MovieComponent o2) {
            if(o1.getMovie().getImdb() > o2.getMovie().getImdb()) {
                return -1;
            } else if (o1.getMovie().getImdb() < o2.getMovie().getImdb()){
                return 1;
            } else {
                return 0;
            }
        }
    }
}
