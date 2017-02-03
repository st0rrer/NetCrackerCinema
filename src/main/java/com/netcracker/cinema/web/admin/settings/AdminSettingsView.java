package com.netcracker.cinema.web.admin.settings;

import com.netcracker.cinema.web.AdminUI;
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
    TextField reserveTime = new TextField("Reserve time (min): ");

    @PostConstruct
    protected void init() {
        DateFormat timeFormat = new SimpleDateFormat("HHmm");

        startTime.setResolution(Resolution.MINUTE);
        startTime.addStyleName("time-only");

        lastTime.setResolution(Resolution.MINUTE);
        lastTime.addStyleName("time-only");

        cleaningTime.setMaxLength(3);
        cleaningTime.setWidth("125px");
        cleaningTime.addStyleName("field-caption");

        reserveTime.setMaxLength(3);
        reserveTime.setWidth("125px");
        reserveTime.addStyleName("field-caption");

        CheckBox checkBox = new CheckBox("Change settings");
        checkBox.addValueChangeListener(e -> {
            if (checkBox.getValue()) {
                startTime.setEnabled(true);
                lastTime.setEnabled(true);
                cleaningTime.setEnabled(true);
                reserveTime.setEnabled(true);
            } else {
                setDefaultValues();
            }
        });

        Button apply = new Button("Apply");
        apply.setWidth("90px");
        apply.setStyleName(ValoTheme.BUTTON_PRIMARY);
        apply.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        apply.addClickListener(e -> {
            START_TIME_OF_WORKING_DAY = Long.parseLong(timeFormat.format(startTime.getValue()));
            LAST_START_TIME_OF_SEANCE = Long.parseLong(timeFormat.format(lastTime.getValue()));
            TIME_FOR_CLEANING = Long.parseLong(cleaningTime.getValue());
            MIN_TIME_TO_START_SEANCE = Long.parseLong(reserveTime.getValue());
            checkBox.setValue(false);
        });

        Button reset = new Button("Reset");
        reset.setWidth("90px");
        reset.addClickListener(e -> setDefaultValues());

        setDefaultValues();

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setSpacing(true);
        buttonsLayout.addComponents(apply, reset);

        FormLayout wrapper = new FormLayout();
        wrapper.addComponents(startTime, lastTime, cleaningTime, reserveTime);
        wrapper.addStyleName("settings-form");
        wrapper.setSpacing(true);

        VerticalLayout layout = new VerticalLayout();
        layout.setSpacing(true);
        layout.setWidth("320px");
        layout.addComponents(checkBox, wrapper, buttonsLayout);
        layout.setComponentAlignment(checkBox, Alignment.TOP_CENTER);
        layout.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);

        addComponent(layout);
        setComponentAlignment(layout, Alignment.MIDDLE_CENTER);
        setSizeFull();
    }

    private void setDefaultValues() {
        startTime.setValue(new Date((START_TIME_OF_WORKING_DAY - 200) * ONE_HOUR / 100));
        lastTime.setValue(new Date((LAST_START_TIME_OF_SEANCE - 200) * ONE_HOUR / 100));
        cleaningTime.setValue(TIME_FOR_CLEANING + "");
        reserveTime.setValue(MIN_TIME_TO_START_SEANCE + "");

        startTime.setEnabled(false);
        lastTime.setEnabled(false);
        cleaningTime.setEnabled(false);
        reserveTime.setEnabled(false);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}