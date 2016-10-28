package com.netcracker.cinema;

import javax.servlet.annotation.*;

import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;

import com.netcracker.cinema.domains.Dummy;
import com.netcracker.cinema.persistance.HibernateUtil;
import com.vaadin.annotations.*;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;


/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SuppressWarnings("serial")
@SpringUI
@Theme("mytheme")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        final TextField id = new TextField();
        final TextField name = new TextField();
        id.setCaption("Type your id here:");
        name.setCaption("Type your name here:");
        Button button = new Button("Save");
        button.addClickListener(e -> {
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
            save(Long.parseLong(id.getValue()), name.getValue());
        });

        layout.addComponents(id, name, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }

    private void save(long id, String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Dummy dummy = new Dummy(id, name);
        session.save(dummy);
        session.getTransaction().commit();
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
