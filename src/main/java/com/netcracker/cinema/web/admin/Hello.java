package com.netcracker.cinema.web.admin;
import com.netcracker.cinema.web.AdminUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;


/**
 * Created by Илья on 24.11.2016.
 */
@SpringView(name = Hello.VIEW_NAME, ui = AdminUI.class)
public class Hello extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @PostConstruct
    void init() {
       addComponent(new Label("Hello, I'm admin"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
