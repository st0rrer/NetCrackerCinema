package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.*;
import com.netcracker.cinema.service.*;
import com.netcracker.cinema.web.CashierUI;
import com.netcracker.cinema.web.common.TicketSelect;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.SimpleDateFormat;
import java.util.Collection;

@SpringView(name = HallDetailsViewCashier.VIEW_NAME, ui = CashierUI.class)
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
    private Button sale;
    private Window windowForSale;

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

        areaForBookTicket = new GridLayout(2, 2);
        areaForBookTicket.setSpacing(true);
        areaForBookTicket.addStyleName("book-ticket-area");
        this.addComponent(areaForBookTicket);

        ticketSelect.buildForThisSeance(seance);
        addSeanceInfo(seance);
        instanceButtons();
    }

    private void instanceButtons() {
        areaForTicketSelect = new VerticalLayout();
        areaForTicketSelect.setSpacing(true);
        areaForTicketSelect.addComponent(ticketSelect);
        HorizontalLayout areaForButtons = new HorizontalLayout();
        areaForButtons.setSpacing(true);
        sale = new Button("Sale");
        areaForButtons.addComponent(sale);
        areaForTicketSelect.addComponent(areaForButtons);
        areaForTicketSelect.setComponentAlignment(areaForButtons, Alignment.BOTTOM_CENTER);

        sale.addClickListener(clickEvent -> {
            if (!ticketSelect.getSelectedPlaces().isEmpty()) {
                this.getUI().addWindow(instanceWindowsForSale());
                ticketSelect.setEnabled(false);
                sale.setEnabled(false);
            }
        });
        areaForBookTicket.addComponent(areaForTicketSelect, 1, 0);
    }

    private Window instanceWindowsForSale() {
        windowForSale = new Window();
        windowForSale.setWidth("250px");
        windowForSale.setHeight("100px");
        windowForSale.center();
        windowForSale.setContent(confirmSale());
        windowForSale.addCloseListener(closing -> {
            ticketSelect.setEnabled(true);
            sale.setEnabled(true);
        });
        return windowForSale;
    }

    private Layout confirmSale() {
        VerticalLayout confirmSale = new VerticalLayout();
        confirmSale.setSpacing(true);
        confirmSale.setMargin(true);
        confirmSale.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
        Button buttonEnter = new Button("Confirm");
        buttonEnter.setEnabled(true);
        buttonEnter.addClickListener(clickEnter -> {
            long codeForTickets = ticketService.getCode();
            for (Place place : ticketSelect.getSelectedPlaces()) {
                Ticket ticket = new Ticket();
                ticket.setPrice(priceTicket(seance, place));
                ticket.setPlaceId(place.getId());
                ticket.setSeanceId(seance.getId());
                ticket.setCode(codeForTickets);
                ticket.setPaid(true);
                ticketService.save(ticket);
            }
            getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME + "/" + codeForTickets);
        });
        confirmSale.addComponent(buttonEnter);
        Label priceSum = new Label("Total price: " + String.valueOf(ticketSelect.getTotalPrice()) + "$");
        confirmSale.addComponent(priceSum);
        confirmSale.setComponentAlignment(priceSum, Alignment.BOTTOM_CENTER);
        return confirmSale;
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