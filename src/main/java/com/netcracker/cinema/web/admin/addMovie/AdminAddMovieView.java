package com.netcracker.cinema.web.admin.addMovie;

import com.netcracker.cinema.dao.MovieDao;
import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.web.AdminUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;


@SpringView(name = AdminAddMovieView.VIEW_NAME, ui = AdminUI.class)
public class AdminAddMovieView extends GridLayout implements View {
    public static final String VIEW_NAME = "addMovie";

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
    private MovieDao movieDao;

    Movie temp;


    @PostConstruct
    protected void init() {
        setRows(30);
        setColumns(30);

        titleAndDesc = new FormLayout();
        initTitleAndDescription();

        info = new FormLayout();
        initInfo();



        addComponent(titleAndDesc, 6, 2, 10, 4);
        addComponent(info,15, 2, 25, 29);

        setSizeFull();

        save.addClickListener(clickEvent ->
                Notification.show("Movie has been added")
        );
        save.addClickListener(clickEvent ->
            movieDao.save(newMovie())
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

        temp.setStartDate(rollingStart == null ? LocalDate.now() :
                rollingStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        temp.setEndDate(rollingEnd == null ? LocalDate.now() :
                rollingStart.getValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return temp;
    }




    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}


