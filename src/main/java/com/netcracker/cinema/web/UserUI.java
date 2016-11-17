package com.netcracker.cinema.web;

import javax.servlet.annotation.*;

import com.vaadin.spring.server.SpringVaadinServlet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;

@SpringUI
@Theme("valo")
public class UserUI extends UI {
    private static final Logger logger = Logger.getLogger(UserUI.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        final TextField id = new TextField();
        final TextField name = new TextField();
        id.setCaption("Type your id here:");
        name.setCaption("Type your name here:");
        Button button = new Button("Save");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });

        layout.addComponents(id, name, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "UserUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UserUI.class, productionMode = false)
    public static class UserUIServlet extends SpringVaadinServlet {
    }
}