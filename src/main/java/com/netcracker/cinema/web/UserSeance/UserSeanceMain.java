package com.netcracker.cinema.web.UserSeance;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import org.apache.log4j.Logger;

import javax.servlet.annotation.WebServlet;

/**
 * Created by Илья on 10.11.2016.
 */
@SpringUI
@Theme("valo")
public class UserSeanceMain extends UI {
    private static final Logger logger = Logger.getLogger(UserSeanceMain.class);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //Тут мы создаем layout-сетку 30х30
        final GridLayout content = new GridLayout(30, 30);
        //Тут создаем дополнительные layout'ы, котрые будут заполнять информацией наш основной layout
        Layout poster = new Poster();
        Layout seats = new Seats(10, 9);
        Layout description = new Description();
        Layout email = new Email();
        Layout totalPrice = new TotalPrice();
        Layout backAndReserve = new BackAndReserve();

        //Добавляет в ячейки 1/1 - 4/4 постер фильма
        content.addComponent(poster, 1, 1, 4, 4);
        //Добавляем в ячейки 1/5 - 28/28 места
        content.addComponent(seats, 1, 5, 28, 28);
        //Далее - по аналогии
        content.addComponent(description, 5, 1, 14, 4);
        content.addComponent(totalPrice, 15, 3, 18, 4);
        content.addComponent(email, 19, 2, 21, 2);
        content.addComponent(backAndReserve, 19, 3, 21, 4);


        setContent(content);
        content.setSizeFull();
    }

    @WebServlet(urlPatterns = "/UserSeanceMain/*", name = "UserSeanceMainUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = UserSeanceMain.class, productionMode = false)
    public static class AdminUIServlet extends SpringVaadinServlet {
    }
}
