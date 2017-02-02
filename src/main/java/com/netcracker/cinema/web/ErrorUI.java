package com.netcracker.cinema.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SpringUI(path = "/error")
public class ErrorUI extends UI {
    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout root = new VerticalLayout();
        setContent(root);
        root.setMargin(true);
        root.setSpacing(true);

        Label title = new Label("Houston, we have a problem");
        title.setStyleName(ValoTheme.LABEL_H1);
        Label message = new Label("The server encountered an error and could not complete your request");
        Label sorry = new Label("That's all we know :(");
        root.addComponent(title);
        root.addComponent(message);
        root.addComponent(sorry);
    }
}
