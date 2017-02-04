package com.netcracker.cinema.web.admin.settings;

import com.netcracker.cinema.validation.Validator;
import com.netcracker.cinema.validation.routines.IntegerValidator;
import com.netcracker.cinema.web.AdminUI;
import com.netcracker.cinema.web.admin.seance.AdminSeanceView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import javax.annotation.PostConstruct;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.netcracker.cinema.web.admin.seance.SeanceTimeSettings.*;

@SpringView(name = AdminSettingsView.VIEW_NAME, ui = AdminUI.class)
public class AdminSettingsView extends HorizontalLayout implements View {
    public static final String VIEW_NAME = "settings";

    InlineDateField startTime = new InlineDateField("Start time: ");
    InlineDateField lastTime = new InlineDateField("Last time: ");
    TextField cleaningTime = new TextField("Cleaning time (min): ");
    TextField reserveTime = new TextField("Minutes before seance: ");

    Button apply = new Button("Apply");
    Button reset = new Button("Reset");
    Button defaultButton = new Button("Default");

    @PostConstruct
    protected void init() {
        DateFormat timeFormat = new SimpleDateFormat("HHmm");

        startTime.setDateFormat("dd MMM yyyy HH:mm");
        startTime.setLocale(AdminSeanceView.englishLocale);
        startTime.setResolution(Resolution.MINUTE);
        startTime.addStyleName("time-only");
        startTime.setDescription("First available time for starting seance");

        lastTime.setLocale(AdminSeanceView.englishLocale);
        lastTime.setResolution(Resolution.MINUTE);
        lastTime.addStyleName("time-only");
        lastTime.setDescription("Last available time for starting seance");

        cleaningTime.setMaxLength(3);
        cleaningTime.setWidth("125px");
        cleaningTime.addStyleName("field-caption");
        cleaningTime.setDescription("Minimal time between seances for cleaning");

        reserveTime.setMaxLength(3);
        reserveTime.setWidth("125px");
        reserveTime.addStyleName("field-caption");
        reserveTime.setDescription("Set minimal time that should be between creating "
                + "time of new seance and start time of this new seance");

        CheckBox checkBox = new CheckBox("Change settings");
        checkBox.addValueChangeListener(e -> {
            if (checkBox.getValue()) {
                setEnable(true);
            } else {
                setDefaultValues();
                setEnable(false);
            }
        });

        apply.setWidth("90px");
        apply.setStyleName(ValoTheme.BUTTON_FRIENDLY);
        apply.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        apply.setDescription("Save current settings");
        apply.addClickListener(e -> {
            if (fieldValidation(cleaningTime.getValue()) &&
                    fieldValidation(reserveTime.getValue())) {
                START_TIME_OF_WORKING_DAY = Long.parseLong(timeFormat.format(startTime.getValue()));
                LAST_START_TIME_OF_SEANCE = Long.parseLong(timeFormat.format(lastTime.getValue()));
                TIME_FOR_CLEANING = Long.parseLong(cleaningTime.getValue());
                MIN_TIME_TO_START_SEANCE = Long.parseLong(reserveTime.getValue());

                checkBox.setValue(false);
                Notification.show("Success!");
            }
        });

        reset.setWidth("90px");
        reset.setDescription("Reset last save settings");
        reset.addClickListener(e -> checkBox.setValue(false));

        defaultButton.setWidth("90px");
        defaultButton.setStyleName(ValoTheme.BUTTON_PRIMARY);
        defaultButton.setDescription("Set and save work time 10:00-22:00, "
                + "20 minutes for cleaning and 120 minutes before seance");
        defaultButton.addClickListener(e -> {
            initializeDefaultTimes();
            checkBox.setValue(false);
        });

        setDefaultValues();
        setEnable(false);

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.addComponents(apply, reset, defaultButton);

        FormLayout wrapper = new FormLayout();
        wrapper.addComponents(startTime, lastTime, cleaningTime, reserveTime);
        wrapper.addStyleName("settings-form");
        wrapper.setSpacing(true);

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidth("325px");
        layout.addComponents(checkBox, wrapper, buttonsLayout);
        layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);

        addComponent(layout);
        setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    private void setDefaultValues() {
        startTime.setValue(new Date(getTime(START_TIME_OF_WORKING_DAY)));
        lastTime.setValue(new Date(getTime(LAST_START_TIME_OF_SEANCE)));
        cleaningTime.setValue(TIME_FOR_CLEANING + "");
        reserveTime.setValue(MIN_TIME_TO_START_SEANCE + "");
    }

    private void setEnable(boolean b) {
        startTime.setEnabled(b);
        lastTime.setEnabled(b);
        cleaningTime.setEnabled(b);
        reserveTime.setEnabled(b);

        apply.setEnabled(b);
        reset.setEnabled(b);
        defaultButton.setEnabled(b);
    }

    private long getTime(long time) {
        final int timezoneOffset = AdminSeanceView.webBrowser.getRawTimezoneOffset();
        return time / 100 * ONE_HOUR + time % 100 * ONE_MINUTE - timezoneOffset;
    }

    private boolean fieldValidation(String str) {
        Validator intValidator = new IntegerValidator(str);
        if (!intValidator.validate()) {
            Notification.show("Invalid time", intValidator.getMessage(), Notification.Type.TRAY_NOTIFICATION);
            return false;
        }
        return true;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}