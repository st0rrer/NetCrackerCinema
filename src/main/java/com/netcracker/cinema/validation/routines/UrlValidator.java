package com.netcracker.cinema.validation.routines;

import com.netcracker.cinema.validation.AbstractValidator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by User on 20.01.2017.
 */
public class UrlValidator extends AbstractValidator {

    private final String url;

    private static final Logger LOGGER = Logger.getLogger(UrlValidator.class);

    public UrlValidator(String url) {
        this.url = url;
    }

    @Override
    public boolean validate() {
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
