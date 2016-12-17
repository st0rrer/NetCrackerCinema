package com.netcracker.cinema.web.user;

import com.netcracker.cinema.dao.filter.impl.SeanceFilter;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.event.LayoutEvents;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.List;

@SpringView(name = ScheduleView.VIEW_NAME, ui = UserUI.class)
public class ScheduleView extends VerticalLayout implements View {

    public static final String VIEW_NAME = "seance";

    @Autowired
    private ScheduleTable scheduleTable;

    private SeanceFilter filter = new SeanceFilter().orderByIdDesc();

    @PostConstruct
    void init() {

        ScheduleFilterComponent seanceFilter = new ScheduleFilterComponent();
        seanceFilter.addLayoutClickListener(new LayoutEvents.LayoutClickListener() {
            @Override
            public void layoutClick(LayoutEvents.LayoutClickEvent event) {
                final Component child = event.getChildComponent();
                if(child != null) {
                    if(!filter.equals(seanceFilter.getSeanceFilter())) {
                        filter = seanceFilter.getSeanceFilter();
                        scheduleTable.updateGrid(seanceFilter.getSeanceFilter());
                    }
                }
            }
        });

        addComponent(seanceFilter);

        scheduleTable.updateGrid(filter);

        addComponent(scheduleTable);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
