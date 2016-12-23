package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@SpringView(name = HallDetailsView.VIEW_NAME, ui = UserUI.class)
public class HallDetailsView extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "details";

    private static final Logger logger = Logger.getLogger(HallDetailsView.class);

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private HallService hallService;

    @Autowired
    private TicketSelect ticketSelect;

    @Autowired
    private PriceService priceService;

    @Autowired
    private TicketService ticketService;

    @PostConstruct
    public void init() {

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        long seanceId;
        long hallId;
        try {
            seanceId = Long.parseLong(event.getParameters());
        } catch (NumberFormatException e) {
            logger.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleView.VIEW_NAME);
            return;
        }
        Seance seance = null;
        Hall hall = null;
        try {
            seance = seanceService.getById(seanceId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Can't find seance with this id " + seanceId, e);
            addComponent(new Label("Can't find seance with this id " + seanceId));
            return;
        }
        try {
            hallId = seance.getHallId();
        } catch (NumberFormatException e) {
            logger.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleView.VIEW_NAME);
            return;
        }
        try {
            hall = hallService.getById(hallId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Can't find hall with this id " + hallId, e);
            addComponent(new Label("Can't find seance with this id " + hallId));
            return;
        }
        setMargin(true);
        setSpacing(true);
        addSeanceInfo(seance);
        ticketSelect.buildForThisSeance(seance);
        ticketSelect.addButtonBook(seance);
        addComponent(ticketSelect);
    }

    private void addSeanceInfo(Seance seance) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        VerticalLayout layout = new VerticalLayout();
        addComponent(layout);
        ExternalResource resource = new ExternalResource(movieService.getById(seance.getMovieId()).getPoster());
        Image poster = new Image(null, resource);
        poster.setHeight("250px");
        poster.setWidth("175px");
        layout.addComponent(poster);
        Label name = new Label(movieService.getById(seance.getMovieId()).getName());
        layout.addComponent(name);
        Label date = new Label("Date: " + dateFormat.format(seance.getSeanceDate()));
        layout.addComponent(date);
        Label time = new Label("Time: " + timeFormat.format(seance.getSeanceDate()));
        layout.addComponent(time);
        Label hall = new Label("Hall: " + hallService.getById(seance.getHallId()).getName());
        layout.addComponent(hall);
        Label pricesum = new Label("Price: ");
        layout.addComponent(pricesum);
        addComponent(layout);
    }
}