package com.netcracker.cinema.web.common;

import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Zone;
import com.netcracker.cinema.service.PlaceService;
import com.netcracker.cinema.service.TicketService;
import com.netcracker.cinema.service.ZoneService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
public class TicketSelect extends GridLayout {
    private static final String PLACE_STYLE = "place-button";
    private static final String SELECTED_PLACE_STYLE = "selected-place";
    private static final String ZONE_ONE_PLACE_STYLE = "zone-1-button";
    private static final String ZONE_TWO_PLACE_STYLE = "zone-2-button";
    private static final String ZONE_THREE_PLACE_STYLE = "zone-3-button";
    private Seance seance;
    private List<PlaceButton> placeButtons;
    private Set<PlaceSelectedListener> listeners;

    private Zone zoneOneStyled;
    private Zone zoneTwoStyled;
    private Zone zoneThreeStyled;

    @Autowired
    private PlaceService placeService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ZoneService zoneService;

    public void buildForThisSeance(Seance seance) {
        addStyleName("tickets-select");
        zoneOneStyled = null;
        zoneTwoStyled = null;
        zoneThreeStyled = null;
        this.seance = seance;
        listeners = new HashSet<>();
        List<Place> places = placeService.getByHall(seance.getHallId());
        placeButtons = new ArrayList<>(places.size());
        removeAllComponents();
        adjustGridSize(places);
        for (Place place : places) {
            PlaceButton placeButton = new PlaceButton(place);
            placeButtons.add(placeButton);
            addComponent(placeButton, place.getNumber() - 1, place.getRowNumber() - 1);
        }
    }

    public List<Place> getSelectedPlaces() {
        List<Place> selectedPlaces = new ArrayList<>();
        for (PlaceButton placeButton : placeButtons) {
            if (placeButton.isSelected())
                selectedPlaces.add(placeButton.getPlace());
        }
        return selectedPlaces;
    }

    public void addPlaceSelectedListener(PlaceSelectedListener listener) {
        listeners.add(listener);
    }

    public void removePlaceSelectedListener(PlaceSelectedListener listener) {
        listeners.remove(listener);
    }

    private void adjustGridSize(List<Place> places) {
        int rows = 0, columns = 0;
        for (Place place : places) {
            if (place.getNumber() > columns) {
                columns = place.getNumber();
            }
            if (place.getRowNumber() > rows) {
                rows = place.getRowNumber();
            }
        }
        setColumns(columns);
        setRows(rows);
    }

    private class PlaceButton extends Button {
        private Place place;
        private boolean selected;

        public PlaceButton(Place place) {
            this.place = place;
            this.selected = false;
            Zone placeZone = zoneService.getById(place.getZoneId());
            setStyleName(PLACE_STYLE);

            if(zoneOneStyled == null || zoneOneStyled.equals(placeZone)) {
                zoneOneStyled = placeZone;
                addStyleName(ZONE_ONE_PLACE_STYLE);
            } else if(zoneTwoStyled == null || zoneTwoStyled.equals(placeZone)) {
                zoneTwoStyled = placeZone;
                addStyleName(ZONE_TWO_PLACE_STYLE);
            } else if(zoneThreeStyled == null || zoneThreeStyled.equals(placeZone)) {
                zoneThreeStyled = placeZone;
                addStyleName(ZONE_THREE_PLACE_STYLE);
            }

            setCaption(place.getRowNumber() + " " + place.getNumber());
            if (ticketService.isAlreadyBookedTicket(seance.getId(), place.getId())) {
                setEnabled(false);
            }
            addClickListener((ClickListener) event -> {
                selected = !selected;
                if (selected) {
                    addStyleName(SELECTED_PLACE_STYLE);
                } else {
                    removeStyleName(SELECTED_PLACE_STYLE);
                }

                for(PlaceSelectedListener placeSelectedListener: listeners) {
                    placeSelectedListener.placeClicked(place, selected);
                }
            });
        }

        public Place getPlace() {
            return place;
        }

        public boolean isSelected() {
            return selected;
        }
    }

    public interface PlaceSelectedListener {
        void placeClicked(Place place, boolean selected);
    }
}