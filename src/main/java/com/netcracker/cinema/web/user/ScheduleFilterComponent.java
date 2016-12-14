package com.netcracker.cinema.web.user;

import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;

import java.util.ArrayList;

class ScheduleFilterComponent extends CustomComponent {

    public ScheduleFilterComponent() {
        HorizontalLayout root = new HorizontalLayout();
        root.setMargin(true);
        root.setSpacing(true);
        setCompositionRoot(root);
        filterDay(root);
        filterTime(root);
        filterHall(root);
    }

    private void filterDay(HorizontalLayout layout) {
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

    private void filterTime(HorizontalLayout layout) {
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

    private void filterHall(HorizontalLayout layout) {
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
