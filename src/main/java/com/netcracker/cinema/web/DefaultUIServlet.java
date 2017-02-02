package com.netcracker.cinema.web;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.spring.server.SpringVaadinServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", name = "DefaultUIServlet", asyncSupported = true)
@VaadinServletConfiguration(ui = UserUI.class, productionMode = true)
public class DefaultUIServlet extends SpringVaadinServlet {
}
