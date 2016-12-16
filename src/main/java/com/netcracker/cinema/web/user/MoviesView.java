package com.netcracker.cinema.web.user;

import java.util.List;
import javax.annotation.PostConstruct;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = MoviesView.VIEW_NAME, ui = UserUI.class)
public class MoviesView extends GridLayout implements View {

	@Autowired
	private MovieService movieService;
	@Autowired
	private SeanceService seanceService;
	@Autowired
	private HallService hallService;

	public static final String VIEW_NAME = "";

	@PostConstruct
	void init() {
		List<Movie> movies = movieService.findAll();

		for(Movie movie: movies) {
			addComponent(new MovieComponent(movie, seanceService.findAll(new SeanceFilter().actual().forMovieId(movie.getId())
			.orderByStartDateAsc()), hallService));
		}
	}
	@Override
	public void enter(ViewChangeEvent event) {
	}
}
