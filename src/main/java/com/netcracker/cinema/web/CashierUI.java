package com.netcracker.cinema.web;

import com.netcracker.cinema.web.cashier.CashierMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by dimka on 04.12.2016.
 */
@SpringUI(path = "/cashier")
@Theme("cinema")
public class CashierUI extends UI {
    private static final Logger logger = Logger.getLogger(AdminUI.class);

    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        CashierMenu cashierMenu = new CashierMenu();
        cashierMenu.setWidth("100%");

        layout.addComponent(cashierMenu);


        final Panel panel = new Panel();
        panel.setSizeFull();
        layout.addComponent(panel);
        layout.setExpandRatio(panel, 1.0f);


        Navigator adminNavigator = new Navigator(this, panel);
        adminNavigator.addProvider(viewProvider);


    }
}
