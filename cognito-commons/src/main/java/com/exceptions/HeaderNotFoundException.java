package com.exceptions;

/**
 * The type Header not found exception.
 */
public class HeaderNotFoundException extends RuntimeException{

    /**
     * Instantiates a new Header not found exception.
     */
    public HeaderNotFoundException() {
        super();
    }

    /**
     * Instantiates a new Header not found exception.
     *
     * @param message the message
     */
    public HeaderNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Header not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public HeaderNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Header not found exception.
     *
     * @param cause the cause
     */
    public HeaderNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new Header not found exception.
     *
     * @param message            the message
     * @param cause              the cause
     * @param enableSuppression  the enable suppression
     * @param writableStackTrace the writable stack trace
     */
    public HeaderNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
