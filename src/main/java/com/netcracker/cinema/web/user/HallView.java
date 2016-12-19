package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.PlaceService;
import com.vaadin.ui.*;

import java.util.List;

public class HallView extends CustomComponent {

    public HallView(Hall hall, PlaceService placeService, Seance seance, MovieService movieService) {
        VerticalLayout root = new VerticalLayout();
        root.setMargin(true);
        root.setSpacing(true);

        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        root.addComponent(layout);


        addPlaces(placeService, layout);
        setCompositionRoot(root);
        ScheduleComponent seanceComponent = new ScheduleComponent(seance, movieService);
        root.addComponent(seanceComponent);
    }

    private void addPlaces(PlaceService placeService, Layout layout) {
        List<Place> places = placeService.findAll();
        for (Place place : places) {
            Label title = new Label(String.valueOf(place.getNumber()));
            layout.addComponent(title);
            title.setWidth("50px");
        }
    }

}

