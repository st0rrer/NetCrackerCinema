package com.netcracker.cinema.web.adminModifyMovie;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction.KeyCode;

public class MovieForm extends MovieFormDesign {

    private MovieService service = MovieService.getInstance();
    private Movie movie;
    private MyUI myUI;

    public MovieForm(MyUI myUI) {
        this.myUI = myUI;

        save.setClickShortcut(KeyCode.ENTER);
        save.addClickListener(e -> this.save());
        delete.addClickListener(e -> this.delete());
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
        BeanFieldGroup.bindFieldsUnbuffered(movie, this);

        // Show delete button for only customers already in the database
        delete.setVisible(movie.isPersisted());
        setVisible(true);
        super.movie.selectAll();
    }

    private void delete() {
        service.delete(movie);
        myUI.updateList();
        setVisible(false);
    }

    private void save() {
        service.save(movie);
        myUI.updateList();
        setVisible(false);
    }
}