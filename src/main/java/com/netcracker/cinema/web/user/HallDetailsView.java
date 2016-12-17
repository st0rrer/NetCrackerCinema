package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.annotation.PostConstruct;

@SpringView(name = ScheduleDetailsView.VIEW_NAME, ui = UserUI.class)
public class ScheduleDetailsView extends CustomComponent implements View {

    public static final String VIEW_NAME = "details";

    private static final Logger logger = Logger.getLogger(MovieDetailsView.class);

    @Autowired
    private SeanceService seanceService;

    @PostConstruct
    public void init() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        long seanceId;
        try {
            seanceId = Long.parseLong(event.getParameters());
        } catch (NumberFormatException e) {
            logger.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleView.VIEW_NAME);
            return;
        }
        Seance seance = null;
        try {
            seance = seanceService.getById(seanceId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Can't find seance with this id " + seanceId, e);
            setCompositionRoot(new Label("Can't find seance with this id " + seanceId));
            return;
        }

        setCompositionRoot(new ScheduleDetailsComponent());
    }
}
