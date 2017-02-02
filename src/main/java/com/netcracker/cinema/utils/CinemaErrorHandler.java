package com.netcracker.cinema.utils;

import com.vaadin.server.ErrorEvent;
import com.vaadin.server.ErrorHandler;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;

public class CinemaErrorHandler implements ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(CinemaErrorHandler.class);
    @Override
    public void error(ErrorEvent event) {
        LOGGER.warn("Uncaught Exception. Redirecting to error page", event.getThrowable());
        UI.getCurrent().getPage().setLocation("/error");
    }
}
