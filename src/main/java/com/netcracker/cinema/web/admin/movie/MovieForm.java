package com.netcracker.cinema.web.admin.movie;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.utils.ConfirmationDialog;
import com.netcracker.cinema.validation.MovieUIValidation;
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
        BeanFieldGroup<Movie> movieBeanFieldGroup = BeanFieldGroup.bindFieldsUnbuffered(movie, this);
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

        MovieUIValidation movieUIValidation = new MovieUIValidation();

        if(movieUIValidation.fieldsAreEmpty(empty)) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Please, fill all fields"));
            return false;
        }

        if(!movieUIValidation.isValidUrl(poster.getValue())) {
            return false;
        }

        if(!movieUIValidation.isValuesInteger(list)) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Value(s) isn't correct"));
            return false;
        }

        if(!movieUIValidation.isDateValid(startDate.getValue(), endDate.getValue())) {
            return false;
        }

        return true;
    }
}