package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.web.UserUI;
import com.netcracker.cinema.web.common.TicketSelect;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;

@SpringView(name = HallDetailsViewUser.VIEW_NAME, ui = UserUI.class)
public class HallDetailsViewUser extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "details";

    private static final Logger logger = Logger.getLogger(HallDetailsViewUser.class);

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
            getUI().getNavigator().navigateTo(ScheduleViewUser.VIEW_NAME);
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
            getUI().getNavigator().navigateTo(ScheduleViewUser.VIEW_NAME);
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
        if (UI.getCurrent().getUI().getClass() == UserUI.class) {
            addSeanceInfo(seance);
            addComponent(addButtonBook(seance));
        }

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
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(ticketSelect);
        Button book = new Button("Book");
        layout.addComponent(book);
        book.addClickListener(clickEvent -> {
            TextField textField = new TextField("Enter your email:");
            Button buttonEnter = new Button("OK");
            for (Place place : ticketSelect.getSelectedPlaces()) {
                if (ticketService.isAlreadyBookedTicket(seance.getId(), place.getId())) {
                    return;
                } else {
                    Ticket ticket = new Ticket();
                    textField.addTextChangeListener(textChangeEvent -> {
                        textField.setValue(textChangeEvent.getText());
                        textField.setNullSettingAllowed(true);
                        ticket.setEmail(textField.getValue());
                    });
                    ticket.setId(ticket.getId());
                    ticket.setPrice(144);
                    ticket.setCode(ticket.getCode());
                    ticket.setPlaceId(place.getId());
                    ticket.setSeanceId(seance.getId());
                    buttonEnter.addClickListener(clickEventt -> {
                        ticketService.save(ticket);
                    });
                }
            }
            layout.addComponent(textField);
            layout.addComponent(buttonEnter);
        });
        return layout;
    }
}