package com.netcracker.cinema.web.user;

import com.netcracker.cinema.model.Hall;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.PlaceService;
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

@SpringView(name = HallDetailsView.VIEW_NAME, ui = UserUI.class)
public class HallDetailsView extends CustomComponent implements View {

    public static final String VIEW_NAME = "details";

    private static final Logger logger = Logger.getLogger(HallDetailsView.class);

    @Autowired
    private SeanceService seanceService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private HallService hallService;

    @Autowired
    private PlaceService placeService;

    @PostConstruct
    public void init() {
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        long seanceId;
        long hallId;
        try {
            seanceId = Long.parseLong(event.getParameters());
        } catch (NumberFormatException e) {
            logger.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleView.VIEW_NAME);
            return;
        }
        Seance seance = null;
        Hall hall = null;
        try {
            seance = seanceService.getById(seanceId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Can't find seance with this id " + seanceId, e);
            setCompositionRoot(new Label("Can't find seance with this id " + seanceId));
            return;
        }
        try {
            hallId = seance.getHallId();
        } catch (NumberFormatException e) {
            logger.info("Expected id, but was " + event.getParameters(), e);
            getUI().getNavigator().navigateTo(ScheduleView.VIEW_NAME);
            return;
        }
        try {
            hall = hallService.getById(hallId);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Can't find hall with this id " + hallId, e);
            setCompositionRoot(new Label("Can't find seance with this id " + hallId));
            return;
        }

        setCompositionRoot(new HallView(hall, placeService, seance, movieService));
    }
}