package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

class MovieComponent extends VerticalLayout {
	
	public MovieComponent(Movie movie) {
		//TODO: title at center
		setMargin(true);
		ExternalResource res = new ExternalResource(movie.getPoster());
		Image poster = new Image(null, res);
		poster.setWidth("200px");
		poster.setHeight("300px");
		Label title = new Label(movie.getName());
		title.setWidth("200px");
		addComponent(poster);
		addComponent(title);
		setComponentAlignment(poster, Alignment.MIDDLE_CENTER);
		setComponentAlignment(title, Alignment.MIDDLE_CENTER);
	}
}
