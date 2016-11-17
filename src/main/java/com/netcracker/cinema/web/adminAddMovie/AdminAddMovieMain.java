package com.netcracker.cinema.web.adminAddMovie;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SpringUI(path = "/adminAddMovieMain")
@Theme("valo")
public class AdminAddMovieMain extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        GridLayout content = new GridLayout(30, 30);
        Layout menuBar = new Menu();
        Layout poster = new Poster();
        Layout titleAndDesc = new TitleAndDesc();
        Layout info = new Info();

        content.addComponent(menuBar, 0, 0, 29, 0);
        content.addComponent(poster, 1, 2, 4, 4);
        content.addComponent(titleAndDesc, 6, 2, 10, 4);
        content.addComponent(info,11, 2, 20, 29);

        setContent(content);
        content.setSizeFull();
    }

    @WebServlet(urlPatterns = "/adminAddMovieMain/*", name = "adminAddMovieMainUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AdminAddMovieMain.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }


}


