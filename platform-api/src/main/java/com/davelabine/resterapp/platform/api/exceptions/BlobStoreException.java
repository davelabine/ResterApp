package com.davelabine.resterapp.platform.api.exceptions;

/**
 * Created by davidl on 3/10/17.
 */
public class BlobStoreException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public BlobStoreException(String message) {
        super(message);
    }
    public BlobStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
