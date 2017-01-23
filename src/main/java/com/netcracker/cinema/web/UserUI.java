package com.netcracker.cinema.web;

import com.netcracker.cinema.utils.CinemaErrorHandler;
import com.netcracker.cinema.web.user.UserMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

@Theme("cinema")
@SpringUI
public class UserUI extends UI {
    @Autowired
    private SpringViewProvider viewProvider;
    
    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        setErrorHandler(new CinemaErrorHandler());
        root.setSizeFull();
        setContent(root);

        addUserMenu(root);
        addViewDisplay(root);
    }

    private void addViewDisplay(VerticalLayout root) {
        Panel springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);

        Navigator navigator = new Navigator(this, springViewDisplay);
        navigator.addProvider(viewProvider);
    }

    private void addUserMenu(VerticalLayout root) {
        UserMenu userMenu = new UserMenu();
        userMenu.setWidth("100%");
        root.addComponent(userMenu);
    }
}