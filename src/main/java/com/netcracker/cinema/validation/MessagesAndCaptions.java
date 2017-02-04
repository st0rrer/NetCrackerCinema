package com.netcracker.cinema.validation;

import com.sun.org.apache.xml.internal.serialize.LineSeparator;

public interface MessagesAndCaptions {
    String INVALID_PRICE_CAPTION = "Invalid price";
    String BOOKED_TICKETS_CAPTION = "Booked tickets";
    String BOOKED_TICKETS_MESSAGE = "Few seconds ago someone has booked tickets" +
            LineSeparator.Windows + "for this seance, so it can't be modified.";
    String INVALID_DATE = "Invalid date";
    String ROLLED_DATES = "Date of seance should be between" +
            LineSeparator.Windows + "start and final dates of selected movie.";
    String MOVIES_CONFLICT = "There is other movie in this hall in the same time.";
    String PERIOD_DATES = "Start date of period should be" +
            LineSeparator.Windows + "before end date of period.";
}