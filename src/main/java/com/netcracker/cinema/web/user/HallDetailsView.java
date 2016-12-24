package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@SpringView(name = HallDetailsView.VIEW_NAME, ui = UserUI.class)
@UIScope
public class HallDetailsView extends VerticalLayout implements View {

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
        ticketSelect.buildForThisSeance(seance);
        addSeanceInfo(seance);
        addComponent(ticketSelect);
        addComponent(addButtonBook(seance));
    }

    private Component addSeanceInfo(Seance seance) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        GridLayout layout = new GridLayout();
        ExternalResource resource = new ExternalResource(movieService.getById(seance.getMovieId()).getPoster());
        Image poster = new Image(null, resource);
        poster.setHeight("250px");
        poster.setWidth("175px");
        layout.addComponent(poster);
        addComponent(layout);
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
        return layout;
    }

    private Component addButtonBook(Seance seance) {
        GridLayout layout = new GridLayout();
        Button book = new Button("Book");
        Ticket ticket = new Ticket();
        layout.addComponent(book);
        book.addClickListener(clickEvent -> {
            for (Place place : ticketSelect.getSelectedPlaces()) {
                if (ticketService.isAlreadyBookedTicket(seance.getId(), place.getId())) {
                    return;
                } else {
                    layout.addComponent(emailBox(place, ticket, seance));
                }
            }
        });
        return layout;
    }

    private Component emailBox(Place place, Ticket ticket, Seance seance) {
        Button button = new Button("OK");
        HorizontalLayout subContent = new HorizontalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        TextField textField = new TextField("Enter your email:");
        textField.setValue("");
        textField.addTextChangeListener(textChangeEvent -> {
            ticket.setEmail(textField.getValue());
        });
        subContent.addComponent(textField);
        subContent.addComponent(button);
        button.addClickListener(clickEvent -> {
            ticket.setEmail(textField.getValue());
            ticket.setPrice(144);
            ticket.setCode(7777);
            ticket.setPlaceId(place.getId());
            ticket.setSeanceId(seance.getId());
            ticketService.save(ticket);
        });
        return subContent;
    }
}