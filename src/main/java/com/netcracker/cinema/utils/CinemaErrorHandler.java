package com.netcracker.cinema.utils;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;

/**
 * Created by aogim on 21.01.2017.
 */
public class CinemaErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(CinemaErrorHandler.class);
    @Override
    public void error(ErrorEvent event) {
        LOGGER.warn("Uncaught Exception. Redirecting to error page", event.getThrowable());
        UI.getCurrent().getPage().setLocation("/error");
    }
}
