package com.netcracker.cinema.web.user;

import com.netcracker.cinema.exception.CinemaEmptyResultException;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.validation.Validation;
import com.netcracker.cinema.web.UserUI;
import com.netcracker.cinema.web.common.TicketSelect;
import com.vaadin.data.Validator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Collection;

@SpringView(name = HallDetailsViewUser.VIEW_NAME, ui = UserUI.class)
public class HallDetailsViewUser extends VerticalLayout implements View {

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

    private GridLayout areaForBookTicket;
    private Button book;
    private Window windowForBook;

    private Seance seance;

    @PostConstruct
    public void init() {
        LOGGER.info("Create bean: " + this.getClass().getSimpleName());
        this.setMargin(true);
        this.setSpacing(true);
    }

    @PreDestroy
    public void preDestroy() {
        LOGGER.info("Destroy bean: " + this.getClass().getSimpleName());
        Collection<Window> windows = UI.getCurrent().getWindows();
        LOGGER.debug("Get all windows: " + !windows.isEmpty());
        windows.forEach(Window::close);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        long seanceId = 0;
        try {
            seanceId = Long.parseLong(event.getParameters());
        } catch (NumberFormatException e) {
            LOGGER.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleViewUser.VIEW_NAME);
        }
        try {
            seance = seanceService.getById(seanceId);
        } catch (CinemaEmptyResultException e) {
            LOGGER.info("Can't find seance with this id " + seanceId, e);
            getUI().getNavigator().navigateTo(ScheduleViewUser.VIEW_NAME);
            Notification.show("Can't find seance with this id " + seanceId, Notification.Type.ERROR_MESSAGE);
        }

        this.removeAllComponents();
        areaForBookTicket = new GridLayout(2, 2);
        areaForBookTicket.setSpacing(true);
        this.addComponent(areaForBookTicket);

