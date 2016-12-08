package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.web.CashierUI;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import oracle.net.aso.i;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dimka on 01.12.2016.
 */
@SpringView(name = PaymentView.VIEW_NAME, ui = CashierUI.class)
public class PaymentView extends VerticalLayout implements View {

    private static final Logger LOGGER = Logger.getLogger(PaymentView.class);
    public static final String VIEW_NAME = "";

    private TextField ticketCode;
    private HorizontalLayout areaForCode;
    private VerticalLayout areaForTicketsTable;

    private Long codeTicket;
    private BeanItemContainer<Ticket> tickets;

    @PostConstruct
    void init() {
        ticketCode = new TextField();
        areaForCode = new HorizontalLayout();
        areaForTicketsTable = new VerticalLayout();
        initAreaForCode();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        try {
            if(!"".equals(event.getParameters())) {
                codeTicket = Long.parseLong(event.getParameters());
                tickets = getTicketsByCode(codeTicket);
            } else {
                return;
            }
        } catch (NumberFormatException e) {
            if(!"".equals(event.getParameters())) {
                getUI().getPage().reload();
                getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);
                return;
            }
        }

        if(tickets.size() == 0) {
            return;
        }

        initAreaForTicketsTable();
    }

    private void initAreaForTicketsTable() {
        Grid ticketsTable = new Grid();
        ticketsTable.setContainerDataSource(tickets);
        ticketsTable.setSizeFull();
        ticketsTable.setColumnOrder("code", "seanceId", "placeId", "price", "email", "paid","id");
        ticketsTable.getColumn("id").setHidden(true);
        ticketsTable.getColumn("seanceId").setHeaderCaption("Seance");
        ticketsTable.getColumn("placeId").setHeaderCaption("Place");

        ticketsTable.setSelectionMode(Grid.SelectionMode.MULTI);

        areaForTicketsTable.setSpacing(true);
        areaForTicketsTable.setMargin(true);
        areaForTicketsTable.setSizeFull();
        areaForTicketsTable.addComponent(ticketsTable);
        areaForTicketsTable.setComponentAlignment(ticketsTable, Alignment.TOP_CENTER);
        addComponent(areaForTicketsTable);
    }

    private void initAreaForCode() {

        ticketCode.setInputPrompt("Enter tickets code");
        ticketCode.setImmediate(true);
        ticketCode.setMaxLength(16);

        ticketCode.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String code = ticketCode.getValue();
                try {
                    if(code.length() <= 3 || code.length() >= 5) {
                        Notification.show("Ticket code should not have less than 4 characters", Notification.Type.WARNING_MESSAGE);
                        return;
                    }
                    if(hasChildComponent(areaForTicketsTable)) {
                        areaForTicketsTable.removeAllComponents();
                    }
                    Long codeTicket = Long.parseLong(code);
                    getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME + "/" + code);
                } catch (NumberFormatException e) {
                    LOGGER.info("Expected id, but was " + code, e);
                    Notification.show("Ticket code should not have the characters", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        Button inputCode = new Button("Find tickets");
        inputCode.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        areaForCode.setMargin(true);
        areaForCode.setSpacing(true);
        areaForCode.setHeight("100px");
        areaForCode.setSizeFull();
        areaForCode.addComponent(ticketCode);
        areaForCode.addComponent(inputCode);
        areaForCode.setExpandRatio(inputCode, 1.0f);
        areaForCode.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        addComponent(areaForCode);
    }

    private boolean hasChildComponent(ComponentContainer container) {
        Iterator<Component> iterator = container.iterator();
        if(iterator.hasNext()) return true;
        return false;
    }

    private BeanItemContainer getTicketsByCode(Long code) {
        TestData data = new TestData();
        BeanItemContainer tickets = new BeanItemContainer(Ticket.class, data.getTicketListByCode(code));
        return  tickets;
    }

}
