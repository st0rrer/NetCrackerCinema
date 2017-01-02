package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.MovieService;
import com.netcracker.cinema.service.PriceService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.web.AdminUI;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Titarenko on 22.12.2016.
 */
@SpringView(name = AdminSeanceView.VIEW_NAME, ui = AdminUI.class)
public class AdminSeanceView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "seances";

    @Autowired
    private SeanceService seanceService;
    private Seance seance;
    @Autowired
    private MovieService movieService;
    private List<Movie> movieList;
    @Autowired
    private PriceService priceService;

    private Date date = new Date(System.currentTimeMillis());
    private DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
    private DateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private VerticalLayout verticalLayout1 = new VerticalLayout();
    private VerticalLayout HallLayout1 = new VerticalLayout();
    private VerticalLayout HallLayout2 = new VerticalLayout();
    private Window subWindow;

    public AdminSeanceView() {
    }

    @PostConstruct
    protected void init() {
        InlineDateField calendar = new InlineDateField();
        calendar.setShowISOWeekNumbers(true);
        calendar.setValue(date);

        Button showButton = new Button("Show seances");
        showButton.setWidth("270px");
        showButton.addClickListener(e -> {
            date = calendar.getValue();
            getHall(1, HallLayout1);
            getHall(2, HallLayout2);
        });

        Button windowButton = new Button("Add new seance");
        windowButton.setWidth("270px");
        windowButton.addClickListener(e -> {
            if (subWindow != null) {
                subWindow.close();
            }
            this.seance = new Seance();
            subWindow = getSubWindow();
            UI.getCurrent().addWindow(subWindow);
        });

        verticalLayout1.setSpacing(true);
        verticalLayout1.setMargin(true);
        verticalLayout1.addComponents(calendar, showButton, windowButton);
        verticalLayout1.setWidth("380px");
        HallLayout1.setWidth("600px");
        HallLayout2.setWidth("600px");

        getHall(1, HallLayout1);
        getHall(2, HallLayout2);

        VerticalLayout verticalLayout4 = new VerticalLayout();
        addComponents(verticalLayout1, HallLayout1, HallLayout2, verticalLayout4);
        setExpandRatio(verticalLayout1, 4);
        setExpandRatio(HallLayout1, 6);
        setExpandRatio(HallLayout2, 6);
        setExpandRatio(verticalLayout4, 1);
        setSizeFull();
    }

    private Window getSubWindow() {
        movieList = movieService.findAll();

        Window subWindow = new Window();
        if (seance.getId() == 0) {
            subWindow.setCaption("Add new seance");
        } else {
            subWindow.setCaption("Change seance");
        }

        subWindow.setContent(getComponents());
        subWindow.center();
        subWindow.setHeight("420px");
        subWindow.setWidth("500px");
        subWindow.setResizable(false);
        return subWindow;
    }

    private Layout getComponents() {
        AbsoluteLayout layout = new AbsoluteLayout();

        VerticalLayout posterLayout = new VerticalLayout();
        posterLayout.setWidth("150px");

        DateField seanceDate = new DateField("Date:");
        seanceDate.setResolution(Resolution.MINUTE);
        seanceDate.setWidth("250px");

        NativeSelect hallId = new NativeSelect("Hall:");
        for (int i = 1; i < 3; i++) {
            hallId.addItem(i);
            hallId.setItemCaption(i, "Hall " + i);
        }
        hallId.setNullSelectionAllowed(false);
        hallId.setImmediate(true);

        ComboBox movieName = new ComboBox("Movie:");
        movieName.setFilteringMode(FilteringMode.CONTAINS);
        movieName.setImmediate(true);
        movieName.setInputPrompt("start type...");
        movieName.setNullSelectionAllowed(false);
        movieName.setWidth("250px");
        for (Movie movie : movieList) {
            movieName.addItem(movie.getId());
            movieName.setItemCaption(movie.getId(), movie.getName());
        }
        movieName.addValueChangeListener(e -> {
            posterLayout.removeAllComponents();
            for (Movie movie : movieList) {
                if (movie.getId().equals(movieName.getValue())) {
                    Label startDate = new Label("start: " + dateFormat.format(movie.getStartDate()));
                    Label endDate = new Label("final: " + dateFormat.format(movie.getEndDate()));
                    posterLayout.addComponents(createPoster(movie, "170px"), startDate, endDate);
                }
            }
        });

        TextField priceFieldA = new TextField("Zone 'AA'");
        TextField priceFieldB = new TextField("Zone 'BB'");
        TextField priceFieldC = new TextField("Zone 'CC'");

        List<TextField> textFieldList = new ArrayList<>();
        textFieldList.add(priceFieldA);
        textFieldList.add(priceFieldB);
        textFieldList.add(priceFieldC);
        for (TextField priceField : textFieldList) {
            priceField.setImmediate(true);
            priceField.setInputPrompt("price");
            priceField.setMaxLength(3);
            priceField.setWidth("90px");
        }

        Button eraseButton = new Button("Erase all");
        eraseButton.setWidth("150px");
        eraseButton.addClickListener(e -> {
            hallId.setValue(null);
            movieName.setValue(null);
            seanceDate.setValue(null);
            posterLayout.removeAllComponents();
        });

        setValues(seanceDate, hallId, movieName, textFieldList);

        Button saveButton = new Button("Save seance");
        saveButton.setWidth("250px");
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addClickListener(e -> {
            if (hallId.getValue() != null && movieName.getValue() != null && seanceDate.getValue() != null &&
                    !priceFieldA.getValue().isEmpty() && !priceFieldB.getValue().isEmpty() && !priceFieldC.getValue().isEmpty()) {
                try {
                    for (TextField priceField : textFieldList) {
                        Integer.parseInt(priceField.getValue());
                    }
                    Seance newSeance = new Seance();
                    newSeance.setSeanceDate(seanceDate.getValue());
                    newSeance.setHallId(Long.parseLong(hallId.getValue().toString()));
                    newSeance.setMovieId(Long.parseLong(movieName.getValue().toString()));
                    if (checks(newSeance)) {
                        saveSeanceAndPrice(newSeance, textFieldList);
                        Notification.show("Success!");
                    }
                } catch (NumberFormatException ex) {
                    Notification.show("Invalid price", "Only numbers!", Notification.Type.TRAY_NOTIFICATION);
                }
            } else {
                Notification.show("Fill in all fields!");
            }
        });

        VerticalLayout contentLayout = new VerticalLayout();
        contentLayout.setSpacing(true);
        contentLayout.addComponents(hallId, movieName, seanceDate);

        HorizontalLayout priceLayout = new HorizontalLayout();
        priceLayout.setSpacing(true);
        priceLayout.addComponents(priceFieldA, priceFieldB, priceFieldC);

        layout.addComponent(contentLayout, "left: 20px; top: 10px;");
        layout.addComponent(posterLayout, "right: 20px; top: 10px;");
        layout.addComponent(priceLayout, "left: 20px; bottom: 90px;");
        layout.addComponent(eraseButton, "left: 30px; bottom: 20px;");
        layout.addComponent(saveButton, "right: 30px; bottom: 20px;");

        return layout;
    }

    private void setValues(DateField seanceDate, NativeSelect hallId, ComboBox movieName, List<TextField> textFieldList) {
        if (seance != null && seance.getId() != 0) {
            seanceDate.setValue(seance.getSeanceDate());
            hallId.setValue((int) seance.getHallId());              //  be careful with with id > 2 billion
            movieName.setValue(seance.getMovieId());

            long zoneId = 3;                                        //  zone id [3;5]
            for (TextField priceField : textFieldList) {
                int price = priceService.getPriceBySeanceZone(seance.getId(), zoneId++);
                priceField.setValue(String.valueOf(price));
            }
        } else {
            seanceDate.setValue(date);
        }
    }

    private void saveSeanceAndPrice(Seance newSeance, List<TextField> textFieldList) {
        Date previousDate = seance.getSeanceDate();
        seanceService.save(newSeance);

        long zoneId = 3;
        for (TextField priceField : textFieldList) {
            Price price = new Price();
            price.setPrice(Integer.parseInt(priceField.getValue()));
            price.setSeanceId(newSeance.getId());
            price.setZoneId(zoneId++);
            priceService.save(price);
        }

        if (dateFormat.format(date).equals(dateFormat.format(newSeance.getSeanceDate())) ||
                (previousDate != null &&
                        dateFormat.format(date).equals(dateFormat.format(previousDate)))) {
            getHall(1, HallLayout1);
            getHall(2, HallLayout2);
        }
    }

    private boolean checks(Seance newSeance) {
        Date minDate = new Date(System.currentTimeMillis() + 7_200_000);   //  current date + 2 hours
        if (newSeance.getSeanceDate().before(minDate)) {
            String message = "Date can't be less than " + dateFormat.format(minDate) +
                    " " + timeFormat.format(minDate);
            notificationForWrongDate(message);
            return false;
        }

        if (!seanceService.checkIfInWorkingTime(newSeance)) {
            String message = "Start time of seance should be" +
                    LineSeparator.Windows + "between 10:00 and 22:00 o'clock.";
            notificationForWrongDate(message);
            return false;
        }

        long oldId = seance.getId();
        if (oldId == 0 || seanceService.editableSeance(oldId)) {
            newSeance.setId(oldId);
        } else {
            notificationForUnmodifiedSeance();
            return false;
        }

        if (!seanceService.checkIfHallIsFree(newSeance)) {
            String message = "There is other movie in this hall in the same time.";
            notificationForWrongDate(message);
            return false;
        }

        if (!seanceService.checkDate(newSeance)) {
            String message = "Date of seance should be between" + LineSeparator.Windows +
                    "start and final dates of selected movie.";
            notificationForWrongDate(message);
            return false;
        }

        return true;
    }

    private void notificationForWrongDate(String message) {
        Notification.show("Invalid date", message, Notification.Type.TRAY_NOTIFICATION);
    }

    private void notificationForUnmodifiedSeance() {
        String message = "Few seconds ago someone has booked tickets"
                + LineSeparator.Windows + "for this seance, so it can't be modified.";
        Notification.show("Booked tickets", message, Notification.Type.TRAY_NOTIFICATION);
    }

    private void getHall(long hallId, VerticalLayout layout) {
        List<Seance> seanceList = seanceService.getByHallAndDate(hallId, date);
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
        Label beginTime = new Label("Begin: " + timeFormat.format(seance.getSeanceDate()));
        Date endDate = new Date(seance.getSeanceDate().getTime() + movie.getDuration() * 60_000);
        Label endTime = new Label("End: " + timeFormat.format(endDate) + " + 20min");

        int price;
        String priceList = "";
        for (int zoneId = 3; zoneId < 6; zoneId++) {
            price = priceService.getPriceBySeanceZone(seance.getId(), zoneId);
            priceList = priceList + " " + price + ",";
        }
        Label prices = new Label("Prices: " + priceList.substring(0, priceList.length() - 2));

        movieInfo.addComponents(name, beginTime, endTime, prices);

        Button editButton = new Button("Edit");
        editButton.setEnabled(seanceService.editableSeance(seance.getId()));
        editButton.addClickListener(e -> {
            if (subWindow != null) {
                subWindow.close();
            }
            this.seance = seance;
            subWindow = getSubWindow();
            UI.getCurrent().addWindow(subWindow);
        });

        Button removeButton = new Button("Remove");
        removeButton.setEnabled(seanceService.editableSeance(seance.getId()));
        removeButton.addClickListener(e -> UI.getCurrent().addWindow(confirmWindow(seance)));

        horizontalLayout.addComponents(
                createPoster(movie, "80px"), movieInfo, editButton, removeButton);
        return horizontalLayout;
    }

    private Window confirmWindow(Seance seance) {
        Window removalWindow = new Window("Confirm erasing");
        removalWindow.setHeight("170px");
        removalWindow.setWidth("350px");
        removalWindow.setResizable(false);
        removalWindow.setModal(true);
        removalWindow.center();

        Label seanceDate = new Label();
        seanceDate.setValue("Seance date: " + dateFormat.format(seance.getSeanceDate())
                + " " + timeFormat.format(seance.getSeanceDate()));
        Label message = new Label();
        message.setValue("Do you really want to delete this seance?");

        Button yesButton = new Button("Yes");
        yesButton.setWidth("60px");
        yesButton.addClickListener(e -> {
            if (seanceService.editableSeance(seance.getId())) {
                seanceService.delete(seance);
                removalWindow.close();
                getHall(seance.getHallId(),
                        seance.getHallId() == 1 ? HallLayout1 : HallLayout2);
                Notification.show("Seance deleted");
            } else {
                notificationForUnmodifiedSeance();
            }
        });
        Button noButton = new Button("No");
        noButton.setWidth("60px");
        noButton.addClickListener(e -> removalWindow.close());

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        horizontalLayout.addComponents(yesButton, noButton);

        AbsoluteLayout layout = new AbsoluteLayout();
        layout.addComponent(seanceDate, "left: 20px; top: 10px;");
        layout.addComponent(message, "left: 20px; top: 40px;");
        layout.addComponent(horizontalLayout, "left: 100px; top: 80px;");

        removalWindow.setContent(layout);
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