        ticketSelect.buildForThisSeance(seance);
        addSeanceInfo(seance);
        instanceButtons();
    }

    private void instanceButtons() {
        VerticalLayout areaForTicketSelect = new VerticalLayout();
        areaForTicketSelect.setSpacing(true);
        areaForTicketSelect.addComponent(ticketSelect);
        HorizontalLayout areaForButtons = new HorizontalLayout();
        areaForButtons.setSpacing(true);
        book = new Button("Book");
        areaForButtons.addComponent(book);
        areaForTicketSelect.addComponent(areaForButtons);
        areaForTicketSelect.setComponentAlignment(areaForButtons, Alignment.BOTTOM_CENTER);
        book.addClickListener(clickEvent -> {
            if (!ticketSelect.getSelectedPlaces().isEmpty()) {
                this.getUI().addWindow(instanceWindowForBook());
                ticketSelect.setEnabled(false);
                book.setEnabled(false);
            }
        });
        areaForBookTicket.addComponent(areaForTicketSelect, 1, 0);
    }

    private Window instanceWindowForBook() {
        windowForBook = new Window();
        windowForBook.center();
        windowForBook.setContent(confirmBooking());
        windowForBook.addCloseListener(closing -> {
            ticketSelect.setEnabled(true);
            book.setEnabled(true);
        });
        return windowForBook;
    }

    private Layout confirmBooking() {
        VerticalLayout confirmBooking = new VerticalLayout();
        confirmBooking.setMargin(true);
        confirmBooking.setSpacing(true);
        confirmBooking.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);

        Button buttonEnter = new Button("Confirm");
        buttonEnter.setEnabled(false);

        TextField emailField = new TextField("Enter your email:");
        emailField.setValidationVisible(false);
        emailField.addValidator(new ValidationEmailTextField());

        ticketSelect.setEnabled(false);
        emailField.addTextChangeListener(textChangeEvent -> {
            emailField.setValue(textChangeEvent.getText());
            emailField.setNullSettingAllowed(true);
            if (!emailField.getValue().isEmpty()) {
                buttonEnter.setEnabled(true);
            }
        });
        buttonEnter.addClickListener(clickEnter -> {
            if (!emailField.isValid()) {
                Notification.show("Incorrect email address", Notification.Type.WARNING_MESSAGE);
                buttonEnter.setEnabled(true);
            } else {
                buttonEnter.setEnabled(false);
                long codeForTickets = ticketService.getCode();
                for (Place place : ticketSelect.getSelectedPlaces()) {
                    Ticket ticket = new Ticket();
                    ticket.setEmail(emailField.getValue());
                    ticket.setPrice(priceTicket(seance, place));
                    ticket.setPlaceId(place.getId());
                    ticket.setSeanceId(seance.getId());
                    ticket.setCode(codeForTickets);
                    ticketService.save(ticket);
                }
                getUI().removeWindow(windowForBook);
                ticketSelect.buildForThisSeance(seance);
                Notification successfulNotification = new Notification("Promo code sent to your email. Thank you!" +
                        "\n" + "Don't forget to buy the ticket an hour before the seance");
                successfulNotification.setDelayMsec(7 * 1_000);
                successfulNotification.show(Page.getCurrent());
            }
        });
        confirmBooking.addComponent(emailField);
        confirmBooking.addComponent(buttonEnter);
        Label priceSum = new Label("Total price: " + String.valueOf(ticketSelect.getTotalPrice()));
        confirmBooking.addComponent(priceSum);
        return confirmBooking;
    }

    private int priceTicket(Seance seance, Place place) {
        int basePrice = movieService.getById(seance.getMovieId()).getBasePrice();
        int placePrice = priceService.getPriceBySeanceColRow(seance.getId(), place.getNumber(), place.getRowNumber());
        return basePrice + placePrice;
    }

    private int priceZone(Seance seance, int row, int placeNumber) {
        int basePrice = movieService.getById(seance.getMovieId()).getBasePrice();
        int placePrice = priceService.getPriceBySeanceColRow(seance.getId(), placeNumber, row);
        return basePrice + placePrice;
    }

    private void addSeanceInfo(Seance seance) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        VerticalLayout areaForSeanceInfo = new VerticalLayout();
        areaForBookTicket.addComponent(areaForSeanceInfo, 0, 0);

        ExternalResource resource = new ExternalResource(movieService.getById(seance.getMovieId()).getPoster());
        Image poster = new Image(null, resource);
        poster.setHeight("270px");
        poster.setWidth("190px");
        areaForSeanceInfo.addComponent(poster);
        Label name = new Label(movieService.getById(seance.getMovieId()).getName());
        areaForSeanceInfo.addComponent(name);
        Label date = new Label("Date: " + dateFormat.format(seance.getSeanceDate()));
        areaForSeanceInfo.addComponent(date);
        Label time = new Label("Time: " + timeFormat.format(seance.getSeanceDate()));
        areaForSeanceInfo.addComponent(time);
        Label hall = new Label("Hall: " + hallService.getById(seance.getHallId()).getName());
        areaForSeanceInfo.addComponent(hall);
        Label priceZoneBlue = new Label("Price the blue zone: " + priceZone(seance, 1, 1));
        areaForSeanceInfo.addComponent(priceZoneBlue);
        Label priceZonePink = new Label("Price the pink zone: " + priceZone(seance, 5, 1));
        areaForSeanceInfo.addComponent(priceZonePink);
        Label priceZoneRed = new Label("Price the red zone: " + priceZone(seance, 9, 1));
        areaForSeanceInfo.addComponent(priceZoneRed);
    }

    private class ValidationEmailTextField implements Validator {
        @Override
        public void validate(Object value) throws InvalidValueException {
            LOGGER.debug("Validation data: " + value + " result: " +
                    (!Validation.isValidEmailAddress((String) value)));
            if (!Validation.isValidEmailAddress((String) value)) {
                throw new InvalidValueException("Incorrect email address");
            }
        }
    }
}