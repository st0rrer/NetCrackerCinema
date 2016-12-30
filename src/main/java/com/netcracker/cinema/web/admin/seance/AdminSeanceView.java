package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.AdminUI;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.vaadin.data.validator.DateRangeValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Titarenko on 22.12.2016.
 */
@SpringView(name = AdminSeanceView.VIEW_NAME, ui = AdminUI.class)
public class AdminSeanceView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "seances";

    private Date date = new Date(System.currentTimeMillis());
    private java.sql.Date sqlDate = new java.sql.Date(date.getTime());

    @Autowired
    private SeanceService seanceService;
    @Autowired
    private MovieService movieService;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private FormLayout formLayout;
    private VerticalLayout verticalLayout1 = new VerticalLayout();
    private VerticalLayout verticalLayout2 = new VerticalLayout();
    private VerticalLayout verticalLayout3 = new VerticalLayout();

    @PostConstruct
    protected void init() {
        InlineDateField calendar = new InlineDateField();
        calendar.setShowISOWeekNumbers(true);
        calendar.setValue(date);
//        DateRangeValidator validator = new DateRangeValidator("Wrong date",
//                new Date(System.currentTimeMillis()), new Date(Long.MAX_VALUE), Resolution.DAY);
//        if (validator.isValid(calendar.getValue())) {
//        }

        Button showButton = new Button("Show seances");
        showButton.setWidth("270px");
        showButton.addClickListener(e -> {
            date = calendar.getValue();
            sqlDate = new java.sql.Date(date.getTime());
            getHall(1, verticalLayout2);
            getHall(2, verticalLayout3);
        });

        Window subWindow = getSubWindow(new Seance());
        Button windowButton = new Button("Add new seance");
        windowButton.setWidth("270px");
        windowButton.addClickListener(e -> UI.getCurrent().addWindow(subWindow));

        verticalLayout1.setSpacing(true);
        verticalLayout1.setMargin(true);
        verticalLayout1.addComponents(calendar, showButton, windowButton);
        verticalLayout1.setWidth("380px");
        verticalLayout2.setWidth("600px");
        verticalLayout3.setWidth("600px");

        getHall(1, verticalLayout2);
        getHall(2, verticalLayout3);

        VerticalLayout verticalLayout4 = new VerticalLayout();
        addComponents(verticalLayout1, verticalLayout2, verticalLayout3, verticalLayout4);
        setExpandRatio(verticalLayout1, 4);
        setExpandRatio(verticalLayout2, 6);
        setExpandRatio(verticalLayout3, 6);
        setExpandRatio(verticalLayout4, 1);
        setSizeFull();
    }

    private Window getSubWindow(Seance seance) {
        Window subWindow = new Window();
        if (seance.getId() == 0) {
            subWindow.setCaption("Add new seance");
        } else {
            subWindow.setCaption("Change seance");
        }

        subWindow.setContent(getComponents(seance));
        subWindow.center();
        subWindow.setHeight("350px");
        subWindow.setWidth("500px");
        subWindow.setResizable(false);
        return subWindow;
    }

    private Layout getComponents(Seance seance) {
        List<Movie> movieList = movieService.findAll();

        AbsoluteLayout layout = new AbsoluteLayout();

        VerticalLayout poster = new VerticalLayout();
        poster.setWidth("160px");

        VerticalLayout subContent = new VerticalLayout();
        subContent.setWidth("250px");
        subContent.setSpacing(true);

        DateField seanceDate = new DateField("Date:");
        seanceDate.setResolution(Resolution.MINUTE);
        seanceDate.setWidth("250px");

        NativeSelect hallId = new NativeSelect("Hall:");
        for (int i = 1; i < 3; i++) {
            hallId.addItem(i);
            hallId.setItemCaption(i, "Hall " + i);
        }
        hallId.setNullSelectionAllowed(false);
//        hallId.setValue(1);
        hallId.setImmediate(true);

        ComboBox movieName = new ComboBox("Movie:");
        movieName.setFilteringMode(FilteringMode.CONTAINS);
        movieName.setImmediate(true);
        movieName.setInputPrompt("start type...");
        movieName.setNullSelectionAllowed(false);
        movieName.setWidth("250px");
        for (Movie movie : movieList) {
            movieName.addItem(movie.getName());
        }
        movieName.addValueChangeListener(e -> {
            poster.removeAllComponents();
            for (Movie movie : movieList) {
                if (movie.getName().equals(movieName.getValue())) {
                    poster.addComponent(createPoster(movie, "220px"));
                }
            }
        });

        Button eraseButton = new Button("Erase all");
        eraseButton.setWidth("150px");
        eraseButton.addClickListener(e -> {
            hallId.setValue(null);
            movieName.setValue(null);
            seanceDate.setValue(null);
            poster.removeAllComponents();
        });

        if (seance != null && seance.getId() != 0) {
            seanceDate.setValue(seance.getSeanceDate());
            hallId.setValue(seance.getHallId());
            for (Movie movie : movieList) {
                if (movie.getId() == seance.getMovieId()) {
                    movieName.setValue(movie.getName());
                }
            }
        }

        Button saveButton = new Button("Save seance");
        saveButton.setWidth("250px");
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addClickListener(e -> {
            for (Movie movie : movieList) {
                if (movie.getName().equals(movieName.getValue())) {
                    if (seance.getId() == 0 || seanceService.editableSeance(seance.getId())) {
                        Seance newSeance = (seance.getId() == 0 ? new Seance() : seance);
                        long oldId = newSeance.getId();
                        Date oldDate = newSeance.getSeanceDate();
                        newSeance.setSeanceDate(seanceDate.getValue());
                        newSeance.setMovieId(movie.getId());
                        newSeance.setHallId(Long.parseLong(hallId.getValue().toString()));
                        if (seanceService.checkDate(newSeance)) {
                            seanceService.save(newSeance);
                            if (dateFormat.format(date).equals(dateFormat.format(newSeance.getSeanceDate())) ||
                                    (oldDate != null &&
                                            dateFormat.format(date).equals(dateFormat.format(oldDate)))) {
                                getHall(1, verticalLayout2);
                                getHall(2, verticalLayout3);
                            }
                            Notification.show("Success!");
                        } else {
                            notificationForWrongSeanceDate();
                        }
                        if (oldId == 0) {
                            newSeance.setId(0);
                        }
                    } else {
                        notificationForUnmodifiedSeance();
                    }
                    break;
                }
            }
        });

        subContent.addComponents(hallId, movieName, seanceDate);

        layout.addComponent(subContent, "left: 20px; top: 10px;");
        layout.addComponent(poster, "right: 20px; top: 10px;");
        layout.addComponent(eraseButton, "left: 20px; bottom: 20px;");
        layout.addComponent(saveButton, "right: 20px; bottom: 20px;");

        return layout;
    }

    private void notificationForWrongSeanceDate() {
        String message = "Date of seance should be between"
                + LineSeparator.Windows + "start and final dates of selected movie.";
        Notification.show("Invalid date", message, Notification.Type.TRAY_NOTIFICATION);
    }


    private void notificationForUnmodifiedSeance() {
        String message = "Few seconds ago someone has booked tickets"
                + LineSeparator.Windows + "for this seance, so it can't be modified.";
        Notification.show("Booked tickets", message, Notification.Type.TRAY_NOTIFICATION);
    }

    private void getHall(long hallId, VerticalLayout layout) {
        List<Seance> seanceList = seanceService.getByHallAndDate(hallId, sqlDate);
        seanceList.sort(Comparator.comparing(Seance::getSeanceDate));
        layout.removeAllComponents();
        layout.setSpacing(true);
        layout.setMargin(true);

        Label hallLabel = new Label("Hall " + hallId);
        hallLabel.setStyleName(ValoTheme.LABEL_H2);
        Label dateLabel = new Label(dateFormat.format(date));

        VerticalLayout verticalLayout = new VerticalLayout();
        for (Seance seance : seanceList) {
            HorizontalLayout horizontalLayout = getSeanceRow(seance);
            horizontalLayout.setMargin(true);
            verticalLayout.addComponent(horizontalLayout);
        }

        Panel hallPanel = new Panel();
        hallPanel.setHeight("750px");
        hallPanel.setContent(verticalLayout);

        layout.addComponents(hallLabel, dateLabel, hallPanel);
    }

    private HorizontalLayout getSeanceRow(Seance seance) {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        Movie movie = movieService.getById(seance.getMovieId());

        VerticalLayout movieInfo = new VerticalLayout();
        movieInfo.setWidth("200px");
        Label name = new Label(movie.getName());
        Label time = new Label(timeFormat.format(seance.getSeanceDate()));
        movieInfo.addComponents(name, time);

        Window subWindow = getSubWindow(seance);
        Button editButton = new Button("Edit");
        editButton.setEnabled(seanceService.editableSeance(seance.getId()));
        editButton.addClickListener(e -> UI.getCurrent().addWindow(subWindow));

        Button removeButton = new Button("Remove");
        removeButton.setEnabled(seanceService.editableSeance(seance.getId()));
        removeButton.addClickListener(e -> UI.getCurrent().addWindow(confirmWindow(seance)));

        horizontalLayout.addComponents(
                createPoster(movie, "80px"), movieInfo, editButton, removeButton);
        return horizontalLayout;
    }

    private Window confirmWindow(Seance seance) {
        Window removalWindow = new Window("Confirm erasing");
        removalWindow.setHeight("200px");
        removalWindow.setWidth("400px");
        removalWindow.setResizable(false);
        removalWindow.setModal(true);
        removalWindow.center();

        Label seanceDate = new Label();
        seanceDate.setValue("Seance date: " + seance.getSeanceDate());
        Label message = new Label();
        message.setValue("Do you really want to delete this seance?");

        Button yesButton = new Button("Yes");
        yesButton.addClickListener(e -> {
            if (seanceService.editableSeance(seance.getId())) {
                seanceService.delete(seance);
                removalWindow.close();
                getHall(seance.getHallId(),
                        seance.getHallId() == 1 ? verticalLayout2 : verticalLayout3);
                Notification.show("Seance deleted");
            } else {
                notificationForUnmodifiedSeance();
            }
        });
        Button noButton = new Button("No");
        noButton.addClickListener(e -> removalWindow.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponents(yesButton, noButton);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.addComponents(seanceDate, message, horizontalLayout);
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        removalWindow.setContent(verticalLayout);
        return removalWindow;
    }

    private Component createPoster(Movie movie, String height) {
        ExternalResource res = new ExternalResource(movie.getPoster());
        Image poster = new Image(null, res);
        poster.setHeight(height);
        return poster;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
    }
}