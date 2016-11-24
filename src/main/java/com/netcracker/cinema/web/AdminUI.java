package com.netcracker.cinema.web;

import com.netcracker.cinema.web.admin.AdminMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

@SpringUI(path = "/admin")
@Theme("valo")
public class AdminUI extends UI {
    private static final Logger logger = Logger.getLogger(AdminUI.class);


    @Autowired
    private SpringViewProvider viewProvider;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        setContent(layout);

        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setWidth("100%");

        layout.addComponent(adminMenu);


        final Panel panel = new Panel();
        panel.setSizeFull();
        layout.addComponent(panel);
        layout.setExpandRatio(panel, 1.0f);



        Navigator adminNavigator = new Navigator(this, panel);
        adminNavigator.addProvider(viewProvider);


    }

    @WebServlet(urlPatterns = "/VAADIN/*", name = "AdminUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AdminUI.class, productionMode = false)
    public static class AdminUIServlet extends SpringVaadinServlet {
    }
}
