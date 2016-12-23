package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.service.HallService;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import com.vaadin.ui.*;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@SpringComponent
@ViewScope
public class ScheduleFilterComponent extends HorizontalLayout {

    private static String DEFAULT_DATE = "Available seances";
    private static String DEFAULT_HALL = "All halls";

    @Autowired
    private HallService hallService;

    private NativeSelect selectDate;
    private NativeSelect selectHall;

    private Object dateFilter = DEFAULT_DATE;
    private Object hallFilter = DEFAULT_HALL;
    private SeanceFilter seanceFilter = new SeanceFilter().actual().orderByIdDesc();

    @PostConstruct
    public void init() {
        this.setMargin(true);
        this.setSpacing(true);
        filterDay();
        filterHall();
    }

    private void filterDay() {
        selectDate = new NativeSelect("Filter by Day");
        fillScrollOfDate(selectDate);
        selectDate.setNullSelectionAllowed(false);
        selectDate.setWidth("100px");
        selectDate.setHeight("50px");
        selectDate.setSizeFull();
        selectDate.setImmediate(true);
        this.addComponent(selectDate);
    }

    private void filterHall() {
        selectHall = new NativeSelect("Filter by hall");
        fillScrollOfHalls(selectHall);
        selectHall.setNullSelectionAllowed(false);
        selectHall.setWidth("100px");
        selectHall.setHeight("50px");
        selectHall.setSizeFull();
        selectHall.setImmediate(true);
        this.addComponent(selectHall);
    }

    private void fillScrollOfHalls(NativeSelect selectHall) {
        selectHall.addItem(DEFAULT_HALL);
        List<Hall> allHalls = hallService.findAll();
        for(Hall hall : allHalls) {
            selectHall.addItem(hall);
            selectHall.setItemCaption(hall, hall.getName());
        }
        selectHall.setValue(DEFAULT_HALL);
    }

    private void fillScrollOfDate(NativeSelect selectDate) {
        SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd MM yyyy");
        Date currentDay = new Date();
        currentDateFormat.format(currentDay);
        selectDate.addItem(currentDay);
        selectDate.setItemCaption(currentDay, "Today");
        selectDate.addItem(DateUtils.addDays(currentDay, 1));
        selectDate.setItemCaption(DateUtils.addDays(currentDay, 1), "Tomorrow");
        selectDate.addItem(DateUtils.addDays(currentDay, 7));
        selectDate.setItemCaption(DateUtils.addDays(currentDay, 7), "Next week");
        selectDate.addItem(DEFAULT_DATE);
        selectDate.setValue(DEFAULT_DATE);
    }


    public SeanceFilter getSeanceFilter() {
        seanceFilter = new SeanceFilter();
        if(dateFilter.getClass() == String.class) {
            seanceFilter.actual();
        } else {
            seanceFilter.forDateRange(new Date(), (Date) dateFilter).orderByIdDesc();
        }

        if(hallFilter.getClass() == String.class) {
            seanceFilter.orderByIdDesc();
        } else {
            seanceFilter.forHallId(((Hall) hallFilter).getId()).orderByIdDesc();
        }
        return seanceFilter;
    }

    public NativeSelect getSelectDate() {
        return selectDate;
    }

    public NativeSelect getSelectHall() {
        return  selectHall;
    }

    public void setDateFilter(Object dateFilter) {
        this.dateFilter = dateFilter;
    }

    public void setHallFilter(Object hallFilter) {
        this.hallFilter = hallFilter;
    }
}
