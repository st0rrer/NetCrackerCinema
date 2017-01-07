package com.netcracker.cinema.validation;

import org.apache.commons.validator.routines.UrlValidator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created by User on 16.12.2016.
 */
public class Validation {

    private static final Logger logger = Logger.getLogger(Validation.class);

    private final String HTTP = "http://";
    private final String HTTPS = "https://";
    private final String JPG = ".jpg";
    private final String JPEG = ".jpeg";
    private final String PNG = ".png";

    /**
     * if url contains prefix http or https, return true
     *
     * @param url
     * @return
     */
    public boolean urlContainsHttpOrHttps(String url) {
        if (url == null) {
            logger.log(Level.WARN, "Url should not be empty");
            return false;
        }
        UrlValidator urlValidator = new UrlValidator();
        if (url.contains(HTTP) || url.contains(HTTPS)) {
            if (!urlValidator.isValid(url)) {
                logger.log(Level.WARN, "Url " + url + " is not valid");
                return false;
            }
        } else {
            logger.log(Level.WARN, "Url must contain: " + HTTP + " or " + HTTPS);
            return false;
        }
        return true;
    }

    /**
     * if url has need extension, return true
     *
     * @param url - url
     * @return - boolean
     */
    public boolean isValidUrlExtension(String url) {
        if (url == null) {
            logger.log(Level.WARN, "Url should not be empty");
            return false;
        }
        if (!url.contains(JPG) &&
                !url.contains(JPEG) &&
                !url.contains(PNG)) {
            logger.log(Level.WARN, "Image is not exist or extension is not correct");
            return false;
        }
        return true;
    }

    /**
     * if string can convert to Integer, return true
     *
     * @param string - string
     * @return - boolean
     */
    public boolean isInteger(String string) {
        if (string != null && string.matches("[-+]?\\d+")) {
            return true;
        }
        logger.log(Level.WARN, "String can not convert to Integer");
        return false;
    }

    /**
     * if string can convert to Double, return true
     *
     * @param string - string
     * @return - boolean
     */
    public boolean isDouble(String string) {
        if (string != null && string.matches("((-|\\+)?[0-9]+(\\.[0-9]+)?)+")) {
            return true;
        }
        logger.log(Level.WARN, "String can not convert to Double");
        return false;
    }

    /**
     * if all fields can convert to integer, return true
     *
     * @param list - list of fields
     * @return - boolean
     */
    public boolean isValuesInteger(ArrayList<String> list) {
        for (String str : list) {
            if (!isInteger(str)) {
                logger.log(Level.WARN, "String can not convert to Integer");
                return false;
            }
        }
        return true;
    }

    /**
     * if all fields are empty or are null, return true
     *
     * @param list - list of fields
     * @return - boolean
     */
    public boolean fieldsAreEmpty(ArrayList<String> list) {
        for (String str : list) {
            if (str == null || str.trim().length() == 0) {
                logger.log(Level.WARN, "Field(s) are empty");
                return true;
            }
        }
        return false;
    }

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

}
