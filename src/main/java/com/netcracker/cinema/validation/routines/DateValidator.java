package com.netcracker.cinema.validation.routines;

import com.netcracker.cinema.validation.Validator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by User on 31.01.2017.
 */
public class DateValidator implements Validator {

    private static final Logger logger = Logger.getLogger(DateValidator.class);

    private String message;

    private Date startDate;
    private Date endDate;

    public DateValidator(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public boolean validate() {
        if(startDate == null || endDate == null) {
            setMessage("Value can not be empty or value must be a date");
            logger.log(Level.WARN, getMessage());
            return false;
        }
        return lessThanCurrent()&&lessThanEndDate();
    }

    private boolean lessThanCurrent() {
        if(startDate.getTime() < new Date().getTime()) {
            setMessage("StartDate can not be less than current");
            logger.log(Level.WARN, getMessage());
            return false;
        }
        return true;
    }

    private boolean lessThanEndDate() {
        if(endDate.getTime() < startDate.getTime()) {
            setMessage("\"Start startDate\" can't be older then the \"End startDate\"");
            logger.log(Level.WARN, getMessage());
            return false;
        }
        return true;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }
}
