package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.*;
import com.netcracker.cinema.service.*;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * Created by dimka on 10.12.2016.
 */
@SpringComponent
@UIScope
@Getter
@Setter
public class TicketsTable extends Grid {

    @Autowired
    private SeanceService seanceService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private HallService hallService;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private ZoneService zoneService;

    private Button sellTickets;
    private Button printTickets;
    private HorizontalLayout buttonsForTicketsTable;

    private Grid.MultiSelectionModel ticketsSelection;

    private Long codeTicket;
    private List<Ticket> tickets;

    public TicketsTable() {
        super();
        this.setSelectionMode(SelectionMode.MULTI);
        ticketsSelection  = (Grid.MultiSelectionModel) this.getSelectionModel();

        buttonsForTicketsTable = new HorizontalLayout();
        sellTickets = new Button("Sell tickets");
        printTickets = new Button("Print tickets");

        printTickets.addClickListener(event -> {
            clickPrintTickets();
        });

        sellTickets.addClickListener(event -> {
            clickSellTickets();
        });

        buttonsForTicketsTable.addComponents(sellTickets, printTickets);
        buttonsForTicketsTable.setSpacing(true);
    }

    private void clickSellTickets() {
        if(ticketsSelection.getSelectedRows().size() != 0) {
            getUI().getCurrent().addWindow(new TicketsTable.SellWindow(new ArrayList(ticketsSelection.getSelectedRows()), "Confirmation"));
        }
    }

    private void clickPrintTickets() {
        StringBuffer content = new StringBuffer("");

        for(Ticket ticket : (Collection<Ticket>) this.getContainerDataSource().getItemIds()) {
            Seance seance = seanceService.getById(ticket.getSeanceId());
            Movie movie = movieService.getById(seance.getMovieId());
            Hall hall = hallService.getById(seance.getHallId());
            Place place = placeService.getById(ticket.getPlaceId());
            Zone zone = zoneService.getById(place.getZoneId());

            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd MMM");

            content.append("<p> Code: " + ticket.getCode()  + "</p>");
            content.append("<p> Seance: " + movie.getName() + " Start Time: " + dateFormat.format(seance.getSeanceDate())
                    + " Hall: " + hall.getName() + "</p>");
            content.append("<p> Place. Number: " + place.getNumber() + " Row number: " + place.getRowNumber()
                    + " Zone: " + zone.getName() + "</p>");
            content.append("<p> Price: " + ticket.getPrice()  + "</p>");
            content.append("<br>");
        }

        content.append(
                "<SCRIPT language=\"JavaScript\">" +
                        "  print();" +
                        "  close();" +
                        "</SCRIPT>");

        String js = "popup = window.open('', 'printWindow','status=1', 'fullscreen = 1');\n" +
                "popup.document.write('" + content.toString() + "');\n";
        JavaScript.getCurrent().execute(js);
    }

    public void updateList() {
        if(codeTicket != null) {

            tickets = ticketService.getTicketsByCode(codeTicket);
            if(tickets.isEmpty()) {
                Notification.show("Tickets don't exist by this code!", Notification.Type.WARNING_MESSAGE);
                this.setVisible(false);
                sellTickets.setVisible(false);
                printTickets.setVisible(false);
            } else {
                this.setVisible(true);
                sellTickets.setVisible(true);
                printTickets.setVisible(true);
            }
            BeanItemContainer<Ticket> ticketsContainer = new BeanItemContainer<>(Ticket.class, tickets);

            GeneratedPropertyContainer ticketsGeneratedContainer = new GeneratedPropertyContainer(ticketsContainer);
            ticketsGeneratedContainer.addGeneratedProperty("Seance", new PropertyValueGenerator<String>() {
                @Override
                public String getValue(Item item, Object itemId, Object propertyId) {
                    long seanceId = (Long) item.getItemProperty("seanceId").getValue();
                    Seance seance = seanceService.getById(seanceId);
                    Movie movie = movieService.getById(seance.getMovieId());
                    Hall hall = hallService.getById(seance.getHallId());
                    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd MMM");
                    return movie.getName() + " " + dateFormat.format(seance.getSeanceDate()) + " Hall: " + hall.getName();
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });
            ticketsGeneratedContainer.addGeneratedProperty("Place", new PropertyValueGenerator<String>() {
                @Override
                public String getValue(Item item, Object itemId, Object propertyId) {
                    long placeId = (Long) item.getItemProperty("placeId").getValue();
                    Place place = placeService.getById(placeId);
                    Zone zone = zoneService.getById(place.getZoneId());
                    return "Number: " + place.getNumber() + " Row: " + place.getRowNumber() + " Zone: " + zone.getName();
                }

                @Override
                public Class<String> getType() {
                    return String.class;
                }
            });

            for(Object ticket : this.getContainerDataSource().getItemIds()) {
                if(((Ticket) ticket).isPaid() == true) {
                    printTickets.setEnabled(true);
                    break;
                } else {
                    printTickets.setEnabled(false);
                }
            }

            this.setContainerDataSource(ticketsGeneratedContainer);
            this.setColumnOrder("code", "Seance", "Place", "price", "email", "paid", "id");
            this.getColumn("id").setHidden(true);
            this.getColumn("seanceId").setHidden(true);
            this.getColumn("placeId").setHidden(true);
        }
    }

    private class SellWindow extends Window {
        SellWindow(List ticketsForSell, String caption) {
            super(caption);
            VerticalLayout confirmation = new VerticalLayout();
            confirmation.setMargin(true);
            confirmation.setSpacing(true);
            Label confirmationWarring = new Label("The remaining tickets will be reset");
            Button checkConfirmation = new Button("OK");
            confirmation.addComponents(confirmationWarring, checkConfirmation);
            confirmation.setComponentAlignment(checkConfirmation, Alignment.TOP_CENTER);
            this.center();
            this.setContent(confirmation);
            checkConfirmation.addClickListener(event -> {
                List<Ticket> ticketsByCode = ticketService.getTicketsByCode(codeTicket);
                for(int i = 0; i < ticketsByCode.size(); i++) {
                    for(int j = 0; j < ticketsForSell.size(); j++) {
                        if(ticketsByCode.get(i).getId() == ((Ticket) ticketsForSell.get(j)).getId()) {
                            ticketsByCode.get(i).setPaid(true);
                        }
                    }
                }
                for(Ticket ticket : ticketsByCode) {
                    if(ticket.isPaid() == true) {
                        ticketService.save(ticket);
                    } else {
                        ticketService.deleteById(ticket.getId());
                    }
                }

                updateList();
                printTickets.setEnabled(true);
                getUI().removeWindow(this);
            });
        }
    }
}
