package com.netcracker.cinema.exception;

/**
 * Created by gaya on 14.11.2016.
 * не полный обработчик !!
 * Потом нужно будет и  другие моменты учитывать
 */

@SuppressWarnings("serial")
public class DaoRuntimeException extends RuntimeException {

    public DaoRuntimeException() {
        super();
    }

    public DaoRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaoRuntimeException(String message) {
        super(message);
    }

}
