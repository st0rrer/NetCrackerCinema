package com.netcracker.cinema.exception;

public class CinemaEmptyResultException extends CinemaDaoException {
    public CinemaEmptyResultException() {}

    public CinemaEmptyResultException(String message) {
        super(message);
    }

    public CinemaEmptyResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
