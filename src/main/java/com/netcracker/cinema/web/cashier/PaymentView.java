package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.model.Ticket;
import com.netcracker.cinema.web.CashierUI;
import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by dimka on 01.12.2016.
 */
@SpringView(name = PaymentView.VIEW_NAME, ui = CashierUI.class)
public class PaymentView extends HorizontalLayout implements View {

    private static final Logger LOGGER = Logger.getLogger(PaymentView.class);
    public static final String VIEW_NAME = "";

    private TextField ticketCode;

    private Long codeTicket;
    private List<Ticket> ticketList;

    @PostConstruct
    void init() {
        initAreaForCode();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void initAreaForCode() {
        ticketCode = new TextField();
        ticketCode.setInputPrompt("Enter tickets code");
        ticketCode.setImmediate(true);
        ticketCode.setMaxLength(16);


        ticketCode.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                String code = ticketCode.getValue();
                try {
                    if(code.length() < 16) {
                        Notification.show("Ticket code should not have less than 16 characters", Notification.Type.WARNING_MESSAGE);
                        return;
                    }

                } catch (NumberFormatException e) {
                    LOGGER.info("Expected id, but was " + code, e);
                    Notification.show("Ticket code should not have the characters", Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        Button inputCode = new Button("Find tickets");
        inputCode.setClickShortcut(ShortcutAction.KeyCode.ENTER);


//        layout.addComponent(ticketCode);
//        layout.addComponent(inputCode);
//        layout.setHeight("100px");
//        layout.setWidthUndefined();
//        layout.setExpandRatio(inputCode, 0.9f);
//        layout.setDefaultComponentAlignment(Alignment.BOTTOM_CENTER);
//        setHeight("100px");

        setHeight("100px");
        setDefaultComponentAlignment(Alignment.TOP_CENTER);
        addComponent(ticketCode);
        addComponent(inputCode);
    }

    private List<Ticket> getTicketsByCode(Long code) {
        /*TestData data = new TestData();
        List<Ticket> tickets = data.getTicketListByCode(code);

        return  data.getTicketListByCode(code);*/
        return null;
    }

}
