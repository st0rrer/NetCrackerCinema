package com.netcracker.cinema.validation.routines;

import com.netcracker.cinema.validation.Validator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by User on 20.01.2017.
 */
public class UrlValidator implements Validator {

    private final String url;

    private String message;

    // константы
    private final String JPG = ".jpg";
    private final String JPEG = ".jpeg";
    private final String PNG = ".png";
    private final String HTTP = "http://";
    private final String HTTPS = "https://";

    // коды ошибок
    private final String EMPTY_URL = "Url should not be empty";
    private final String NOT_CORRECT_URL_EXTENSION = "Url should not be empty";
    private final String URL_IS_NOT_VALID = "Url is not valid";
    private final String URL_NOT_CONTAIN_HTTP_OR_HTTPS = "Url must contain: " + HTTP + " or " + HTTPS;

    private static final Logger LOGGER = Logger.getLogger(UrlValidator.class);

    public UrlValidator(String url) {
        this.url = url;
        this.message = "Success";
    }

    @Override
    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean validate() {
        System.out.println(getMessage());
        if (url == null) {
            setMessage(EMPTY_URL);
            LOGGER.log(Level.WARN, getMessage());
            return false;
        }
        return isValidUrlExtension() && urlContainsHttpOrHttps();
    }

    private boolean isValidUrlExtension() {

        if (!url.contains(JPG) &&
                !url.contains(JPEG) &&
                !url.contains(PNG)) {
            setMessage(NOT_CORRECT_URL_EXTENSION);
            LOGGER.log(Level.WARN, getMessage());
            return false;
        }
        return true;
    }


    private boolean urlContainsHttpOrHttps() {

        org.apache.commons.validator.routines.UrlValidator urlValidator = new org.apache.commons.validator.routines.UrlValidator();
        if (url.contains(HTTP) || url.contains(HTTPS)) {
            if (!urlValidator.isValid(url)) {
                setMessage(URL_IS_NOT_VALID);
                LOGGER.log(Level.WARN, getMessage());
                return false;
            }
        } else {
            setMessage(URL_NOT_CONTAIN_HTTP_OR_HTTPS);
            LOGGER.log(Level.WARN, URL_NOT_CONTAIN_HTTP_OR_HTTPS);
            return false;
        }
        return true;
    }
}
