package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Place;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.validation.Validation;
import com.netcracker.cinema.web.CashierUI;
import com.netcracker.cinema.web.common.TicketSelect;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SpringView(name = HallDetailsViewCashier.VIEW_NAME, ui = CashierUI.class)
@ViewScope
public class HallDetailsViewCashier extends VerticalLayout implements View {

    public static final String VIEW_NAME = "details";
    private static final Logger LOGGER = Logger.getLogger(HallDetailsViewCashier.class);

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
    private VerticalLayout areaForTicketSelect;
    private Button book;
    private Button sale;

    private Seance seance;

    @PostConstruct
    public void init() {
        this.setMargin(true);
        this.setSpacing(true);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        long seanceId;
        try {
            seanceId = Long.parseLong(event.getParameters());
        } catch (NumberFormatException e) {
            LOGGER.error("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleViewCashier.VIEW_NAME);
            return;
        }
        try {
            seance = seanceService.getById(seanceId);
        } catch (EmptyResultDataAccessException e) {
            LOGGER.error("Can't find seance with this id " + seanceId, e);
            getUI().getNavigator().navigateTo(ScheduleViewCashier.VIEW_NAME);
            Notification.show("Can't find seance with this id " + seanceId, Notification.Type.ERROR_MESSAGE);
            return;
        }

        this.removeAllComponents();
        areaForBookTicket = new GridLayout(2, 2);
        areaForBookTicket.setSpacing(true);
        areaForBookTicket.addStyleName("book-ticket-area");
        this.addComponent(areaForBookTicket);

        ticketSelect.buildForThisSeance(seance);
        addSeanceInfo(seance);
        instanceButtons(seance);
    }

    private void instanceButtons(Seance seance) {
        areaForTicketSelect = new VerticalLayout();
        areaForTicketSelect.setSpacing(true);
        areaForTicketSelect.addComponent(ticketSelect);
        HorizontalLayout areaForButtons = new HorizontalLayout();
        areaForButtons.setSpacing(true);
        book = new Button("Book");
        sale = new Button("Sale");
        areaForButtons.addComponent(book);
        areaForButtons.addComponent(sale);
        areaForTicketSelect.addComponent(areaForButtons);
        areaForTicketSelect.setComponentAlignment(areaForButtons, Alignment.BOTTOM_CENTER);
        book.addClickListener(clickEvent -> {
            if (!ticketSelect.getSelectedPlaces().isEmpty()) {
                this.getUI().addWindow(instanceWindowForBook());
                ticketSelect.setEnabled(false);
                book.setEnabled(false);
                sale.setEnabled(false);
            }
        });
        sale.addClickListener(clickEvent -> {
            if (!ticketSelect.getSelectedPlaces().isEmpty()) {
                this.getUI().addWindow(instanceWindowsForSale());
                ticketSelect.setEnabled(false);
                book.setEnabled(false);
                sale.setEnabled(false);
            }
        });
        areaForBookTicket.addComponent(areaForTicketSelect, 1, 0);
    }

    private Window instanceWindowForBook() {

        Window windowForBook = new Window();
        windowForBook.center();
        windowForBook.setContent(confirmBooking());
        windowForBook.addCloseListener(closing -> {
            ticketSelect.setEnabled(true);
            book.setEnabled(true);
            sale.setEnabled(true);
        });
        return windowForBook;
    }

    private Window instanceWindowsForSale() {
        Window windowForSale = new Window();
        windowForSale.center();
        windowForSale.setContent(confirmSaling());
        windowForSale.addCloseListener(closing -> {
            ticketSelect.setEnabled(true);
            book.setEnabled(true);
            sale.setEnabled(true);
        });
        return windowForSale;
    }

    private Layout confirmBooking() {
        VerticalLayout confirmBooking = new VerticalLayout();
        confirmBooking.setSpacing(true);
        confirmBooking.setMargin(true);
        confirmBooking.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        TextField textField = new TextField("Enter your email:");
        Button buttonEnter = new Button("Confirm");
        ticketSelect.setEnabled(false);
        buttonEnter.setEnabled(false);
        List<Ticket> bookTickets = new ArrayList();
        for (Place place : ticketSelect.getSelectedPlaces()) {
            Ticket ticket = new Ticket();
            textField.addTextChangeListener(textChangeEvent -> {
                textField.setValue(textChangeEvent.getText());
                textField.setNullSettingAllowed(true);
                if (!Validation.isValidEmailAddress(textField.getValue())) {
                    Notification.show("Incorrect email address", Notification.Type.WARNING_MESSAGE);
                    buttonEnter.setEnabled(false);
                } else {
                    ticket.setEmail(textField.getValue());
                    buttonEnter.setEnabled(true);
                }
            });
            ticket.setPrice(priceTicket(seance, place));
            ticket.setPlaceId(place.getId());
            ticket.setSeanceId(seance.getId());
            bookTickets.add(ticket);
        }
        buttonEnter.addClickListener(clickEnter -> {
            long codeForTickets = ticketService.getCode();
            for (Ticket ticket : bookTickets) {
                ticket.setCode(codeForTickets);
                ticketService.save(ticket);
            }
            messageBox();
        });
        confirmBooking.addComponent(textField);
        confirmBooking.addComponent(buttonEnter);
        Label priceSum = new Label("Total price: " + String.valueOf(ticketSelect.getTotalPrice()) + "$");
        confirmBooking.addComponent(priceSum);
        return confirmBooking;
    }

    private Layout confirmSaling() {
        VerticalLayout confirmSaling = new VerticalLayout();
        confirmSaling.setSpacing(true);
        confirmSaling.setMargin(true);
        confirmSaling.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        TextField emailField = new TextField("You can enter the email:");
        Button buttonEnter = new Button("Confirm");
        buttonEnter.setEnabled(true);
        List<Ticket> bookTickets = new ArrayList();
        for (Place place : ticketSelect.getSelectedPlaces()) {
            Ticket ticket = new Ticket();
            ticket.setPrice(priceTicket(seance, place));
            ticket.setPlaceId(place.getId());
            ticket.setSeanceId(seance.getId());
            bookTickets.add(ticket);
        }
        buttonEnter.addClickListener(clickEnter -> {
            String email = emailField.getValue();
            long codeForTickets = ticketService.getCode();
            for (Ticket ticket : bookTickets) {
                if (Validation.isValidEmailAddress(email)) {
                    ticket.setEmail(email);
                }
                ticket.setCode(codeForTickets);
                ticketService.save(ticket);
            }
            messageBox(codeForTickets);
        });
        confirmSaling.addComponent(emailField);
        confirmSaling.addComponent(buttonEnter);
        Label priceSum = new Label("Total price: " + String.valueOf(ticketSelect.getTotalPrice()) + "$");
        confirmSaling.addComponent(priceSum);
        return confirmSaling;
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

    private void messageBox(long codeForTickets) {
        MessageBox
                .createInfo()
                .withCaption("Well done!")
                .withMessage("Your code is : " + codeForTickets + ")\n" +
                        "(Don't forget to buy the ticket an hour before the seance)\n")
                .withOkButton(() -> {
                    Page.getCurrent().reload();
                })
                .open();
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
        Label priceZoneBlue = new Label("Price the blue zone: " + priceZone(seance, 1, 1) + "$");
        areaForSeanceInfo.addComponent(priceZoneBlue);
        Label priceZonePink = new Label("Price the pink zone: " + priceZone(seance, 5, 1) + "$");
        areaForSeanceInfo.addComponent(priceZonePink);
        Label priceZoneRed = new Label("Price the red zone: " + priceZone(seance, 9, 1) + "$");
        areaForSeanceInfo.addComponent(priceZoneRed);
    }
}