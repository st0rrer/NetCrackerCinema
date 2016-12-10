package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.web.CashierUI;
import com.sun.corba.se.spi.ior.ObjectKey;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dimka on 01.12.2016.
 */
@SpringView(name = PaymentView.VIEW_NAME, ui = CashierUI.class)
public class PaymentView extends VerticalLayout implements View {

    private static final Logger LOGGER = Logger.getLogger(PaymentView.class);
    public static final String VIEW_NAME = "";

    private HorizontalLayout areaForCode;
    private TextField ticketCode;
    private Button cleatTicketCode;
    private Button inputCode;

    private VerticalLayout areaForTicketsTable;
    private Grid ticketsTable;
    private Button printTickets;
    private Button sellTickets;

    private Long codeTicket;

    final TestData data = new TestData();;

    @PostConstruct
    void init() {
        areaForCode = new HorizontalLayout();
        areaForTicketsTable = new VerticalLayout();
        initAreaForCode();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if("".equals(event.getParameters())) return;
        try {
            codeTicket = Long.parseLong(event.getParameters());
        } catch (NumberFormatException e) {
            if(!"".equals(event.getParameters())) {
                getUI().getPage().reload();
                getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);
            }
        }

        if(codeTicket != null) {
            initAreaForTicketsTable();
        }
    }

    private void initAreaForTicketsTable() {
        ticketsTable = new Grid();

        updateList();

        ticketsTable.setSizeFull();
        ticketsTable.setColumnOrder("code", "seanceId", "placeId", "price", "email", "paid", "id");
        ticketsTable.getColumn("id").setHidden(true);
        ticketsTable.getColumn("seanceId").setHeaderCaption("Seance");
        ticketsTable.getColumn("placeId").setHeaderCaption("Place");

        ticketsTable.setSelectionMode(Grid.SelectionMode.MULTI);

        HorizontalLayout buttonsForTicketsTable = new HorizontalLayout();
        sellTickets = new Button("Sell tickets");
        printTickets = new Button("Print tickets");

        printTickets.setEnabled(false);

        for(Object ticket : ticketsTable.getContainerDataSource().getItemIds()) {
            if(((Ticket) ticket).isPaid() == true) {
                printTickets.setEnabled(true);
                break;
            }
        }

        printTickets.addClickListener(event -> {
            clickPrintTickets();
        });

        Grid.MultiSelectionModel ticketsSelection  = (Grid.MultiSelectionModel) ticketsTable.getSelectionModel();

        sellTickets.addClickListener(event -> {
            if(ticketsSelection.getSelectedRows().size() != 0) {
                getUI().getCurrent().addWindow(new SellWindow(new ArrayList(ticketsSelection.getSelectedRows()), "Confirmation"));
            }
        });

        buttonsForTicketsTable.addComponents(sellTickets, printTickets);
        buttonsForTicketsTable.setSpacing(true);

        areaForTicketsTable.setSpacing(true);
        areaForTicketsTable.setMargin(true);
        areaForTicketsTable.setSizeFull();
        areaForTicketsTable.addComponents(ticketsTable, buttonsForTicketsTable);
        areaForTicketsTable.setComponentAlignment(ticketsTable, Alignment.BOTTOM_CENTER);

        addComponent(areaForTicketsTable);
    }

    private void initAreaForCode() {
        ticketCode = new TextField();

        ticketCode.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if(ticketCode.getValue().length() == 0) return;
                String code = ticketCode.getValue();
                try {
                    if(code.length() <= 3 || code.length() >= 5) {
                        Notification.show("Ticket code should not have less than 4 characters", Notification.Type.WARNING_MESSAGE);
                        return;
                    }
                    if(hasChildComponent(areaForTicketsTable)) {
                        areaForTicketsTable.removeAllComponents();
                    }
                    Long.parseLong(code);
                    getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME + "/" + code);
                } catch (NumberFormatException e) {
                    LOGGER.info("Expected code, but was " + code, e);
                    Notification.show("Ticket code should not have the characters", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        ticketCode.setInputPrompt("Enter tickets code");
        ticketCode.setImmediate(true);
        ticketCode.setMaxLength(16);

        cleatTicketCode = new Button(FontAwesome.TIMES);
        cleatTicketCode.addClickListener(e -> {
            ticketCode.clear();
            if(hasChildComponent(areaForTicketsTable)) {
                areaForTicketsTable.removeAllComponents();
            }
            getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);
        });

        CssLayout areaInputTicketCode = new CssLayout();
        areaInputTicketCode.addComponents(ticketCode, cleatTicketCode);
        areaInputTicketCode.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        inputCode = new Button("Find tickets");
        inputCode.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        areaForCode.setMargin(true);
        areaForCode.setSpacing(true);
        areaForCode.setHeight("100px");
        areaForCode.setSizeFull();
        areaForCode.addComponent(areaInputTicketCode);
        areaForCode.addComponent(inputCode);
        areaForCode.setExpandRatio(inputCode, 1.0f);
        areaForCode.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        addComponent(areaForCode);
    }

    private void clickPrintTickets() {
        StringBuffer content = new StringBuffer("");

        for(Ticket ticket : (Collection<Ticket>) ticketsTable.getContainerDataSource().getItemIds()) {
            content.append("<p> " + ticket.getId() + "</p>");
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

    private boolean hasChildComponent(ComponentContainer container) {
        Iterator<Component> iterator = container.iterator();
        if(iterator.hasNext()) return true;
        return false;
    }

    public void updateList() {
        if(codeTicket != null) {
            if(!data.getTicketListByCode(codeTicket).isEmpty()) {
                ticketsTable.setContainerDataSource(new BeanItemContainer<Ticket>(Ticket.class, data.getTicketListByCode(codeTicket)));
            }
        }
    }

    private class SellWindow extends Window {
        SellWindow(ArrayList ticketsForSell, String caption) {
            super(caption);
            VerticalLayout confirmation = new VerticalLayout();
            confirmation.setMargin(true);
            confirmation.setSpacing(true);
            Label confirmationWarring = new Label("The remaining tickets will be reset");
            Button checkConfirmation = new Button("OK");
            confirmation.addComponents(confirmationWarring, checkConfirmation);
            confirmation.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
            this.center();
            this.setContent(confirmation);
            checkConfirmation.addClickListener(event -> {
                List<Ticket> list = data.getTicketListByCode(codeTicket);
                for(int i = 0; i < list.size(); i++) {
                    for(int j = 0; j < ticketsForSell.size(); j++) {
                        if(list.get(i).getId() == ((Ticket) ticketsForSell.get(j)).getId()) {
                            list.get(i).setPaid(true);
                        }
                    }
                }
                for(Ticket ticket : list) {
                    data.save(ticket);
                }
                updateList();
                printTickets.setEnabled(true);
                getUI().removeWindow(this);
            });
        }
    }

}
