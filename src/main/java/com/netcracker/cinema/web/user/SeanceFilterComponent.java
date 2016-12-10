package com.netcracker.cinema.web.user;

import com.vaadin.ui.*;

class SeanceFilterComponent extends CustomComponent {

    public SeanceFilterComponent() {
        VerticalLayout root = new VerticalLayout();
        root.setMargin(true);
        root.setSpacing(true);
        setCompositionRoot(root);
        filterDay(root);
        filterTime(root);
        filterHall(root);
    }

    private void filterDay(VerticalLayout layout) {
        NativeSelect select = new NativeSelect("Filter by Day");
        select.setValue("All week");
        select.addItems("Today", "Tomorrow");
        select.setWidth("100px");
        select.setHeight("50px");
        layout.addComponent(select);
        select.setSizeFull();
    }

    private void filterTime(VerticalLayout layout) {
        NativeSelect select = new NativeSelect("Filter by Time");
        select.addItems("Before noon", "After noon");
        select.setWidth("100px");
        select.setHeight("50px");
        layout.addComponent(select);
        select.setSizeFull();
        // layout.setComponentAlignment(select, Alignment.MIDDLE_CENTER);
    }

    private void filterHall(VerticalLayout layout) {
        NativeSelect select = new NativeSelect("Filter by hall");
        select.setValue("All hall");
        select.addItems("Hall 1", "Hall 2");
        select.setWidth("100px");
        select.setHeight("50px");
        layout.addComponent(select);
        select.setSizeFull();
    }
}
