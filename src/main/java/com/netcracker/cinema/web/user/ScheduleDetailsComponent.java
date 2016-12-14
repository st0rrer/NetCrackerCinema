package com.netcracker.cinema.web.user;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

import javax.annotation.PostConstruct;

public class ScheduleDetailsComponent extends CustomComponent implements View {

    @PostConstruct
    public void init() {
        Label label = new Label("Implement me!");
        setCompositionRoot(label);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
