package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Zone;
import com.netcracker.cinema.service.PlaceService;
import com.netcracker.cinema.service.ZoneService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
public class TicketSelect extends GridLayout {

    public void buildForThisSeance(Seance seance) {
    }

    public List<Place> getSelectedPlaces() {
        return null;
    }
}
