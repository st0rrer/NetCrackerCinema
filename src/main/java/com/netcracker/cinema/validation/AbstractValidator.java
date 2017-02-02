package com.netcracker.cinema.validation;

public abstract class AbstractValidator implements Validator {

    private Caption caption;
    private String message = "Error";

    public Caption getCaption() {
        return caption;
    }

    void setCaption(Caption caption) {
        this.caption = caption;
    }

    @Override
    public String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    public enum Caption {
        invalid_date("Invalid date"),
        invalid_price("Invalid price"),
        booked_tickets("Booked tickets");
        private String fullName;

        Caption(String fullName) {
            this.fullName = fullName;
        }

        public String getFullName() {
            return fullName;
        }
    }
}