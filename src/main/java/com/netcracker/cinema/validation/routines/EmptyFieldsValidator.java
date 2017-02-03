package com.netcracker.cinema.validation.routines;

import com.netcracker.cinema.validation.Validator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class EmptyFieldsValidator implements Validator {

    private static final Logger logger = Logger.getLogger(EmptyFieldsValidator.class);

    private String message;

    private List<String> fields;

    public EmptyFieldsValidator(String field) {
        fields = new ArrayList<>();
        fields.add(field);
    }

    public EmptyFieldsValidator(List<String> fields) {
        this.fields = fields;
    }

    @Override
    public boolean validate() {
        return fields != null ? fieldsAreEmpty(fields) : false;
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    private boolean fieldsAreEmpty(List<String> list) {
        for (String str : list) {
            if (str == null || str.trim().length() == 0) {
                return false;
            }
        }
        setMessage("Field(s) are empty");
        logger.log(Level.WARN, getMessage());
        return true;
    }
}
