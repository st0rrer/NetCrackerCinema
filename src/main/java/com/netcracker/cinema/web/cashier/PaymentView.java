package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.web.CashierUI;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;

/**
 * Created by dimka on 01.12.2016.
 */
@SpringView(name = PaymentView.VIEW_NAME, ui = CashierUI.class)
public class PaymentView extends CustomComponent implements View {

    private static final Logger LOGGER = Logger.getLogger(PaymentView.class);
    public static final String VIEW_NAME = "";
    private TextField ticketCode;
    private long codeTicket;

    @PostConstruct
    void init() {
        initAreaForCode();
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

    private void initAreaForCode() {
        ticketCode = new TextField();
        ticketCode.setInputPrompt("Enter ticket code");
        ticketCode.setImmediate(true);
        ticketCode.setMaxLength(16);

        Button inputCode = new Button("Find ticket");
        inputCode.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        inputCode.addClickListener(event -> this.getCode());

        HorizontalLayout layout = new HorizontalLayout(ticketCode, inputCode);
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.setHeight("30px");
        layout.setSizeFull();
        layout.setExpandRatio(inputCode, 0.9f);
        layout.setComponentAlignment(ticketCode, Alignment.MIDDLE_CENTER);
        setCompositionRoot(layout);
    }

    private void getCode() {
        String code = ticketCode.getValue();
        try {
            codeTicket = Long.parseLong(code);
            if(code.length() < 16) {
                Notification.show("Ticket code should not have less than 16 characters", Notification.Type.WARNING_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            LOGGER.info("Expected id, but was " + code, e);
            Notification.show("Ticket code should not have the characters", Notification.Type.WARNING_MESSAGE);
        }
    }

}
