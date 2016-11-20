package com.netcracker.cinema.web.user;

import java.util.List;
import javax.annotation.PostConstruct;
import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

@SpringView(name = MoviesView.VIEW_NAME, ui = UserUI.class)
public class MoviesView extends GridLayout implements View {

	@Autowired
	private MovieDao movieDao;

	public static final String VIEW_NAME = "";
	private final int GRID_COLUMNS = 4;

	@PostConstruct
	void init() {
		List<Movie> movies = movieDao.findAll();
		int rows = movies.size() % GRID_COLUMNS + 1;
		setColumns(GRID_COLUMNS);
		setRows(rows);
		for(Movie movie: movies) {
			MovieComponent movieComponent = new MovieComponent(movie);
			addComponent(movieComponent);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
	}
}
