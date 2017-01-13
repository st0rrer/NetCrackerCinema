package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.web.CashierUI;
import com.vaadin.data.Property;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Iterator;

/**
 * Created by dimka on 01.12.2016.
 */
@SpringView(name = PaymentView.VIEW_NAME, ui = CashierUI.class)
@ViewScope
public class PaymentView extends VerticalLayout implements View {

    private static final Logger LOGGER = Logger.getLogger(PaymentView.class);
    public static final String VIEW_NAME = "";

    private HorizontalLayout areaForCode;
    private TextField ticketCode;
    private Button clearTicketCode;
    private Button inputCode;

    private VerticalLayout areaForTicketsTable;
    @Autowired
    private TicketsTable ticketsTable;

    private Long codeTicket;

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

        ticketsTable.setCodeTicket(codeTicket);
        ticketsTable.updateList();

        ticketsTable.setSizeFull();

        areaForTicketsTable.setSpacing(true);
        areaForTicketsTable.setMargin(true);
        areaForTicketsTable.setSizeFull();
        areaForTicketsTable.addComponents(ticketsTable, ticketsTable.getButtonsForTicketsTable());

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

        clearTicketCode = new Button(FontAwesome.TIMES);
        clearTicketCode.addClickListener(e -> {
            ticketCode.clear();
            if(hasChildComponent(areaForTicketsTable)) {
                areaForTicketsTable.removeAllComponents();
            }
            getUI().getNavigator().navigateTo(PaymentView.VIEW_NAME);
        });

        CssLayout areaInputTicketCode = new CssLayout();
        areaInputTicketCode.addComponents(ticketCode, clearTicketCode);
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

    private boolean hasChildComponent(ComponentContainer container) {
        Iterator<Component> iterator = container.iterator();
        if(iterator.hasNext()) return true;
        return false;
    }
}