package com.netcracker.cinema.web.admin.seance;

import com.netcracker.cinema.model.Movie;
import com.netcracker.cinema.model.Price;
import com.netcracker.cinema.model.Seance;
import com.netcracker.cinema.service.PriceService;
import com.netcracker.cinema.service.SeanceService;
import com.netcracker.cinema.service.schedule.impl.ScheduleServiceImpl;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SubWindow extends Window {
    private AdminSeanceView adminSeanceView;
    private Seance seance;
    private SeanceService seanceService;
    private List<Movie> movieList;
    private PriceService priceService;
    private ScheduleServiceImpl schedule;

    private AbsoluteLayout layout = new AbsoluteLayout();
    private VerticalLayout posterLayout = new VerticalLayout();
    private NativeSelect hallId = new NativeSelect("Hall:");
    private ComboBox movieName = new ComboBox("Movie:");
    private DateField seanceDate = new DateField("Date:");
    private DateField seancePeriodEndDate = new DateField("Period end date:");
    private OptionGroup periodGroup = new OptionGroup("Select an option");
    private List<TextField> textFieldList = new ArrayList<>(10);

    private static final long ONE_DAY = 86_400_000;
    private static final long TWO_HOURS = 7_200_000;
    private DateFormat dateFormat;
    private DateFormat timeFormat;
    private boolean success;

    SubWindow(AdminSeanceView adminSeanceView, Seance seance) {
        this.adminSeanceView = adminSeanceView;
        this.seance = seance;

        movieList = adminSeanceView.movieService.findAll();
        priceService = adminSeanceView.priceService;
        seanceService = adminSeanceView.seanceService;
        schedule = adminSeanceView.schedule;
        dateFormat = adminSeanceView.dateFormat;
        timeFormat = adminSeanceView.timeFormat;

        setCaption(seance.getId() == 0 ? "Add new seance" : "Change seance");
        center();
        setHeight("440px");
        setWidth("490px");
        setResizable(false);
        setComponents();
        setContent(layout);
    }

    private void setComponents() {
        posterLayout.setWidth("150px");

        setValuesToOptionGroup();

        for (int i = 1; i < 3; i++) {
            hallId.addItem(i);
            hallId.setItemCaption(i, "Hall " + i);
        }
        hallId.setNullSelectionAllowed(false);
        hallId.setImmediate(true);
        hallId.addValueChangeListener(e -> setCaptionsToTextFields(hallId.getValue()));

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
                if (movieName.getValue() != null &&
                        movie.getId() == (long) movieName.getValue()) {
                    Label startDate = new Label("start: " + dateFormat.format(movie.getStartDate()));
                    Label endDate = new Label("final: " + dateFormat.format(movie.getEndDate()));
                    posterLayout.addComponents(adminSeanceView.createPoster(movie, 170), startDate, endDate);
                }
            }
        });

        seanceDate.setResolution(Resolution.MINUTE);
        seanceDate.setWidth("250px");

        CheckBox checkBox = new CheckBox("Add one more seance after this");
        checkBox.setVisible(false);

        setFieldsToTextFieldList();
        setCaptionsToTextFields(hallId.getValue());
        setValues();

        Button eraseButton = new Button("Erase all");
        eraseButton.setWidth("150px");
        eraseButton.addClickListener(e -> eraseAll());

        Button saveButton = new Button("Save seance");
        saveButton.setWidth("250px");
        saveButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        saveButton.addClickListener(e -> {
            if (hallId.getValue() != null &&
                    movieName.getValue() != null &&
                    seanceDate.getValue() != null &&
                    !textFieldList.get(0).getValue().isEmpty() &&
                    !textFieldList.get(1).getValue().isEmpty() &&
                    !textFieldList.get(2).getValue().isEmpty()) {
                try {
                    for (TextField priceField : textFieldList) {                    //  Checking characters
                        Integer.parseInt(priceField.getValue());
                    }

                    int periodDays = getPeriodDays(seanceDate, seancePeriodEndDate);
                    for (int i = 0; i <= periodDays; i++) {
                        Date date = new Date(seanceDate.getValue().getTime() + i * ONE_DAY);
                        Seance newSeance = new Seance();
                        newSeance.setSeanceDate(date);
                        newSeance.setHallId(Long.parseLong(hallId.getValue().toString()));
                        newSeance.setMovieId(Long.parseLong(movieName.getValue().toString()));
                        if (checks(newSeance)) {
                            saveSeanceAndPrice(newSeance);
                            success = true;
                        }
                    }
                    if (success) {
                        if (seance.getId() == 0 && checkBox.getValue()) {
                            eraseAll();
                        } else {
                            close();
                        }
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
        contentLayout.addComponents(hallId, movieName);
        if (seance != null && seance.getId() == 0) {
            setHeight("540px");
            contentLayout.addComponent(periodGroup);
            checkBox.setVisible(true);
        }

        HorizontalLayout datesLayout = new HorizontalLayout();
        datesLayout.setSpacing(true);
        datesLayout.addComponent(seanceDate);
        periodGroup.addValueChangeListener(e -> {
            if (Integer.parseInt(e.getProperty().getValue().toString()) == 1) {     //  for one day
                datesLayout.removeComponent(seancePeriodEndDate);
            } else {                                                                //  for period
                datesLayout.addComponent(seancePeriodEndDate);
            }
        });

        HorizontalLayout priceLayout = new HorizontalLayout();
        priceLayout.setSpacing(true);
        for (TextField priceField : textFieldList) {
            priceLayout.addComponent(priceField);
        }

        layout.addComponent(contentLayout, "left: 20px; top: 10px;");
        layout.addComponent(posterLayout, "right: 20px; top: 10px;");
        layout.addComponent(datesLayout, "left: 20px; bottom: 190px;");
        layout.addComponent(priceLayout, "left: 20px; bottom: 110px;");
        layout.addComponent(checkBox, "left: 30px; bottom: 70px;");
        layout.addComponent(eraseButton, "left: 30px; bottom: 20px;");
        layout.addComponent(saveButton, "right: 30px; bottom: 20px;");
    }

    private void eraseAll() {
        hallId.setValue(null);
        movieName.setValue(null);
        seanceDate.setValue(null);
        seancePeriodEndDate.setValue(null);
        posterLayout.removeAllComponents();
        for (TextField priceField : textFieldList) {
            priceField.clear();
        }
    }

    private int getPeriodDays(DateField seanceDate, DateField seancePeriodEndDate) {
        int periodDays = 0;
        if (Integer.parseInt(periodGroup.getValue().toString()) == 2
                && seancePeriodEndDate.getValue() != null) {        //  for one day
            Date startDate = seanceDate.getValue();
            Date endDate = seancePeriodEndDate.getValue();
            double periodInMillis = endDate.getTime() - startDate.getTime();
            periodDays = (int) Math.ceil(periodInMillis / ONE_DAY);
            if (periodDays < 0) {
                String message = "Start date of period should be" +
                        LineSeparator.Windows + "before end date of period.";
                notificationForWrongDate(message);
            }
        }
        return periodDays;
    }

    private void setFieldsToTextFieldList() {
        for (int i = 0; i < 3; i++) {
            TextField priceField = new TextField();
            priceField.setImmediate(true);
            priceField.setInputPrompt("price");
            priceField.setMaxLength(3);
            priceField.setWidth("90px");
            textFieldList.add(priceField);
        }
    }

    private void setValuesToOptionGroup() {
        periodGroup.addItem(1);
        periodGroup.setItemCaption(1, "Seance for one day");
        periodGroup.addItem(2);
        periodGroup.setItemCaption(2, "Seances for period");
        periodGroup.select(1);
        periodGroup.setNullSelectionAllowed(false);
        periodGroup.setImmediate(true);
    }

    private void setCaptionsToTextFields(Object hallId) {
        if (hallId != null && hallId.equals(1)) {
            textFieldList.get(0).setCaption("Zone 'AA'");
            textFieldList.get(1).setCaption("Zone 'BB'");
            textFieldList.get(2).setCaption("Zone 'CC'");
        } else if (hallId != null && hallId.equals(2)) {
            textFieldList.get(0).setCaption("Zone 'DD'");
            textFieldList.get(1).setCaption("Zone 'EE'");
            textFieldList.get(2).setCaption("Zone 'FF'");
        } else {
            textFieldList.get(0).setCaption("Zone");
            textFieldList.get(1).setCaption("Zone");
            textFieldList.get(2).setCaption("Zone");
        }
    }

    private void setValues() {
        if (seance != null && seance.getId() != 0) {
            seanceDate.setValue(seance.getSeanceDate());
            hallId.setValue(seance.getHallId() == 1 ? 1 : 2);
            movieName.setValue(seance.getMovieId());

            long zoneId = (seance.getHallId() == 1 ? 3 : 6);
            for (TextField priceField : textFieldList) {
                Price price = priceService.getPriceBySeanceZone(seance.getId(), zoneId++);
                priceField.setValue(String.valueOf(price.getPrice()));
            }
        } else {
            seanceDate.setValue(adminSeanceView.date);
        }
    }

    private void saveSeanceAndPrice(Seance newSeance) {
        Date previousDate = seance.getSeanceDate();
        seanceService.save(newSeance);
        schedule.createTask(newSeance.getSeanceDate(), newSeance.getId());

        long oldZoneId = (seance.getHallId() == 1 ? 3 : 6);
        long newZoneId = (newSeance.getHallId() == 1 ? 3 : 6);
        for (TextField priceField : textFieldList) {
            Price price;
            if (seance != null && seance.getId() != 0) {
                price = priceService.getPriceBySeanceZone(newSeance.getId(), oldZoneId++);
            } else {
                price = new Price();
                price.setSeanceId(newSeance.getId());
            }
            price.setPrice(Integer.parseInt(priceField.getValue()));
            price.setZoneId(newZoneId++);
            priceService.save(price);
        }

        if (dateFormat.format(adminSeanceView.date).equals(dateFormat.format(newSeance.getSeanceDate())) ||
                (previousDate != null &&
                        dateFormat.format(adminSeanceView.date).equals(dateFormat.format(previousDate)))) {
            adminSeanceView.getHall(1);
            adminSeanceView.getHall(2);
        }
    }

    private boolean checks(Seance newSeance) {
        Date minDate = new Date(System.currentTimeMillis() + TWO_HOURS);
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
}