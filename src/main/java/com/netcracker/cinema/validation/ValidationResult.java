package com.netcracker.cinema.validation;

public class ValidationResult {

    private boolean valid;
    private String message;

    public ValidationResult(boolean valid, String message) {
        this.valid = valid;
        this.message = message;
    }

    public ValidationResult(boolean valid) {
        this.valid = valid;
        this.message = "Success";
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

}
