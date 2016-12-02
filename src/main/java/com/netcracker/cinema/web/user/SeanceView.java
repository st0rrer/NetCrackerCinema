package com.netcracker.cinema.web.user;

import com.netcracker.cinema.service.HallService;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.web.UserUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@SpringView(name = SeanceView.VIEW_NAME, ui = UserUI.class)
public class SeanceView extends CustomComponent implements View {

    @Autowired
    private MovieService movieService;
    private HallService hallService;

    public static final String VIEW_NAME = "seance";
    private final int GRID_COLUMNS = 4;

//    @PostConstruct
//    public void init() {
//        Label label = new Label("Implement me!");
//        setCompositionRoot(label);
//    }

//    @PostConstruct
//    void init() {
//        List<Movie> movies = movieService.findAll();
//        List<Hall> halls = hallService.findAll();
//        setGridSize(movies);
//
//        for (Movie movie : movies) {
//            MovieComponent movieComponent = new MovieComponent(movie);
//            addComponent(movieComponent);
//
//            for (Hall hall : halls) {
//                HallComponent hallComponent = new HallComponent(hall, movie);
//                addComponent(hallComponent);
//            }
//        }
//    }
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//
//    }
//
//    private void setGridSize(List<Movie> movies) {
//        int rows = movies.size() % GRID_COLUMNS + 1;
//        setColumns(GRID_COLUMNS);
//        setRows(rows);
//    }
//
//}

//    void init() {
//        List<Movie> movies = movieService.findAll();
//
//        setGridSize(movies);
//
//        for(Movie movie: movies) {
//            MovieComponent movieComponent = new MovieComponent(movie);
//            addComponent(movieComponent);
//        }
//    }
//
//    private void setGridSize(List<Movie> movies) {
//        int rows = movies.size() % GRID_COLUMNS + 1;
//        setColumns(GRID_COLUMNS);
//        setRows(rows);
//    }
//
//    @Override
//    public void enter(ViewChangeListener.ViewChangeEvent event) {
//    }
//}
@PostConstruct
public void init() {
    Label label = new Label("Implement me!");
    setCompositionRoot(label);
}

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
