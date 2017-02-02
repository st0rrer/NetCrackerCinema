package com.netcracker.cinema.exception;

public class CinemaDaoException extends CinemaException {
    public CinemaDaoException() {}

    public CinemaDaoException(String message) {
        super(message);
    }

    public CinemaDaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
