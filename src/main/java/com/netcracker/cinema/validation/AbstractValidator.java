package com.netcracker.cinema.validation;

/**
 * Created by User on 23.01.2017.
 */
public abstract class AbstractValidator implements Validator {

    // по умолчанию
    private Caption caption;
    private String message = "Error";

    // константы
    protected final String JPG = ".jpg";
    protected final String JPEG = ".jpeg";
    protected final String PNG = ".png";
    protected final String HTTP = "http://";
    protected final String HTTPS = "https://";

    // коды ошибок
    protected final String EMPTY_URL = "Url should not be empty";
    protected final String NOT_CORRECT_URL_EXTENSION = "Url should not be empty";
    protected final String URL_IS_NOT_VALID = "Url is not valid";
    protected final String URL_NOT_CONTAIN_HTTP_OR_HTTPS = "Url must contain: " + HTTP + " or " + HTTPS;

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