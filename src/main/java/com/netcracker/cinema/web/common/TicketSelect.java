package com.netcracker.cinema.web.common;

import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Zone;
import com.netcracker.cinema.service.*;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringComponent
@UIScope
public class TicketSelect extends VerticalLayout {
    private static final String PLACE_STYLE = "place-button";
    private static final String SELECTED_PLACE_STYLE = "selected-place";
    private static final String ZONE_ONE_PLACE_STYLE = "zone-1-button";
    private static final String ZONE_TWO_PLACE_STYLE = "zone-2-button";
    private static final String ZONE_THREE_PLACE_STYLE = "zone-3-button";
    private Seance seance;
    private List<PlaceButton> placeButtons;
    private Zone zoneOneStyled;
    private Zone zoneTwoStyled;
    private Zone zoneThreeStyled;
    private int totalPrice;
    private GridLayout areaForTicketSelect;
    private Label areaForTotalPrice;

    @Autowired
    private PlaceService placeService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private ZoneService zoneService;
    @Autowired
    private PriceService priceService;
    @Autowired
    private MovieService movieService;

    @PostConstruct
    public void init() {
        this.setSpacing(true);
        this.setMargin(true);
        this.addStyleName("tickets-select");
    }

    public void buildForThisSeance(Seance seance) {
        totalPrice = 0;
        zoneOneStyled = null;
        zoneTwoStyled = null;
        zoneThreeStyled = null;
        this.seance = seance;
        List<Place> places = placeService.getByHall(seance.getHallId());
        placeButtons = new ArrayList<>(places.size());
        removeAllComponents();
        instanceTotalPrice();
        adjustGridSize(places);
        for (Place place : places) {
            PlaceButton placeButton = new PlaceButton(place);
            placeButtons.add(placeButton);
            areaForTicketSelect.addComponent(placeButton, place.getNumber() - 1, place.getRowNumber() - 1);
        }
    }

    private void instanceTotalPrice() {
        areaForTotalPrice = new Label();
        areaForTotalPrice.setValue("Total price: " + totalPrice);
        this.addComponent(areaForTotalPrice);
        this.setComponentAlignment(areaForTotalPrice, Alignment.BOTTOM_CENTER);
    }

    public List<Place> getSelectedPlaces() {
        List<Place> selectedPlaces = new ArrayList<>();
        for (PlaceButton placeButton : placeButtons) {
            if (placeButton.isSelected())
                selectedPlaces.add(placeButton.getPlace());
        }
        return selectedPlaces;
    }

    public int getTotalPrice() {
        return totalPrice;
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
        areaForTicketSelect = new GridLayout();
        this.addComponent(areaForTicketSelect);
        areaForTicketSelect.setColumns(columns);
        areaForTicketSelect.setRows(rows);
    }

    private class PlaceButton extends Button {
        private Place place;
        private boolean selected;

        PlaceButton(Place place) {
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
                areaForTotalPrice.setValue("Total price: " + totalPrice);
                if (selected) {
                    totalPrice += priceTicket(seance, place);
                    areaForTotalPrice.setValue("Total price: " + totalPrice);
                    addStyleName(SELECTED_PLACE_STYLE);
                } else {
                    totalPrice -= priceTicket(seance, place);
                    areaForTotalPrice.setValue("Total price: " + totalPrice);
                    removeStyleName(SELECTED_PLACE_STYLE);
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

    private int priceTicket(Seance seance, Place place) {
        int basePrice = movieService.getById(seance.getMovieId()).getBasePrice();
        int placePrice = priceService.getPriceBySeanceColRow(seance.getId(), place.getNumber(), place.getRowNumber());
        return basePrice + placePrice;
    }
}