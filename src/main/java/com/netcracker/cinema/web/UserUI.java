package com.netcracker.cinema.web;

import com.netcracker.cinema.web.user.UserMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.annotation.WebServlet;

@Theme("valo")
@SpringUI
public class UserUI extends UI {
    private static final Logger logger = Logger.getLogger(UserUI.class);

    @Autowired
    private SpringViewProvider viewProvider;
    
    @Override
    protected void init(VaadinRequest request) {

        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        UserMenu userMenu = new UserMenu();
        userMenu.setWidth("100%");

        root.addComponent(userMenu);

        final Panel springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);

        Navigator navigator = new Navigator(this, springViewDisplay);
        navigator.addProvider(viewProvider);
    }

    @WebServlet(urlPatterns = "/*", name = "UserUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UserUI.class, productionMode = false)
    public static class UserUIServlet extends SpringVaadinServlet {
    }
}