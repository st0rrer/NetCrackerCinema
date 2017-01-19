package com.netcracker.cinema.exception;

/**
 * Created by aogim on 16.01.2017.
 */
public class CinemaException extends RuntimeException {
    public CinemaException() {
    }

    public CinemaException(String message) {
        super(message);
    }

    public CinemaException(String message, Throwable cause) {
        super(message, cause);
    }
}
