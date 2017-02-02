package com.netcracker.cinema.web.cashier;

import com.netcracker.cinema.web.common.ScheduleTable;
import com.vaadin.spring.annotation.SpringComponent;

import javax.annotation.PostConstruct;

@SpringComponent
public class ScheduleTableCashier extends ScheduleTable {

    @PostConstruct
    public void init() {
        setSpacing(true);
        addStyleName("schedule-grid");
    }
}
