package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.service.PlaceService;
import com.vaadin.ui.*;

import java.util.List;

public class HallView extends CustomComponent {

    public HallView(Hall hall, PlaceService placeService) {
        VerticalLayout root = new VerticalLayout();
        root.setMargin(true);
        root.setSpacing(true);

        HorizontalLayout posterAndTextDetails = new HorizontalLayout();
        posterAndTextDetails.setSpacing(true);
        root.addComponent(posterAndTextDetails);

        addTitle(hall, placeService, posterAndTextDetails);
        setCompositionRoot(root);
    }

    private void addTitle(Hall hall, PlaceService placeService, Layout layout) {
        List<Place> places = placeService.findAll();
        for (Place place : places) {
            Label title = new Label(String.valueOf(place.getNumber()));
            layout.addComponent(title);
            title.setWidth("50px");
        }
    }
}

