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

	@Autowired
	private MovieService movieService;
	@Autowired
	private MovieListContainer movieListContainer;

	public static final String VIEW_NAME = "";

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
		NativeSelect selector = new NativeSelect("Sort by");
		selector.addItem("IMDB");
		selector.addItem("Price");

		selector.addValueChangeListener((Property.ValueChangeListener) event -> {
            String value = (String) event.getProperty().getValue();
            if(value.equals("IMDB")) {
                movieListContainer.sortByImdb();
            }

            if(value.equals("Price")) {
                movieListContainer.sortByPrice();
            }
        });

		selector.setNullSelectionAllowed(false);
		selector.setValue("IMDB");

		return selector;
	}
}
