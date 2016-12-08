package com.netcracker.cinema.web.adminModifyMovie;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.validator.UrlValidator;

@SpringComponent
public class MovieForm extends MovieFormDesign {

    @Autowired
    private MovieService movieService;

    private Movie movie;

    private MyUI myUI;
    private Window window;

    public MovieForm() {
        super.save.setClickShortcut(KeyCode.ENTER);
        super.save.addClickListener(e -> this.save());
        super.close.addClickListener(e -> this.close());
    }

    private void initInfo(Movie movie) {
        movie.setName("");
        movie.setImdb(0);
        movie.setDuration(0);
        movie.setBasePrice(0);
        movie.setDescription("");
        movie.setPeriodicity(0);
        movie.setPoster("");
    }


    public void setMovie(Movie movie, Window window, MyUI myUI) {
        endDate.setReadOnly(true);
        this.myUI = myUI;
        this.window = window;
        if(movie.getName() == null)
            initInfo(movie);
        this.movie = movie;
        BeanFieldGroup.bindFieldsUnbuffered(movie, this);
        System.out.println(movie.getId());
        super.name.selectAll();
    }

    private void close() {
        UI.getCurrent().removeWindow(window);
    }

    private void save() {
        if(!isCheck()) {
            return;
        }

        movieService.save(movie);
        myUI.updateList();
        UI.getCurrent().removeWindow(window);
    }

    private boolean isCheck() {

        UrlValidator urlValidator = new UrlValidator();
        if(urlValidator.isValid(super.poster.getValue())) {
            System.out.println("url is not valid");
        }


        if(super.startDate.isEmpty()
                || super.endDate.isEmpty()) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(myUI, "Value(s) is empty or isn't correct"));
            return false;
        }

        if(super.endDate.getValue().getTime() < super.startDate.getValue().getTime()) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(myUI, "\"Start date\" can't be older then the \"End date\""));
            return false;
        }

        try {
            Integer.parseInt(super.duration.getValue());
            Integer.parseInt(super.imdb.getValue());
            Integer.parseInt(super.periodicity.getValue());
            Double.parseDouble(super.basePrice.getValue());
        } catch (Exception e) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(myUI, "Value(s) isn't correct"));
            return false;
        }

        if(super.name.getValue().trim().length() == 0
                || super.duration.getValue().trim().length() == 0
                || super.imdb.getValue().trim().length() == 0
                || super.basePrice.getValue().trim().length() == 0
                || super.periodicity.getValue().trim().length() == 0
                || super.poster.getValue().trim().length() == 0
                || super.description.isEmpty()) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(myUI, "Please, fill all fields"));
            return false;
        }
        return true;
    }
}