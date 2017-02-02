package com.netcracker.cinema.exception;

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
