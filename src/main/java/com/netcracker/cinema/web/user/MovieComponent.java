package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

class MovieComponent extends VerticalLayout {
	
	public MovieComponent(Movie movie) {
		setMargin(true);

		addPoster(movie);
		addTitle(movie);

		//TODO: show border when mouse enter component
		addLayoutClickListener(event -> getUI().getNavigator().navigateTo(MovieDetailsView.VIEW_NAME + "/" + movie.getId()));
	}

	private void addTitle(Movie movie) {
		Label title = new Label(movie.getName());
		title.setWidth("200px");
		addComponent(title);
		setComponentAlignment(title, Alignment.MIDDLE_CENTER);
	}

	private void addPoster(Movie movie) {
		ExternalResource res = new ExternalResource(movie.getPoster());
		Image poster = new Image(null, res);
		poster.setWidth("200px");
		poster.setHeight("300px");
		addComponent(poster);
		setComponentAlignment(poster, Alignment.MIDDLE_CENTER);
	}
}
