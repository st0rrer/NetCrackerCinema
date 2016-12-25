package com.netcracker.cinema.web;

import com.vaadin.spring.server.SpringVaadinServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/*", name = "DefaultUIServlet", asyncSupported = true)
public class DefaultUIServlet extends SpringVaadinServlet {
}
