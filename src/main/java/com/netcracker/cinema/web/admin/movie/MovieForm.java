package com.netcracker.cinema.web.admin.movie;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.utils.ConfirmationDialog;
import com.netcracker.cinema.validation.ValidationExecutor;
import com.netcracker.cinema.validation.ValidationResult;
import com.netcracker.cinema.validation.routines.DateValidator;
import com.netcracker.cinema.validation.routines.EmptyFieldsValidator;
import com.netcracker.cinema.validation.routines.IntegerValidator;
import com.netcracker.cinema.validation.routines.UrlValidator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@SpringComponent
public class MovieForm extends MovieFormDesign {

    @Autowired
    private MovieService movieService;

    @Autowired
    private SeanceService seanceService;

    private Movie movie;

    private ModifyAdminMovieView modifyAdminMovieView;
    private Window window;

    public MovieForm() {
        super.save.setClickShortcut(KeyCode.ENTER);
        super.save.addClickListener(e -> this.save());
        super.close.addClickListener(e -> this.close());
    }

    private void initInfo() {
        name.setNullRepresentation("");
        imdb.setNullRepresentation("");
        duration.setNullRepresentation("");
        basePrice.setNullRepresentation("");
        description.setNullRepresentation("");
        periodicity.setNullRepresentation("");
        poster.setNullRepresentation("");
        startDate.setTextFieldEnabled(false);
        endDate.setTextFieldEnabled(false);
    }


    public void setMovie(Movie movie, Window window, ModifyAdminMovieView modifyAdminMovieView) {
        this.modifyAdminMovieView = modifyAdminMovieView;
        this.window = window;
        initInfo();
        this.movie = movie;
        BeanFieldGroup.bindFieldsUnbuffered(movie, this);
        name.selectAll();
    }

    private void close() {
        UI.getCurrent().removeWindow(window);
    }

    private void save() {
        if(!isCheck()) {
            return;
        }

        if(movie.getId() != 0 && seanceService.getCountActiveMoviesById(movie.getId()) > 0) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("You can not change a movie with active seances"));
            return;
        }
        movieService.save(movie);
        modifyAdminMovieView.updateList();
        UI.getCurrent().removeWindow(window);
    }

    private boolean isCheck() {

        ArrayList<String> list = new ArrayList<>();
        list.add(duration.getValue());
        list.add(imdb.getValue());
        list.add(periodicity.getValue());
        list.add(basePrice.getValue());

        ArrayList<String> empty = new ArrayList<>();
        empty.addAll(list);
        empty.add(name.getValue());
        empty.add(description.getValue());
        empty.add(poster.getValue());

        ValidationResult validationResult = new ValidationExecutor()
                .addValidator(new UrlValidator(poster.getValue()))
                .addValidator(new IntegerValidator(list))
                .addValidator(new EmptyFieldsValidator(empty))
                .addValidator(new DateValidator(startDate.getValue(), endDate.getValue()))
                .execute();

        if(!validationResult.isValid()) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog(validationResult.getMessage()));
            return false;
        }

        return true;
    }
}