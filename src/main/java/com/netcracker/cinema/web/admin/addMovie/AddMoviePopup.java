package com.netcracker.cinema.web.admin.addMovie;

import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.impl.MovieServiceImpl;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@SpringComponent
@UIScope
public class AddMoviePopup extends HorizontalLayout {

    FormLayout titleAndDesc;

    TextField title;
    TextArea description;
    TextField posterUrl;

    FormLayout info;
    TextField duration;
    TextField imdb;
    TextField periodicity;
    TextField basePrice;
    TextField timeOut;
    DateField rollingStart;
    DateField rollingEnd;
    Button save;
    Button back;

    @Autowired
    private MovieService movieService;

    Movie temp;

    public AddMoviePopup() {
        titleAndDesc = new FormLayout();
        initTitleAndDescription();

        info = new FormLayout();
        initInfo();

        addComponent(titleAndDesc);
        addComponent(info);

        setSizeFull();

        save.addClickListener(clickEvent ->
                Notification.show("Movie has been added")
        );
        save.addClickListener(clickEvent ->
           movieService.save(newMovie())
        );

    }

    public void initTitleAndDescription(){
        posterUrl = new TextField();
        posterUrl.setCaption("Input poster URL");
        title = new TextField();
        title.setCaption("Title");
        description = new TextArea();
        description.setCaption("Description");
        titleAndDesc.addComponent(posterUrl);
        titleAndDesc.addComponent(title);
        titleAndDesc.addComponent(description);
    }

    public void initInfo(){
        duration = new TextField("Duration:");
        imdb = new TextField("Imdb:");
        periodicity = new TextField("Periodicity:");
        basePrice = new TextField("Base Price:");
        timeOut = new TextField("Time Out:");
        rollingStart = new DateField("Rolling Start:");
        rollingEnd = new DateField("Rolling End:");

        save = new Button("Save");
        back = new Button("Back");

        info.addComponent(duration);
        info.addComponent(imdb);
        info.addComponent(periodicity);
        info.addComponent(basePrice);
        info.addComponent(timeOut);
        info.addComponent(rollingStart);
        info.addComponent(rollingEnd);
        info.addComponent(save);
        info.addComponent(back);
    }

    public Movie newMovie(){
        temp = new Movie();
        temp.setName(title.getValue().equals("") ? "Title not found" : title.getValue());

        temp.setDescription(description.getValue().equals("") ? "Description not found" : description.getValue());

        temp.setPoster(posterUrl.getValue().equals("") ? "http://empo.pro/wp-content/uploads/2012/02/404-page.jpg"
                : posterUrl.getValue());

        temp.setDuration(duration.getValue().equals("") ? 90 : Integer.valueOf(duration.getValue()));

        temp.setImdb(imdb.getValue().equals("") ? 50 : Integer.valueOf(imdb.getValue()));

        temp.setPeriodicity(periodicity.getValue().equals("") ? 10 : Integer.valueOf(periodicity.getValue()));

        temp.setBasePrice(basePrice.getValue().equals("") ? 50 : Integer.valueOf(basePrice.getValue()));

        temp.setStartDate(rollingStart.getValue() == null ? new Date() : rollingStart.getValue());
        temp.setEndDate(rollingEnd.getValue() == null ? new Date() : rollingStart.getValue());
        return temp;
    }

}


