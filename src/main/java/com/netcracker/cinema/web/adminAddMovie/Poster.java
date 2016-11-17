package com.netcracker.cinema.web.adminAddMovie;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.VerticalLayout;


/**
 * Created by Илья on 07.11.2016.
 */
public class Poster extends VerticalLayout {
    Button setPoster;
    Image image;
    Resource fileResource;
    public Poster() {
        fileResource = new ThemeResource("poster.jpg");
        image = new Image(null, fileResource);
        image.setAlternateText("poster");
        addComponent(image);
        image.setSizeFull();
        setPoster = new Button("Set poster");
        addComponent(setPoster);
        setComponentAlignment(setPoster, Alignment.BOTTOM_CENTER);

    }
}
