package com.netcracker.cinema.web;

import com.netcracker.cinema.web.admin.AdminMenu;
import com.netcracker.cinema.web.cashier.CashierMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

/**
 * Created by dimka on 04.12.2016.
 */
@SpringUI(path = "/cashier")
@Theme("valo")
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

    @WebServlet(urlPatterns = "/VAADIN/cashier/*", name = "CashierUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = CashierUI.class, productionMode = false)
    public static class AdminUIServlet extends SpringVaadinServlet {
    }
}
