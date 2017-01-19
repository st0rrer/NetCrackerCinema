package com.netcracker.cinema.exception;

/**
 * Created by aogim on 16.01.2017.
 */
public class CinemaDaoException extends CinemaException {
    public CinemaDaoException() {}

    public CinemaDaoException(String message) {
        super(message);
    }

    public CinemaDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
