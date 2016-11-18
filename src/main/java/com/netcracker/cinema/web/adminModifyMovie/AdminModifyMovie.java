package com.netcracker.cinema.web.adminModifyMovie;
import javax.servlet.annotation.WebServlet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@SpringUI(path = "/adminAddMovieMain")
@Theme("valo")
public class AdminModifyMovie extends UI {
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout = new VerticalLayout();
        final VerticalLayout layout1 = new VerticalLayout();
        MenuBar barmenu = new MenuBar();
        //addComponent(barmenu);
        MenuBar.MenuItem movies = barmenu.addItem("Movies", null, null);
        MenuBar.MenuItem schedule = barmenu.addItem("Schedule", null, null);
        MenuBar.MenuItem statistics = barmenu.addItem("Statistics", null, null);
        barmenu.setHeight("30px");
        Button btnAdd = new Button("Add");
        btnAdd.addClickListener(e -> {
        });
        Button btnEdit = new Button("Edit");
        btnAdd.addClickListener(e -> {
        });
        Button btnRemove = new Button("Remove");
        btnAdd.addClickListener(e -> {
        });
        ComboBox comboBox = new ComboBox();
        comboBox.addItem("Rolling start");
        comboBox.addItem("Title");
        Resource fileResource = new ThemeResource("B5wEpk55Ncw.jpg");
        Image image = new Image("Image", fileResource);
        image.setHeight("100px");
        image.setWidth("100px");
        image.setAlternateText(image.getCaption());
        Label movieName = new Label("Джек Ричер");
        Label descritption = new Label("jsdlkfjdskljflsjflsjgjs\n" +
                "djfdskljfklsdjflksdjfldsj\n" +
                "fdsfdsfdsffsdfdsfsdfdsf");
        Label duration = new Label("Duration: 1h 55m");
        Label imdb = new Label("IMDB: 7.0");
        Label basePrice = new Label("Base price: 50");
        Label periodicity = new Label("2");
        Label timeOut = new Label("Time out: 30 min");
        Label rollingStart = new Label("Rolling start: 07/10/2016");
        Label rollingEnd = new Label("Rolling start: 07/10/2016");
        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(movieName, descritption, duration, imdb, basePrice, periodicity, timeOut, rollingStart, rollingEnd);
        verticalLayout.setSpacing(true);
        HorizontalLayout toolbar = new HorizontalLayout(btnAdd, comboBox);
        layout.addComponents(image, btnEdit, btnRemove);
        layout.setSpacing(true);
        HorizontalLayout hor = new HorizontalLayout();
        hor.addComponents(layout,verticalLayout);
        hor.setSpacing(true);
        layout1.addComponents(barmenu,toolbar, hor);
        layout1.setMargin(true);
        layout1.setSpacing(true);
        setContent(layout1);
    }
    @WebServlet(urlPatterns = "/adminModifyMovie/*", name = "adminModifyMovieUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = AdminModifyMovie.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
