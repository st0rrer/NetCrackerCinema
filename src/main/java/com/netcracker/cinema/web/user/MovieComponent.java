package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.HallService;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;

import java.text.SimpleDateFormat;
import java.util.List;

class MovieComponent extends HorizontalLayout {
	
	public MovieComponent(Movie movie, List<Seance> seances, HallService hallService) {
		setMargin(true);
		setSpacing(true);
		addComponent(createPoster(movie));
		addComponent(createDetails(movie));
		addComponent(createSeances(seances, hallService));
	}

	private Component createSeances(List<Seance> seances, HallService hallService) {
		VerticalLayout root = new VerticalLayout();
		root.setSpacing(true);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY HH:mm");
		for(Seance seance: seances) {
			VerticalLayout seanceInfo = new VerticalLayout();

			Label date = new Label(dateFormat.format(seance.getSeanceDate()));
			seanceInfo.addComponent(date);

			Label hall = new Label(hallService.getById(seance.getHallId()).getName());
			seanceInfo.addComponent(hall);

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

		Label imdb = new Label("IMDB: " + movie.getImdb() / 10);
		root.addComponent(imdb);

		Label duration = new Label("Duration: " + movie.getDuration());

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
