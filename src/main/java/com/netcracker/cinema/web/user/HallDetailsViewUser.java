package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.validation.Validation;
import com.netcracker.cinema.web.UserUI;
import com.netcracker.cinema.web.common.TicketSelect;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@SpringView(name = HallDetailsViewUser.VIEW_NAME, ui = UserUI.class)
public class HallDetailsViewUser extends HorizontalLayout implements View {

    public static final String VIEW_NAME = "details";

    private static final Logger LOGGER = Logger.getLogger(HallDetailsViewUser.class);

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
            LOGGER.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleViewUser.VIEW_NAME);
            return;
        }
        Seance seance = null;
        Hall hall = null;
        try {
            seance = seanceService.getById(seanceId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Can't find seance with this id " + seanceId, e);
            addComponent(new Label("Can't find seance with this id " + seanceId));
            return;
        }
        try {
            hallId = seance.getHallId();
        } catch (NumberFormatException e) {
            LOGGER.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleViewUser.VIEW_NAME);
            return;
        }
        try {
            hall = hallService.getById(hallId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.info("Can't find hall with this id " + hallId, e);
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
        poster.setHeight("270px");
        poster.setWidth("190px");
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
        Label priceZoneBlue = new Label("Price the blue zone: " + priceZone(seance, 1, 1) + "$");
        layout.addComponent(priceZoneBlue);
        Label priceZonePink = new Label("Price the pink zone: " + priceZone(seance, 5, 1) + "$");
        layout.addComponent(priceZonePink);
        Label priceZoneRed = new Label("Price the red zone: " + priceZone(seance, 9, 1) + "$");
        layout.addComponent(priceZoneRed);
        return layout;
    }

    private int priceTicket(Seance seance, Place place) {
        int basePrice = movieService.getById(seance.getMovieId()).getBasePrice();
        int placePrice = priceService.getPriceBySeanceColRow((int) seance.getId(), place.getNumber(), place.getRowNumber());
        return basePrice + placePrice;
    }

    private int priceZone(Seance seance, int row, int placeNumber) {
        int basePrice = movieService.getById(seance.getMovieId()).getBasePrice();
        int placePrice = priceService.getPriceBySeanceColRow((int) seance.getId(), placeNumber, row);
        return basePrice + placePrice;
    }

    private Component addButtonBook(Seance seance) {
        ArrayList<Integer> sumPrice = new ArrayList<Integer>();
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(ticketSelect);
        Button book = new Button("Book");
        TextField textField = new TextField("Enter your email:");
        Button buttonEnter = new Button("OK");
        layout.addComponent(book);
        book.addClickListener(clickEvent -> {
            if (ticketSelect.getSelectedPlaces().isEmpty())
                book.setEnabled(true);
            if (!ticketSelect.getSelectedPlaces().isEmpty()) {
                book.setEnabled(false);
                Validation validation = new Validation();
                ticketSelect.setEnabled(false);
                buttonEnter.setEnabled(false);
                book.setEnabled(false);
                for (Place place : ticketSelect.getSelectedPlaces()) {
                    Ticket ticket = new Ticket();
                    textField.addTextChangeListener(textChangeEvent -> {
                        textField.setValue(textChangeEvent.getText());
                        textField.setNullSettingAllowed(true);
                        if (validation.isValidEmailAddress(textField.getValue())) {
                            ticket.setEmail(textField.getValue());
                            buttonEnter.setEnabled(true);
                        }
                        if (!validation.isValidEmailAddress(textField.getValue())) {
                            Notification.show("Incorrect email address", Notification.Type.WARNING_MESSAGE);
                            buttonEnter.setEnabled(false);
                        }
                    });
                    ticket.setPrice(priceTicket(seance, place));
                    ticket.setCode(ticketService.getCode());
                    ticket.setPlaceId(place.getId());
                    ticket.setSeanceId(seance.getId());
                    buttonEnter.addClickListener(clickEventt -> {
                        ticketService.save(ticket);
                        messageBox();
                        buttonEnter.setEnabled(false);
                    });
                    sumPrice.add(priceTicket(seance, place));
                }
                layout.addComponent(textField);
                layout.addComponent(buttonEnter);
                ticketPrice(sumPrice, layout);
            }
        });
        return layout;
    }

    private void messageBox() {
        MessageBox
                .createInfo()
                .withCaption("Well done!")
                .withMessage("Promo code sent to your email. Thank you :)\n" +
                        "(Don't forget to buy the ticket an hour before the seance)\n")
                .withOkButton(() -> {
                    Page.getCurrent().reload();
                })
                .open();
    }

    private void ticketPrice(ArrayList<Integer> sumPrice, Layout layout) {
        int sumPriceTicket = 0;
        for (int i = 0; i < sumPrice.size(); i++) {
            sumPriceTicket = sumPriceTicket + sumPrice.get(i);
        }
        Label priceSum = new Label("Total price: " + String.valueOf(sumPriceTicket) + "$");
        layout.addComponent(priceSum);
    }
}