package com.netcracker.cinema.web.admin;
import com.netcracker.cinema.web.AdminUI;
import com.netcracker.cinema.web.admin.addMovie.AddMoviePopup;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


/**
 * Created by Илья on 24.11.2016.
 */
@SpringView(name = Hello.VIEW_NAME, ui = AdminUI.class)
public class Hello extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";

    @Autowired
    private AddMoviePopup addMoviePopup;

    @PostConstruct
    void init() {
       addComponent(new Label("Hello, I'm admin"));

        HorizontalLayout popupContent = new HorizontalLayout();
        popupContent.addComponent(addMoviePopup);

        PopupView popup = new PopupView("",popupContent);
        addComponent(popup);
        popup.setWidth("300");
        popup.setHeight("300");

        Button button = new Button("Show table", click -> // Java 8
                popup.setPopupVisible(true));
        addComponent(button);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }


}
