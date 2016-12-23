package com.netcracker.cinema.web.user;

import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.data.Property;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;

@SpringView(name = MoviesView.VIEW_NAME, ui = UserUI.class)
public class MoviesView extends VerticalLayout implements View {
	public static final String VIEW_NAME = "";
	private static final String SORT_BY_LABEL_TITLE = "Sort by";
	private static final String SORT_BY_IMDB_OPTION = "IMDB";
	private static final String SORT_BY_PRICE_OPTION = "Price";

	@Autowired
	private MovieService movieService;
	@Autowired
	private MovieListContainer movieListContainer;

	@PostConstruct
	void init() {
		movieListContainer.buildForMovies(movieService.findWhereRollingPeriodWasStarted());

		addComponent(createSortButtons());
		addComponent(movieListContainer);

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
                movieListContainer.sortByImdb();
            }

            if(value.equals(SORT_BY_PRICE_OPTION)) {
                movieListContainer.sortByPrice();
            }
        });

		selector.setNullSelectionAllowed(false);
		selector.setValue(SORT_BY_IMDB_OPTION);

		return selector;
	}
}
