package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.vaadin.event.LayoutEvents;
import com.vaadin.ui.*;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.Date;

public class ScheduleFilterComponent extends HorizontalLayout {

    private SeanceFilter seanceFilter = new SeanceFilter().orderByIdDesc();
    private NativeSelect selectDate;
    private NativeSelect selectHall;

    private String dateFilter;
    private String hallFilter;

    public ScheduleFilterComponent() {
        this.setMargin(true);
        this.setSpacing(true);
        filterDay();
        filterHall();
    }

    private void filterDay() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Available seances");
        list.add("Today");
        list.add("Tomorrow");
        selectDate = new NativeSelect("Filter by Day");
        selectDate.setNullSelectionAllowed(false);
        selectDate.addItems(list);
        selectDate.setValue(list.get(0));
        selectDate.setWidth("100px");
        selectDate.setHeight("50px");
        selectDate.setSizeFull();
        selectDate.setImmediate(true);

        this.addComponent(selectDate);

        selectDate.addValueChangeListener(event -> {
            dateFilter = String.valueOf(event.getProperty().getValue());
        });
    }

    private void filterHall() {
        ArrayList<String> list = new ArrayList<>();
        list.add("All hall");
        list.add("Hall 1");
        list.add("Hall 2");
        selectHall = new NativeSelect("Filter by hall");
        selectHall.setNullSelectionAllowed(false);
        selectHall.addItems(list);
        selectHall.setValue(list.get(0));
        selectHall.setWidth("100px");
        selectHall.setHeight("50px");
        selectHall.setSizeFull();
        selectHall.setImmediate(true);

        this.addComponent(selectHall);

        selectHall.addValueChangeListener(event -> {
            hallFilter = String.valueOf(event.getProperty().getValue());
        });
    }


    private void createNewFilter() {
        Date today = new Date();
        seanceFilter = new SeanceFilter().forHallId(Integer.parseInt(hallFilter)).forDateRange(today, DateUtils.addDays(new Date(), 1));
    }

    public SeanceFilter getSeanceFilter() {
//        Date today = new Date();
//        seanceFilter = new SeanceFilter().forHallId(Integer.parseInt(hallFilter)).forDateRange(today, DateUtils.addDays(new Date(), 1));
        return seanceFilter;
    }

}
