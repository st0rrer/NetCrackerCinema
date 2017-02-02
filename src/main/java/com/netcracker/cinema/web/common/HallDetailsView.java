package com.netcracker.cinema.web.common;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

public abstract class HallDetailsView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "details";

    @Override
    public abstract void enter(ViewChangeListener.ViewChangeEvent event);
}
