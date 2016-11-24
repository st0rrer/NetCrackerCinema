package com.netcracker.cinema.web.adminModifyMovie;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import com.vaadin.ui.themes.ValoTheme;

import javax.servlet.annotation.WebServlet;
import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private MovieService service = MovieService.getInstance();
    private Grid grid = new Grid();
    private TextField filterText = new TextField();
    private MovieForm form = new MovieForm(this);

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();

//        UploadImage receiver = new UploadImage();


//        Upload upload = new Upload("Upload Image Here", receiver);
////        upload.setButtonCaption("Start Upload");
//        upload.addSucceededListener(receiver);



        MenuBar barmenu = new MenuBar();
        MenuBar.MenuItem movies = barmenu.addItem("Movies", null, null);
        MenuBar.MenuItem schedule = barmenu.addItem("Schedule", null, null);
        MenuBar.MenuItem statistics = barmenu.addItem("Statistics", null, null);

        // filter and clear
        filterText.setInputPrompt("filter by movie...");
        filterText.addTextChangeListener(e -> {
            grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, service.findAll(e.getText())));
        });
        Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
        clearFilterTextBtn.setDescription("Clear the current filter");
        clearFilterTextBtn.addClickListener(e -> {
            filterText.clear();
            updateList();
        });

        // concat elements
        CssLayout filtering = new CssLayout();
        filtering.addComponents(filterText, clearFilterTextBtn);
        filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);



        Button addMovieBtn = new Button("Add new movie");
        addMovieBtn.addClickListener(e -> {
            grid.select(null);
            form.setMovie(new Movie());
        });
        // horizontal
        HorizontalLayout toolbar = new HorizontalLayout(filtering, addMovieBtn);
        toolbar.setSpacing(true);

        grid.setColumns("movie", "duration", "imdb", "basePrice", "periodicity", "timeOut", "rollingStart", "rollingEnd");
        grid.addColumn("hello", Resource.class)
                .setRenderer(new ImageRenderer());

        HorizontalLayout main = new HorizontalLayout(grid, form);
        main.setSpacing(true);
        main.setSizeFull();
        grid.setSizeFull();
        main.setExpandRatio(grid, 1);

        layout.addComponents(barmenu, toolbar, main);

        updateList();

        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        form.setVisible(false);

        grid.addSelectionListener(event -> {
            if (event.getSelected().isEmpty()) {
                form.setVisible(false);
            } else {
                Movie movie = (Movie) event.getSelected().iterator().next();
                form.setMovie(movie);
            }
        });
    }

    public void updateList() {
        List<Movie> movies = service.findAll(filterText.getValue());
        grid.setContainerDataSource(new BeanItemContainer<>(Movie.class, movies));
    }

    @WebServlet(urlPatterns = "/adminMyUI*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
