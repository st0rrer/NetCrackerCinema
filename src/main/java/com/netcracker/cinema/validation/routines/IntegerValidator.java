package com.netcracker.cinema.validation.routines;

import com.netcracker.cinema.validation.Validator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class IntegerValidator implements Validator {

    private static final Logger logger = Logger.getLogger(IntegerValidator.class);

    private List<String> values;

    private String message;

    public IntegerValidator(String value) {
        values = new ArrayList<>();
        values.add(value);
    }

    public IntegerValidator(List<String> values) {
        this.values = values;
    }

    @Override
    public boolean validate() {
        return isValuesInteger(values);
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private boolean isInteger(String value) {
        if (value != null && value.matches("\\d+")) {
            return true;
        }
        setMessage("Only numbers are allowed or number is negative");
        logger.log(Level.WARN, getMessage());
        return false;
    }

    private boolean isValuesInteger(List<String> list) {
        for (String str : list) {
            if (!isInteger(str)) {
                return false;
            }
        }
        return true;
    }
}
