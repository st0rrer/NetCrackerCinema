package com.netcracker.cinema.validation;
import com.netcracker.cinema.utils.ConfirmationDialog;
import com.vaadin.ui.UI;

import java.util.Date;

public class MovieUIValidation extends Validation {

    public boolean isValidUrl(String url) {

        if(!urlContainsHttpOrHttps(url)) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Url is not valid"));
            return false;
        }

        if(!isValidUrlExtension(url)) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Image is not exist or extension is not correct"));
            return false;
        }

        return true;
    }


    public boolean isDateValid(Date startDate, Date endDate) {
        if(startDate == null || endDate == null) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("Value can not be empty or value must be a date"));
            return false;
        }

        if(startDate.getTime() < new Date().getTime()) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("StartDate can not be less than current"));
            return false;
        }

        if(endDate.getTime() < startDate.getTime()) {
            UI.getCurrent().addWindow(new ConfirmationDialog().infoDialog("\"Start startDate\" can't be older then the \"End startDate\""));
            return false;
        }

        return true;
    }





}