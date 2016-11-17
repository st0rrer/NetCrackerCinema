package com.netcracker.cinema.web.adminAddMovie;

import com.vaadin.ui.Button;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

/**
 * Created by Илья on 17.11.2016.
 */
public class Info extends FormLayout {
    TextField duration;
    TextField imdb;
    TextField periodicity;
    TextField basePrice;
    TextField timeOut;
    DateField rollingStart;
    DateField rollingEnd;
    Button save;
    Button back;
    public Info() {
        setSizeFull();
        init();
        addComponent(duration);
        addComponent(imdb);
        addComponent(periodicity);
        addComponent(basePrice);
        addComponent(timeOut);
        addComponent(rollingStart);
        addComponent(rollingEnd);
        addComponent(save);
        addComponent(back);

        setTextFieldsNotNull();
    }

    public void init(){
        duration = new TextField("Duration:");
        imdb = new TextField("Imdb:");
        periodicity = new TextField("Periodicity:");
        basePrice = new TextField("Base Price:");
        timeOut = new TextField("Time Out:");

        rollingStart = new DateField("Rolling Start:");
        rollingEnd = new DateField("Rolling End:");

        save = new Button("Save");
        back = new Button("Back");
    }

    public void setTextFieldsNotNull(){
        duration.setNullSettingAllowed(false);
        imdb.setNullSettingAllowed(false);
        periodicity.setNullSettingAllowed(false);
        basePrice.setNullSettingAllowed(false);
        timeOut.setNullSettingAllowed(false);

    }
}
