package com.davelabine.resterapp.platform.api.exceptions;

/**
 * Created by davidl on 3/9/17.
 */
public class DaoException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DaoException(String message) {
        super(message);
    }
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
