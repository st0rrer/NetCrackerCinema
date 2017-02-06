package com.netcracker.cinema.web.admin.movie;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.utils.ConfirmationDialog;
import com.netcracker.cinema.web.AdminUI;
import com.netcracker.cinema.web.admin.AdminMenu;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringView(name = ModifyAdminMovieView.VIEW_NAME, ui = AdminUI.class)
@Theme("valo")
public class ModifyAdminMovieView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";
    @Autowired
    private MovieService movieService;
    @Autowired
    private SeanceService seanceService;
    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    @Autowired
    private MovieForm movieForm;
    List<Movie> movie = null;

    @PostConstruct
    protected void init() {

        AdminMenu adminMenu = new AdminMenu();
        adminMenu.setWidth("100%");

        filterText.setInputPrompt("filter by movie...");
        filterText.setWidth("900");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, search(e.getText())));
        });

        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterText.clear();
            updateList();
        });

        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);

        Window subWindow = new Window("Sub-window");
        subWindow.setContent(subContent);
        subWindow.center();
        subWindow.setHeight("550");
        subWindow.setWidth("700");

        Panel panel = new Panel();
        panel.setContent(movieForm);

        subContent.addComponent(panel);

        Button addMovieBtn = new Button("Add new movie");
        addMovieBtn.addClickListener(e -> {
            movieForm.setMovie(new Movie(), subWindow, this);
            UI.getCurrent().addWindow(subWindow);
        });

        Button editMovieBtn = new Button("Edit");
        editMovieBtn.addClickListener(e -> {
            if(!grid.getSelectionModel().getSelectedRows().isEmpty()) {
                Long temp = ((Movie) grid.getSelectedRow()).getId();
                Movie movie = movieService.getById(temp);
                movieForm.setMovie(movie, subWindow, this);
                UI.getCurrent().addWindow(subWindow);
            } else {
                UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Select the movie"));
            }
        });

        Button deleteMovieBtn = new Button("Delete");
        deleteMovieBtn.addClickListener(e -> {
            if(!grid.getSelectionModel().getSelectedRows().isEmpty()) {
                Movie movie = (Movie) grid.getSelectionModel().getSelectedRows().iterator().next();
                if(seanceService.getCountActiveMoviesById(movie.getId()) > 0) {
                    UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("You can not delete a movie with active seances"));
                    return;
                }
                movieService.delete(movie);
                this.updateList();
                grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, search(filterText.getValue())));
            } else {
                UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Select the movie"));
            }
        });

        HorizontalLayout toolbar = new HorizontalLayout(filtering, addMovieBtn, editMovieBtn, deleteMovieBtn);
        toolbar.setSpacing(true);

        grid.setColumns("name", "duration", "imdb", "basePrice", "periodicity", "startDate", "endDate");
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);

        addComponents(toolbar, grid);

        updateList();

        setMargin(true);
        setSpacing(true);
    }

    public void updateList() {
        movie = movieService.findAll();
        grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, movie));
    }

    public List<Movie> search(String stringFilter) {
        ArrayList<Movie> arrayList = new ArrayList<>();
        for (Movie contact : movie) {

            boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
                    || contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
            if (passesFilter) {
                arrayList.add(contact);
            }
        }
        Collections.sort(arrayList, new Comparator<Movie>() {

            @Override
            public int compare(Movie o1, Movie o2) {
                return (int) (o2.getId() - o1.getId());
            }
        });
        return arrayList;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
