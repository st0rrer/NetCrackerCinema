package com.netcracker.cinema.web.user;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;

class ScheduleFilterComponent extends CustomComponent {

    public ScheduleFilterComponent() {
        VerticalLayout root = new VerticalLayout();
        root.setMargin(true);
        root.setSpacing(true);
        setCompositionRoot(root);
        filterDay(root);
        filterTime(root);
        filterHall(root);
    }

    private void filterDay(VerticalLayout layout) {
        ArrayList<String> list = new ArrayList<>();
        list.add("All days");
        list.add("Today");
        list.add("Tomorrow");
        NativeSelect select = new NativeSelect("Filter by Day");
        select.setNullSelectionAllowed(false);
        select.addItems(list);
        select.setValue(list.get(0));
        select.setWidth("100px");
        select.setHeight("50px");
        layout.addComponent(select);
        select.setSizeFull();
        select.setImmediate(true);
    }

    private void filterTime(VerticalLayout layout) {
        ArrayList<String> list = new ArrayList<>();
        list.add("Before noon");
        list.add("After noon");
        NativeSelect select = new NativeSelect("Filter by Time");
        select.setNullSelectionAllowed(false);
        select.addItems(list);
        select.setValue(list.get(0));
        select.setWidth("100px");
        select.setHeight("50px");
        layout.addComponent(select);
        select.setSizeFull();
        select.setImmediate(true);
    }

    private void filterHall(VerticalLayout layout) {
        ArrayList<String> list = new ArrayList<>();
        list.add("All hall");
        list.add("Hall 1");
        list.add("Hall 2");
        NativeSelect select = new NativeSelect("Filter by hall");
        select.setNullSelectionAllowed(false);
        select.addItems(list);
        select.setValue(list.get(0));
        select.setWidth("100px");
        select.setHeight("50px");
        layout.addComponent(select);
        select.setSizeFull();
        select.setImmediate(true);
    }
}
