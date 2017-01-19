package com.netcracker.cinema.exception;

/**
 * Created by aogim on 16.01.2017.
 */
public class CinemaEmptyResultException extends CinemaDaoException {
    public CinemaEmptyResultException() {}

    public CinemaEmptyResultException(String message) {
        super(message);
    }

    public CinemaEmptyResultException(String message, Throwable cause) {
        super(message, cause);
    }
}
