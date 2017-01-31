package com.netcracker.cinema.web.admin.rating;

import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.Resolution;
import org.tepi.filtertable.FilterDecorator;
import org.tepi.filtertable.numberfilter.NumberFilterPopupConfig;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Locale;

@SuppressWarnings("serial")
class DemoFilterDecorator implements FilterDecorator, Serializable {


    @Override
    public String getBooleanFilterDisplayName(Object propertyId, boolean value) {
        if ("validated".equals(propertyId)) {
            return value ? "Validated" : "Not validated";
        }
        // returning null will output default value
        return null;
    }

    @Override
    public String getFromCaption() {
        return "Start date:";
    }

    @Override
    public String getToCaption() {
        return "End date:";
    }

    @Override
    public boolean isTextFilterImmediate(Object propertyId) {
        // use text change events for all the text fields
        return true;
    }

    @Override
    public int getTextChangeTimeout(Object propertyId) {
        // use the same timeout for all the text fields
        return 500;
    }

    @Override
    public String getAllItemsVisibleString() {
        return "Show all";
    }

    @Override
    public Resolution getDateFieldResolution(Object propertyId) {
        return Resolution.DAY;
    }

    public DateFormat getDateFormat(Object propertyId) {
        return DateFormat.getDateInstance(DateFormat.SHORT, new Locale("ru", "RU"));
    }

    @Override
    public boolean usePopupForNumericProperty(Object propertyId) {
        return true;
    }

    @Override
    public String getDateFormatPattern(Object propertyId) {
        return null;
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public NumberFilterPopupConfig getNumberFilterPopupConfig() {
        return null;
    }

    @Override
    public String getSetCaption() {
        return null;
    }

    @Override
    public String getClearCaption() {
        return null;
    }

    @Override
    public String getEnumFilterDisplayName(Object propertyId, Object value) {
        return null;
    }

    @Override
    public Resource getEnumFilterIcon(Object propertyId, Object value) {
        return null;
    }

    @Override
    public Resource getBooleanFilterIcon(Object propertyId, boolean value) {
        return null;
    }
}
